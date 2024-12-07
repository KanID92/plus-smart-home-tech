package ru.yandex.practicum.commerce.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.PaymentDto;
import ru.yandex.practicum.commerce.payment.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping
    public PaymentDto create(@RequestBody final OrderDto order) {
        log.info("==> POST /api/v1/payment Create payment for order: {}", order);
        PaymentDto payment = paymentService.create(order);
        log.info("<== POST /api/v1/payment Payment created: {}", payment);
        return payment;
    }

    @PostMapping("/totalCost")
    public Float calculateTotalCost(@RequestBody final OrderDto order) {
        log.info("==> POST /api/v1/payment/totalCost Calculate total cost for order: {}", order);
        Float totalCost = paymentService.calculateTotalCost(order);
        log.info("<== POST /api/v1/payment/totalCost Total cost calculated: {}", totalCost);
        return totalCost;
    }

    @PostMapping("/refund")
    public ResponseEntity<Void> refund(String orderId) {
        log.info("==> POST /api/v1/payment/refund Calculate product cost for order: {}", orderId);
        return paymentService.refund(orderId);
    }

    @PostMapping("/productCost")
    public Float calculateProductCost(@RequestBody final OrderDto order) {
        log.info("==> POST /api/v1/payment/productCost Calculate product cost for order: {}", order);
        Float productCost = paymentService.calculateProductCost(order);
        log.info("<== POST /api/v1/payment/productCost Product cost calculated: {}", productCost);
        return productCost;
    }

    @PostMapping("/failed")
    public void paymentFailed(String orderId) {
        log.info("==> POST /api/v1/payment/failed Payment failed processing: {}", orderId);
        paymentService.paymentFailed(orderId);
        log.info("<== POST /api/v1/payment/failed Payment failed: {}", orderId);
    }











}
