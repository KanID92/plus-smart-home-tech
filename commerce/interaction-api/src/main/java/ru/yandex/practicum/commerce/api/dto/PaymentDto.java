package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

@Builder
public record PaymentDto(
        String paymentId,
        Float totalPayment,
        Float deliveryTotal,
        Float feeTotal
) {
}
