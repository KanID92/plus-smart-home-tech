package ru.yandex.practicum.commerce.delivery.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.delivery.mapper.AddressMapper;
import ru.yandex.practicum.commerce.delivery.mapper.DeliveryMapper;
import ru.yandex.practicum.commerce.delivery.model.Delivery;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GeneralDeliveryMapper implements DeliveryMapper {

    private final AddressMapper addressMapper;

    @Override
    public Delivery toDelivery(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .from_address(addressMapper.toAddress(deliveryDto.fromAddress()))
                .to_address(addressMapper.toAddress(deliveryDto.toAddress()))
                .orderId(UUID.fromString(deliveryDto.orderId()))
                .deliveryState(deliveryDto.deliveryState())
                .build();
    }

    @Override
    public DeliveryDto toDeliveryDto(Delivery delivery) {
        return DeliveryDto.builder()
                .fromAddress(addressMapper.toAddressDto(delivery.getFromAddress()))
                .toAddress(addressMapper.toAddressDto(delivery.getToAddress()))
                .orderId(delivery.getOrderId().toString())
                .deliveryState(delivery.getDeliveryState())
                .build();
    }
}
