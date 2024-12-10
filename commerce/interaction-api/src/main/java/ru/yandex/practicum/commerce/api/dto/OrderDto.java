package ru.yandex.practicum.commerce.api.dto;

import ru.yandex.practicum.commerce.api.dto.enums.OrderState;

import java.util.Map;
import java.util.UUID;

public record OrderDto(
        UUID orderId,
        UUID shoppingCartId,
        Map<UUID, Long> products,
        UUID deliveryId,
        OrderState state,
        Double deliveryVolume,
        Double deliveryWeight,
        Boolean fragile,
        Long totalPrice,
        Long deliveryPrice,
        Long productPrice
) {
}
