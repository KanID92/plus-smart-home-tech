package ru.yandex.practicum.commerce.api.dto;

public record ShoppingCartDto(

        String shoppingCartId, // Идентификатор корзины в БД

        long products // Отображение идентификатора товара на отобранное количество.

) {
}
