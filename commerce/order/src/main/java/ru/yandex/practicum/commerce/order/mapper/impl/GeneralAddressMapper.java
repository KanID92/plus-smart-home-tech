package ru.yandex.practicum.commerce.order.mapper.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.AddressDto;
import ru.yandex.practicum.commerce.order.mapper.AddressMapper;
import ru.yandex.practicum.commerce.order.model.Address;

@Component
public class GeneralAddressMapper implements AddressMapper {
    @Override
    public Address toAddress(AddressDto addressDto) {
        return null;
    }

    @Override
    public AddressDto toAddressDto(Address address) {
        return null;
    }
}
