package ru.yandex.practicum.commerce.shoppingCart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.shoppingCart.service.CartService;

@RestController("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    CartService cartService;

    @GetMapping
    public ShoppingCartDto get(@RequestParam String username) {
        log.info("==> GET /api/v1/shopping-cart Getting shoppingCart by username {}", username);
        ShoppingCartDto receivedShoppingCart = cartService.get(username);
        log.info("<== GET /api/v1/shopping-cart Returning shoppingCart by username {}: {}",
                username, receivedShoppingCart);
        return receivedShoppingCart;
    }

    @PutMapping
    public ShoppingCartDto addNewProducts(@RequestParam String username,
                                          @RequestBody) {}



}
