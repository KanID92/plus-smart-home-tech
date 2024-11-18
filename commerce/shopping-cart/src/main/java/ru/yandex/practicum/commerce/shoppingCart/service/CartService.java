package ru.yandex.practicum.commerce.shoppingCart.service;

import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;

public interface CartService {

    ShoppingCartDto get(String username);

}
