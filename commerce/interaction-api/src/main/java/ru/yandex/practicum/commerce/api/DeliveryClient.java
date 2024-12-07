package ru.yandex.practicum.commerce.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.commerce.api.dto.DeliveryDto;
import ru.yandex.practicum.commerce.api.dto.OrderDto;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryClient {

    @PutMapping
    DeliveryDto create(DeliveryDto deliveryDto);

    @PostMapping("/successful")
    void setDeliveryStatusSuccessful(String orderId);

    @PostMapping("/picked")
    void setDeliveryStatusPicked(String orderId);

    @PostMapping("/failed")
    void setFailedStatusToDelivery(String orderId);

    @GetMapping("/cost")
    Float calculateDeliveryCost(OrderDto orderDto);

}
