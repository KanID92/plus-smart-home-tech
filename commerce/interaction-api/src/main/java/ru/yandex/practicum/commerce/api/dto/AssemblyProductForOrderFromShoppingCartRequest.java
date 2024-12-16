package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

// Запрос на сбор заказа из ранее зарезервированных товаров из корзины к заказу.
public record AssemblyProductForOrderFromShoppingCartRequest(

        @NotBlank
        UUID shoppingCartId,

        @NotBlank
        UUID orderId
) {
}
