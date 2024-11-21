package ru.yandex.practicum.commerce.shoppingCart.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.api.dto.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.api.dto.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.shoppingCart.mapper.CartMapper;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProductId;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;
import ru.yandex.practicum.commerce.shoppingCart.repository.CartProductsRepository;
import ru.yandex.practicum.commerce.shoppingCart.repository.CartRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GeneralCartService implements CartService {

    private final CartRepository cartRepository;
    private final CartProductsRepository cartProductsRepository;
    private final CartMapper cartMapper;

    @Override
    public ShoppingCartDto get(String username) {
        checkUsernameForEmpty(username);
        Optional<ShoppingCart> shoppingCart = cartRepository.findByUsernameAndActivatedEqualsIgnoreCase(
                username, true);
        if (shoppingCart.isPresent()) {
            List<CartProduct> cartProductList =
                    cartProductsRepository.findAllCartProductsOfCart(shoppingCart.get().getShoppingCartId());
            return cartMapper.toShoppingCartDto(shoppingCart.get(), cartProductList);
        } else {
            throw new NotAuthorizedUserException("No cart for username: " + username);
        }
    }

    @Override
    public ShoppingCartDto addProducts(String username, Map<String, Long> products) {
        checkUsernameForEmpty(username);
        ShoppingCartDto currentShoppingCart = get(username);
        UUID cartId = UUID.fromString(currentShoppingCart.shoppingCartId());
        List<CartProduct> newCartProducts = new ArrayList<>();
        for (Map.Entry<String, Long> entry : products.entrySet()) {
            newCartProducts.add(
                    new CartProduct(
                        new CartProductId(cartId, UUID.fromString(entry.getKey())), entry.getValue()));
        }

        cartProductsRepository.saveAll(newCartProducts);

        return cartMapper.toShoppingCartDto(cartMapper.toShoppingCart(currentShoppingCart),
                cartProductsRepository.findAllCartProductsOfCart(
                        UUID.fromString(currentShoppingCart.shoppingCartId())));

    }

    @Override
    public void deactivate(String username) {
        checkUsernameForEmpty(username);
        Optional<ShoppingCart> shoppingCart =
                cartRepository.findByUsernameAndActivatedEqualsIgnoreCase(username, true);
        if (shoppingCart.isPresent()) {
            shoppingCart.get().setActivated(false);
        } else {
            throw new NotAuthorizedUserException("No cart for username: " + username);
        }
    }

    @Override
    @Transactional
    public ShoppingCartDto update(String username, Map<String, Long> products) {
        checkUsernameForEmpty(username);
        ShoppingCartDto currentShoppingCart = get(username);
        UUID cartId = UUID.fromString(currentShoppingCart.shoppingCartId());


        List<CartProduct> currentCartProducts = mapToCartProducts(cartId, currentShoppingCart.products());
        cartProductsRepository.deleteAll(currentCartProducts);

        List<CartProduct> newCartProducts = mapToCartProducts(cartId, products);
        List<CartProduct> savedCartProducts = cartProductsRepository.saveAll(newCartProducts);

        return cartMapper.toShoppingCartDto(cartMapper.toShoppingCart(currentShoppingCart),
                savedCartProducts);
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String username,
                                                 ChangeProductQuantityRequest changeProductQuantityRequest) {
        checkUsernameForEmpty(username);
        ShoppingCartDto currentShoppingCart = get(username);
        UUID cartId = UUID.fromString(currentShoppingCart.shoppingCartId());

        Optional<CartProduct> cartProduct = cartProductsRepository.findById(
                new CartProductId(cartId, UUID.fromString(changeProductQuantityRequest.productId())));

        if (cartProduct.isPresent()) {
            cartProduct.get().setQuantity(cartProduct.get().getQuantity());
            cartProductsRepository.save(cartProduct.get());
        } else {
            throw new NoProductsInShoppingCartException(
                    "Product with id " + changeProductQuantityRequest.productId() + " not found");
        }

        return get(username);
    }

    @Override
    public BookedProductsDto book(String username) { //TODO
        return null;
    }

    private void checkUsernameForEmpty(String username) {
        if(username == null || username.isBlank()) {
            throw new NotAuthorizedUserException("Username is empty");
        }
    }

    private List<CartProduct> mapToCartProducts(UUID cartId, Map<String, Long> products) {
        List<CartProduct> cartProducts = new ArrayList<>();
        for (Map.Entry<String, Long> entry : products.entrySet()) {
            cartProducts.add(new CartProduct(
                    new CartProductId(cartId, UUID.fromString(entry.getKey())), entry.getValue()));
        }
        return cartProducts;
    }

}
