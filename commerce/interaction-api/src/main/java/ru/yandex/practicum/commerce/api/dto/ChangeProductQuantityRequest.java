package ru.yandex.practicum.commerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record ChangeProductQuantityRequest( // Запрос на изменение количества единиц товара


        @NotBlank
        UUID productId, // Идентификатор товара

        @PositiveOrZero
        long newQuantity // Новое количество товара
) {
}
