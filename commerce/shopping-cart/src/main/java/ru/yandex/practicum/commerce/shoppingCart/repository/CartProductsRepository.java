package ru.yandex.practicum.commerce.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProduct;
import ru.yandex.practicum.commerce.shoppingCart.model.CartProductId;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProduct, CartProductId> {

    @Query(value = "SELECT cp " +
            "FROM carts_products cp " +
            "WHERE cp.shopping_cart_id = :shoppingCartId", nativeQuery = true)
    List<CartProduct> findAllCartProductsOfCart(@Param("shoppingCartId") UUID shoppingCartId);

}
