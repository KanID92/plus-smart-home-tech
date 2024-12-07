package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

@Builder
public record ShippedToDeliveryRequest(
        String orderId,
        String deliveryId
) {
}
