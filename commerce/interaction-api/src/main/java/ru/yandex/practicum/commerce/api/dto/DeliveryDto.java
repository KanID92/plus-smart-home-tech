package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;
import ru.yandex.practicum.commerce.api.dto.enums.DeliveryState;

@Builder
public record DeliveryDto(
        AddressDto fromAddress,
        AddressDto toAddress,
        String orderId,
        DeliveryState deliveryState
) {
}
