package ru.yandex.practicum.commerce.api.dto.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record AssemblyProductForOrderRequest(
        Map<String, Long> products,
        String orderId
) {
}
