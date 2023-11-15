package comp31.database_demo.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import comp31.database_demo.model.Cart;
import comp31.database_demo.services.CartService;

import java.util.Optional;

@Controller
public class MainController {

    private final CartService cartService;

    public MainController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/{cartId}")
    public String viewCart(@PathVariable Integer cartId, Model model) {
        Optional<Cart> cart = cartService.findCartById(cartId);
        cart.ifPresent(value -> model.addAttribute("cart", value));
        return "cart";
    }

    @PostMapping("/cart/add/{cartId}/{productId}")
    public String addProductToCart(@PathVariable Integer cartId, @PathVariable Integer productId) {
        cartService.addProductToCart(cartId, productId);
        return "redirect:/cart/" + cartId;
    }

    @PostMapping("/cart/remove/{cartId}/{productId}")
    public String removeProductFromCart(@PathVariable Integer cartId, @PathVariable Integer productId) {
        cartService.removeProductFromCart(cartId, productId);
        return "redirect:/cart/" + cartId;
    }

}
