package ru.yandex.practicum.commerce.order.service;

import ru.yandex.practicum.commerce.api.dto.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.ProductReturnRequest;

public interface OrderService {

    OrderDto get(String username);

    OrderDto create(CreateNewOrderRequest createNewOrderRequest);

    OrderDto returnOrder(ProductReturnRequest productReturnRequest);

    OrderDto pay(String orderId);

    OrderDto abortByPayment(String orderId);

    OrderDto deliver(String orderId);

    OrderDto abortByFail(String orderId);

    OrderDto complete(String orderId);

    OrderDto calculateOrderCost(String orderId);

    OrderDto calculateDeliveryCost(String orderId);

    OrderDto assembly(String orderId);

    OrderDto abortAssemblyByFail(String orderId);


}
