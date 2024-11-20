package ru.yandex.practicum.commerce.shoppingCart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.api.dto.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.shoppingCart.mapper.CartMapper;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;
import ru.yandex.practicum.commerce.shoppingCart.repository.CartProductsRepository;
import ru.yandex.practicum.commerce.shoppingCart.repository.CartRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneralCartService implements CartService {

    private final CartRepository cartRepository;
    private final CartProductsRepository cartProductsRepository;
    private final CartMapper cartMapper;

    @Override
    public ShoppingCartDto get(String username) {
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
        return null;
    }

    @Override
    public boolean deactivate(String username) {
        return false;
    }

    @Override
    public ShoppingCartDto update(String username, Map<String, Long> products) {
        return null;
    }

    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest changeProductQuantityRequest) {
        return null;
    }

    @Override
    public BookedProductsDto book(String username) {
        return null;
    }
}
