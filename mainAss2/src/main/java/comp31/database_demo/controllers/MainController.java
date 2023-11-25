package comp31.database_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import comp31.database_demo.model.*;
import comp31.database_demo.services.*;


import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final FeedbackService feedbackService;
    private final CartItemService cartItemService;

    public MainController(ProductService productService, UserService userService,
                          OrderService orderService, FeedbackService feedbackService,
                          CartItemService cartItemService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.feedbackService = feedbackService;
        this.cartItemService = cartItemService;
    }
    
    // BEGIN: be15d9bcejpp
    @GetMapping("/product")
    public String getProducts(@RequestParam(value = "productName", required = false) String productName, Model model) {
    List<Product> products;

    if (productName != null && !productName.isEmpty()) {
        // Assuming you have a method in productService to get products by name
        products = productService.getProductsByBrand(productName);
    } else {
        products = productService.getAllProducts();
    }

    model.addAttribute("products", products);
    return "productPage"; // Change to the name of your desired Thymeleaf template
    }
    
    @GetMapping("/signin")
    public String showSignInForm(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignIn(@ModelAttribute("user") User user) {
    
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {
            // If the user is admin, redirect to the admin page
            return "redirect:/admin";
        } else {
            // For non-admin users, redirect to the home page
            return "redirect:/home";
        }
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user) {
        // Save the user to the database
        return "redirect:/home";
    }

    @GetMapping("/product/{productId}")
    public String productPage(@PathVariable Integer productId, Model model) {
        // Load product data (assuming you have a method for this)
        Optional<Product> products = productService.getProductById(productId);

        // Load feedbacks for the product
        List<Feedback> feedbacks = feedbackService.getFeedbackByProductId(productId);

        // Add product and feedbacks to the model
        model.addAttribute("products", products);
        model.addAttribute("feedbacks", feedbacks);

        return "product"; // name of your product page HTML file
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product, Model model) {
        productService.addProduct(product);
        model.addAttribute("message", "Product added successfully");
        return "productManagement"; // Return to the management page
    }

    @PostMapping("/updateProduct/{productId}")
    public String updateProduct(@PathVariable Integer productId, @ModelAttribute Product product, Model model) {
        productService.updateProduct(productId, product);
        model.addAttribute("message", "Product updated successfully");
        return "productManagement";
    }

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Integer productId, Model model) {
        productService.deleteProduct(productId);
        model.addAttribute("message", "Product deleted successfully");
        return "productManagement";
    }

    @GetMapping("/cart/{orderId}")
    public String viewCart(@PathVariable Integer orderId, Model model )
    {
        List<CartItem> cartItems = cartItemService.getCartItemsByStatus("available");
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

      @PostMapping("/cart/add/{orderId}/{itemId}")
    public String addOrUpdateCartItems(@PathVariable int orderId, @PathVariable int numItem) {
        orderService.addOrUpdateCartItems(orderId, numItem);
        return "redirect:/cart/" + orderId;
    }

    @PostMapping("/cart/remove/{orderId}/{itemId}")
    public String removeCartItems(@PathVariable int orderId, @PathVariable int numItem) {
        orderService.removeCartItems(orderId, numItem);
        return "redirect:/cart/" + orderId;
    }

    @PostMapping("/cart/checkout/{orderId}")
    public String checkout(@PathVariable int orderId) {
        orderService.checkout(orderId);
        return "redirect:/cart/" + orderId;
    }
}
