package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

// Запрос на увеличение единиц товара по его идентификатору
public record AddProductToWarehouseRequest(

        @NotBlank
        UUID productId,

        long quantity

) {
}
