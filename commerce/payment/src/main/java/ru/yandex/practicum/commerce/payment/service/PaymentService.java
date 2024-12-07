package ru.yandex.practicum.commerce.payment.service;


import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.PaymentDto;

public interface PaymentService {

    PaymentDto create(OrderDto orderDto);

    float calculateTotalCost(OrderDto orderDto);

    float calculateProductCost(OrderDto orderDto);

    ResponseEntity<Void> refund(String orderId);

    void paymentFailed(String orderId);

}
