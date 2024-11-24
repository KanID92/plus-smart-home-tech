package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter @ToString
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "reserved_products")
public class ReservedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reserved_products_id")
    UUID reservedProductId;

    @Column(name = "shopping_cart_id", nullable = false)
    UUID shoppingCartId;

    @Column(name = "product_id", nullable = false)
    UUID productId;

    @Column(name = "reserved_quantity", nullable = false)
    long reservedQuantity;

}
