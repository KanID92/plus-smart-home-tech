package ru.yandex.practicum.commerce.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.api.DeliveryClient;
import ru.yandex.practicum.commerce.api.ShoppingCartClient;
import ru.yandex.practicum.commerce.api.dto.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.api.dto.OrderDto;
import ru.yandex.practicum.commerce.api.dto.ProductReturnRequest;
import ru.yandex.practicum.commerce.api.dto.enums.OrderState;
import ru.yandex.practicum.commerce.order.mapper.AddressMapper;
import ru.yandex.practicum.commerce.order.mapper.OrderMapper;
import ru.yandex.practicum.commerce.order.model.Order;
import ru.yandex.practicum.commerce.order.repository.OrderRepository;
import ru.yandex.practicum.commerce.order.service.OrderService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeneralOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;

    private final ShoppingCartClient shoppingCartClient;
    private final DeliveryClient deliveryClient;

    @Override
    public OrderDto get(String username) { //TODO
        //1. Взять из shopping-cart корзину по имени через Feign для products
        //ShoppingCartDto shoppingCartDto = shoppingCartClient.get(username);
        //2. Взять из warehouse BookedProductsDto с полями deliveryWeight, fragile
        //BookedProductsDto bookedProductsDto =
        //3. Взять price из payment
        //4. Взять из базы Order с полями deliveryId, state
        //5. Собрать в Order, замапить в dto и вернуть
        return null;
    }

    @Override
    @Transactional
    public OrderDto create(CreateNewOrderRequest createNewOrderRequest) { //TODO не используются продукты и количество
        //1. Взять из warehouse BookedProductsDto с полями deliveryWeight, fragile
        //2. Взять price'ы из payment, с paymentId
        //3. Сохранить сведения о доставке в базе delivery. Получить от него deliveryId
        //4. Сохранить в базе с объединением.
        //5. Вернуть OrderDto
        //? Может быть адрес хранить в delivery?
        Order creatingOrder = Order.builder()
                .shoppingCartId(
                        UUID.fromString(
                                createNewOrderRequest.shoppingCart().shoppingCartId()))
                .state(OrderState.NEW)
                .address(
                        addressMapper.toAddress(
                                createNewOrderRequest.deliveryAddress()))
                .build();
        Order createdOrder = orderRepository.save(creatingOrder);
        return orderMapper.toOrderDto(createdOrder);
    }

    @Override
    @Transactional
    public OrderDto returnOrder(ProductReturnRequest productReturnRequest) {
        //1. Вызвать метод возврата в Warehouse.
        //2. Изменить статус в БД здесь
        //3. Собрать текущий Order из микросервисов
        //4. Вернуть OrderDto
        return null;
    }

    @Override
    public OrderDto pay(String orderId) {
        //1. Вызвать create у payment.
        //2. Изменить статус у заказа "оплачен" и сохранить paymentId
        //3. Собрать текущий Order из микросервисов
        //4. Вернуть OrderDto
        return null;
    }

    @Override
    public OrderDto abortByPayment(String orderId) {
        //1. Вызвать paymentFailed у payment.
        //2. Изменить статус у заказа "failed".
        //3. Собрать текущий Order из микросервисов
        //4. Вернуть OrderDto
        return null;
    }

    @Override
    public OrderDto deliver(String orderId) {
        //1. Вызвать метод у delivery.
        //2. Сохранить deliveryId в базе.
        //3. Поменять статус у заказа. На доставлен
        //4. Собрать текущий Order из микросервисов
        //5. Вернуть OrderDto
        return null;
    }

    @Override
    public OrderDto abortByFail(String orderId) {
        //1. Вызвать метод setFailedStatusToDelivery у Delivery
        //2. Изменить статус в БД
        return null;
    }

    @Override
    public OrderDto complete(String orderId) {
        //добавление проверок оплаты и доставки.
        //исправление статуса в ордере.
        return null;
    }

    @Override
    public OrderDto calculateOrderCost(String orderId) {
        //расчет
        return null;
    }

    @Override
    public OrderDto calculateDeliveryCost(String orderId) {
        //вызов метода в delivery
        //сборка и возвращение orderDto
        return null;
    }

    @Override
    public OrderDto assembly(String orderId) {
        //изменение статуса на собран.
        return null;
    }

    @Override
    public OrderDto abortAssemblyByFail(String orderId) {
        return null;
    }
}
