package ru.yandex.practicum.commerce.shoppingCart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.api.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.api.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.api.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.shoppingCart.service.CartService;

import java.util.Map;

@RestController("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    CartService cartService;

    @GetMapping
    public ShoppingCartDto get(@RequestParam String username) {
        log.info("==> GET /api/v1/shopping-cart Getting shoppingCart by user {}", username);
        ShoppingCartDto receivedShoppingCart = cartService.get(username);
        log.info("<== GET /api/v1/shopping-cart Returning shoppingCart by user {}: {}",
                username, receivedShoppingCart);
        return receivedShoppingCart;
    }

    @PutMapping
    public ShoppingCartDto addNewProductsToCart(@RequestParam String username,
                                          @RequestBody Map<String, Long> products) {
        log.info("==> PUT /api/v1/shopping-cart Adding new products {}, by user {}", products, username);
        ShoppingCartDto receivedShoppingCart = cartService.addProducts(username, products);
        log.info("<== PUT /api/v1/shopping-cart Returning shoppingCart by user {}: {}",
                username, receivedShoppingCart);
        return receivedShoppingCart;
    }

    @DeleteMapping
    public boolean deactivate(@RequestParam String username) {
        log.info("==> DELETE /api/v1/shopping-cart Deactivate cart of user {}", username);
        boolean isDeactivate = cartService.deactivate(username);
        log.info("<== DELETE /api/v1/shopping-cart Result of deactivating cart of user {}", username);
        return isDeactivate;
    }

    @PostMapping("/remove")
    public ShoppingCartDto updateCart(@RequestParam String username,
                                      @RequestBody Map<String, Long> products) {
        log.info("==> POST /api/v1/shopping-cart/remove Changing cart of user {}", username);
        ShoppingCartDto changedShoppingCart = cartService.update(username, products);
        log.info("<== POST /api/v1/shopping-cart/remove Changed cart of user {}: {}", username, changedShoppingCart);
        return changedShoppingCart;
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeQuantityOfProduct(
            @RequestParam String username, @RequestBody ChangeProductQuantityRequest changeProductQuantityRequest) {
        log.info("==> POST /api/v1/shopping-cart/change-quantity Changing quantity of product of user {}: {}",
                username, changeProductQuantityRequest);
        ShoppingCartDto changedShoppingCart = cartService.changeProductQuantity(username, changeProductQuantityRequest);
        log.info("<== POST /api/v1/shopping-cart/change-quantity Cart with changed quantity of product of user {}: {}",
                username, changeProductQuantityRequest);
        return changedShoppingCart;
    }

    @PostMapping("/booking")
    public BookedProductsDto book(@RequestParam String username) { //TODO
        log.info("==> POST /api/v1/shopping-cart/booking book cart of user {}",
                username);
        BookedProductsDto bookedProductsDto = cartService.book(username);
        throw new RuntimeException("Method is not develop");
    }





}
