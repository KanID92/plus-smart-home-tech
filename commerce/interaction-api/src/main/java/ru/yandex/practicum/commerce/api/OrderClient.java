package ru.yandex.practicum.commerce.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.api.dto.OrderDto;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient {

    @PostMapping("/assembly")
    OrderDto assembly(@RequestBody String orderId);

    @PostMapping("/delivery")
    OrderDto deliver(@RequestBody String orderId);

    @PostMapping("/payment")
    OrderDto pay(@RequestBody String orderId);

    @PostMapping("/completed")
    OrderDto complete(@RequestBody String orderId);

    @PostMapping("/delivery/failed")
    OrderDto abortDeliverByFail(@RequestBody String orderId);

    @PostMapping("/assembly/failed")
    OrderDto abortAssemblyByFail(@RequestBody String orderId);

    @PostMapping("/payment/failed")
    OrderDto abortOrderByPaymentFailed(@RequestBody String orderId);




}
