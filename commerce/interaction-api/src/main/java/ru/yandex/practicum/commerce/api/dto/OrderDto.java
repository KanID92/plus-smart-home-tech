package ru.yandex.practicum.commerce.api.dto;

import ru.yandex.practicum.commerce.api.dto.enums.OrderState;

import java.util.Map;

public record OrderDto(
        String orderId,
        String shoppingCartId,
        Map<String, Long> products,
        String deliveryId,
        OrderState state,
        Double deliveryVolume,
        Double deliveryWeight,
        Boolean fragile,
        Long totalPrice,
        Long deliveryPrice,
        Long productPrice
) {
}
