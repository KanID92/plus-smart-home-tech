package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@EqualsAndHashCode(of = "shoppingCartId")
@Builder
@RequiredArgsConstructor @AllArgsConstructor
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @Column(name = "shoppingCart_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shoppingCartId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "is_activated", nullable = false)
    private boolean isActivated;


}
