package ru.yandex.practicum.commerce.order.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.yandex.practicum.commerce.api.dto.enums.OrderState;

import java.util.UUID;

@Builder
@Getter @Setter @EqualsAndHashCode(of = "orderId") @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "shopping_cart_id")
    private UUID shoppingCartId;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

}
