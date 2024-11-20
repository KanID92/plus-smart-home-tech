package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductId implements Serializable {
    private Long shoppingCartId;
    private Long productId;
}
