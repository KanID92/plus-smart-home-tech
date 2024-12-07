package ru.yandex.practicum.commerce.warehouse.service;

import ru.yandex.practicum.commerce.api.dto.*;
import ru.yandex.practicum.commerce.api.dto.exception.AssemblyProductForOrderRequest;

import java.util.Map;

public interface WarehouseService {

    void addNewProduct(NewProductInWarehouseRequest newProduct);

    void returnProducts(Map<String, Long> products);

    BookedProductsDto assemblyProductsForOrder(AssemblyProductForOrderRequest assembly);

    BookedProductsDto checkForProductsSufficiency(ShoppingCartDto shoppingCart);

    void addingProductsQuantity(AddProductToWarehouseRequest addingProductsQuantity);

    AddressDto getWarehouseAddress();

    void shipToDelivery(ShippedToDeliveryRequest shippedToDeliveryRequest);

}
