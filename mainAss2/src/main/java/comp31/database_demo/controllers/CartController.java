package comp31.database_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import comp31.database_demo.services.CartService;
import comp31.database_demo.model.Cart;
import org.springframework.ui.Model;

@Controller
public class CartController{

    @Autowired
    CartService cartService;

    // Add product to cart 
    @PostMapping("/add-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        // Simulate getting the user ID (for example, setting a static user ID for the demo)
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L; // For the purpose of the demo, let's use '1L' as the default user ID
            session.setAttribute("userId", userId);
        }
        cartService.addProductToCart(userId, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        Cart cart = cartService.getUserCart(userId);
        double subtotal = cartService.calculateSubtotal(cart.getProducts());
        double tax = cartService.calculateTax(subtotal);
        double totalAmount = subtotal + tax;
        model.addAttribute("cart", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        model.addAttribute("totalAmount", totalAmount);
        return "cart";
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        cartService.updateProductQuantity(userId, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeProductFromCart(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        cartService.removeProductFromCart(userId, productId);
        return "redirect:/cart";
    }

    @GetMapping("/continue-shopping")
    public String continueShopping() {
        return "redirect:/product"; // Redirect to the product details page
    }

}