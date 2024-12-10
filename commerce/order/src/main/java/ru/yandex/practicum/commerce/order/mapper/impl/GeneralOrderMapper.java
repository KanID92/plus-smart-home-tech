package ru.yandex.practicum.commerce.order.mapper.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.order.mapper.OrderMapper;
import ru.yandex.practicum.commerce.order.model.Order;

@Component
public class GeneralOrderMapper implements OrderMapper {
    @Override
    public Order toOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto toOrderDto(Order order) {
        return null;
    }
}
