package ru.yandex.practicum.commerce.delivery.service;

import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.api.dto.OrderDto;

public interface DeliveryService {

    DeliveryDto create(DeliveryDto deliveryDto);

    void setSuccessfulToDelivery(String orderId);

    void setPickedToDelivery(String orderId);

    void setFailedToDelivery(String orderId);

    float calculateDeliveryCost(OrderDto orderDto);

}
