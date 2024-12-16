package ru.yandex.practicum.commerce.api.dto;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record ShoppingCartDto(

        UUID shoppingCartId, // Идентификатор корзины в БД

        Map<UUID, Long> products // Отображение идентификатора товара на отобранное количество.

) {
}
