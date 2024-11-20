package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @ToString
@Builder
@RequiredArgsConstructor @AllArgsConstructor
public class CartProduct {

    @EmbeddedId
    private CartProductId cartProductId;

    private long quantity;

}
