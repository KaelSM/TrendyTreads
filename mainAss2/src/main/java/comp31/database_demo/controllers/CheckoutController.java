package comp31.database_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import comp31.database_demo.services.CheckoutService;
import comp31.database_demo.model.Checkout;
import comp31.database_demo.services.CartService;
import comp31.database_demo.model.Cart;
import comp31.database_demo.model.User;
import comp31.database_demo.services.UserService;

@Controller
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

     @PostMapping("/checkout")
public String processCheckout(@ModelAttribute Checkout checkout, HttpSession session, RedirectAttributes redirectAttributes) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        redirectAttributes.addFlashAttribute("checkoutError", "User is not recognized.");
        return "redirect:/login";
    }

    try {
        Cart cart = cartService.getUserCart(userId);
        checkoutService.processCheckout(
            cart.getId(),
            checkout.getName(),
            checkout.getAddress(),
            checkout.getEmail(),
            checkout.getPaymentMethod()
        );
        return "redirect:/orderMessage";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("checkoutError", "Checkout process failed: " + e.getMessage());
        return "redirect:/cart";
    }
}

    @GetMapping("/checkout")
    public String showCheckoutForm(Model model, HttpSession session) {
    String username = (String) session.getAttribute("username");
    if (username == null) {
        return "redirect:/login"; // Redirect to login if user not found
    }
    
    User currentUser = userService.findByUsername(username);
    if (currentUser == null) {
        // Handle case where the user is not found in the database
        return "redirect:/login";
    }
    
    Checkout checkout = new Checkout();
    checkout.setName(currentUser.getName()); // Ensure the currentUser object has the correct name
    model.addAttribute("checkout", checkout);
    
    return "checkout";
    }

    @GetMapping("/orderMessage")
    public String orderMessage() {
        return "orderMessage"; 
    }

}