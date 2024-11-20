package ru.yandex.practicum.commerce.shoppingCart.service;

import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;

import java.util.Map;

public class GeneralCartService implements CartService {



    @Override
    public ShoppingCartDto get(String username) {


        return null;
    }

    @Override
    public ShoppingCartDto addProducts(String username, Map<String, Integer> products) {
        return null;
    }

    @Override
    public boolean deactivate(String username) {
        return false;
    }

    @Override
    public ShoppingCartDto update(String username, Map<String, Integer> products) {
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
