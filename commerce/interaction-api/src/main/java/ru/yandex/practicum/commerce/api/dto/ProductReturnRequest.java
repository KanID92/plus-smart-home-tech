package ru.yandex.practicum.commerce.api.dto;

import java.util.Map;

public record ProductReturnRequest(
        String orderId,
        Map<String, Long> products
) {
}
