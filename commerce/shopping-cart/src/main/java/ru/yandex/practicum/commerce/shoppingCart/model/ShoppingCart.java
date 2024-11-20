package ru.yandex.practicum.commerce.shoppingCart.model;

import jakarta.persistence.*;

@Entity
public class ShoppingCart {

    @Id
    @Column(name = "shoppingCart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long shoppingCartId;





}
