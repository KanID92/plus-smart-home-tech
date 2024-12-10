package ru.yandex.practicum.commerce.api.dto;

public record CreateNewOrderRequest(
        ShoppingCartDto shoppingCart, // Корзина товаров в онлайн магазине.
        AddressDto deliveryAddress // Представление адреса в системе
) {
}
