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
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * Endpoint to add a product to the cart.
     * 
     * @param productId the ID of the product to add
     * @param quantity  the quantity of the product to add
     * @param session   the HttpSession object
     * @return          the redirect URL to the cart page
     */
    @PostMapping("/add-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            userId = 1L;
            session.setAttribute("userId", userId);
        }
        cartService.addProductToCart(userId, productId, quantity);
        return "redirect:/cart";
    }

    /**
     * Endpoint to view the cart.
     * 
     * @param model   the Model object
     * @param session the HttpSession object
     * @return        the view name for the cart page
     */
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

    /**
     * Endpoint to update the quantity of a product in the cart.
     * 
     * @param productId the ID of the product to update
     * @param quantity  the new quantity of the product
     * @param session   the HttpSession object
     * @return          the redirect URL to the cart page
     */
    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        cartService.updateProductQuantity(userId, productId, quantity);
        return "redirect:/cart";
    }

    /**
     * Endpoint to remove a product from the cart.
     * 
     * @param productId the ID of the product to remove
     * @param session   the HttpSession object
     * @return          the redirect URL to the cart page
     */
    @PostMapping("/cart/remove")
    public String removeProductFromCart(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        cartService.removeProductFromCart(userId, productId);
        return "redirect:/cart";
    }

    /**
     * Endpoint to continue shopping.
     * 
     * @return the redirect URL to the product page
     */
    @GetMapping("/continue-shopping")
    public String continueShopping() {
        return "redirect:/product";
    }

}