package ru.yandex.practicum.commerce.warehouse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.dto.*;
import ru.yandex.practicum.commerce.api.dto.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.commerce.api.dto.exception.ProductNotFoundException;
import ru.yandex.practicum.commerce.api.dto.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.warehouse.mapper.BookedProductsMapper;
import ru.yandex.practicum.commerce.warehouse.mapper.DimensionMapper;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;
import ru.yandex.practicum.commerce.warehouse.model.ReservedProduct;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseAddress;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;
import ru.yandex.practicum.commerce.warehouse.repository.ReservedProductsRepository;
import ru.yandex.practicum.commerce.warehouse.repository.WarehouseProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneralWarehouseService implements WarehouseService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final ReservedProductsRepository reservedProductsRepository;
    private final DimensionMapper dimensionMapper;
    private final BookedProductsMapper bookedProductsMapper;

    @Override
    public void addNewProduct(NewProductInWarehouseRequest newProduct) {
        if (warehouseProductRepository.findById(UUID.fromString(newProduct.productId())).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException("Product already exists in warehouse");
        }

        Dimension dimension = dimensionMapper.fromDto(newProduct.dimension());

        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                .productId(UUID.fromString(newProduct.productId()))
                .fragile(newProduct.fragile())
                .dimension(dimension)
                .weight(newProduct.weight())
                .quantity(0L)
                .build();

        warehouseProductRepository.save(warehouseProduct);
    }

    @Override
    public void returnProducts(Map<String, Long> products) {
        List<WarehouseProduct> warehouseProductList = warehouseProductRepository.findAllByProductIdIn(
                    products.keySet().stream()
                            .map(UUID::fromString)
                            .toList());
        if (products.size() != warehouseProductList.size()) {
            throw new ProductNotFoundException("Not all product found in warehouse");
        }

        Map<UUID, WarehouseProduct> productMap = warehouseProductList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));

        for (Map.Entry<String, Long> entry : products.entrySet()) {
            UUID productId = UUID.fromString(entry.getKey());
            WarehouseProduct product = productMap.get(productId);
            product.setQuantity(product.getQuantity() + entry.getValue());
        }
        List<WarehouseProduct> savedProducts = warehouseProductRepository.saveAll(productMap.values());
        log.info("Returning products with id: {}, by quantity {}. Products with new quantity: {}",
                products.keySet(), products.entrySet(), savedProducts);
    }

    @Override
    @Transactional
    public BookedProductsDto bookProducts(ShoppingCartDto shoppingCart) { //Зарезервировать товары на складе по корзине.
        Map<String, Long> productsForBooking = shoppingCart.products();
        List<UUID> productsIds = productsForBooking.keySet().stream()
                .map(UUID::fromString)
                .toList();
        Map<UUID, WarehouseProduct> currentProducts =
                warehouseProductRepository.findAllByProductIdIn(productsIds).stream()
                        .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));
        for(UUID productId : productsIds) {
            if (currentProducts.get(productId) == null) {
                throw new ProductNotFoundException("Product not found in warehouse");
            }
            if (currentProducts.get(productId).getQuantity() < productsForBooking.get(productId.toString())) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        "Not enough product " + productId + " + in warehouse");
            }

        }

        List<ReservedProduct> productsForBookingList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : productsForBooking.entrySet()) {
            productsForBookingList.add(ReservedProduct.builder()
                    .shoppingCartId(UUID.fromString(shoppingCart.shoppingCartId()))
                    .productId(UUID.fromString(entry.getKey()))
                    .reservedQuantity(entry.getValue())
                    .build());
        }

        List<ReservedProduct> reservedProductsList = reservedProductsRepository.saveAll(productsForBookingList);

        return bookedProductsMapper.toBookedProductsDto(reservedProductsList, currentProducts);
    }

    @Override
    @Transactional
    public BookedProductsDto assemblyProductsForOrder(AssemblyProductForOrderFromShoppingCartRequest assembly) {
        List<ReservedProduct> reservedProducts = reservedProductsRepository.findAllByShoppingCartId(
                UUID.fromString(assembly.shoppingCartId()));

        List<WarehouseProduct> warehouseProductList = warehouseProductRepository.findAllByProductIdIn(
                reservedProducts.stream()
                        .map(ReservedProduct::getReservedProductId)
                        .toList());
        Map<UUID, WarehouseProduct> warehouseProductMap = warehouseProductList.stream()
                .collect(Collectors.toMap(WarehouseProduct::getProductId, product -> product));

        for (ReservedProduct reservedProduct : reservedProducts) {
            UUID reservedProductId = reservedProduct.getReservedProductId();

            long currentQuantity = warehouseProductMap.get(reservedProductId).getQuantity();
            long reservedQuantity = reservedProduct.getReservedQuantity();
            warehouseProductMap.get(reservedProductId).setQuantity(currentQuantity - reservedQuantity);
        }

        warehouseProductRepository.saveAll(warehouseProductMap.values());

        return bookedProductsMapper.toBookedProductsDto(reservedProducts, warehouseProductMap);
    }

    @Override
    public void addingProductsQuantity(AddProductToWarehouseRequest addingProductsQuantity) {
        WarehouseProduct warehouseProduct =
                warehouseProductRepository.findByProductId(UUID.fromString(addingProductsQuantity.productId()))
                        .orElseThrow(() -> new ProductNotFoundException(
                                "Product with id: " + addingProductsQuantity.productId() + " not found in warehouse"));
        warehouseProduct.setQuantity(warehouseProduct.getQuantity() + addingProductsQuantity.quantity());
        WarehouseProduct saveWarehouseProduct = warehouseProductRepository.save(warehouseProduct);
        log.info("Added product with id: {}, by quantity {}. New quantity: {}",
                addingProductsQuantity.productId(),
                addingProductsQuantity.quantity(),
                saveWarehouseProduct.getQuantity());
    }

    @Override
    public AddressDto getWarehouseAddress() {
        WarehouseAddress warehouseAddress = new WarehouseAddress();
        return AddressDto.builder()
                .country(warehouseAddress.getCountry())
                .city(warehouseAddress.getCity())
                .street(warehouseAddress.getStreet())
                .house(warehouseAddress.getHouse())
                .flat(warehouseAddress.getFlat())
                .build();
    }

}
