package ru.yandex.practicum.commerce.order.mapper;

import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.order.model.Order;


public interface OrderMapper {

    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDto(Order order);

}
