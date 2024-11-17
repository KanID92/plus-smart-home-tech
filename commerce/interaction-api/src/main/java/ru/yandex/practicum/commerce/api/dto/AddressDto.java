package ru.yandex.practicum.commerce.api.dto;

// Представление адреса в системе.
public record AddressDto(

        String country,
        String city,
        String street,
        String house,
        String flat

) {
}
