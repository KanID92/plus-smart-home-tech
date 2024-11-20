package ru.yandex.practicum.commerce.shoppingCart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.commerce.shoppingCart.model.ShoppingCart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUsernameAndActivatedEqualsIgnoreCase(String username, boolean activated);

    boolean existsByUsernameAndActivatedEqualsIgnoreCase(String username, boolean activated);

}
