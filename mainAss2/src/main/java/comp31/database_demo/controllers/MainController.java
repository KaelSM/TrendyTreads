package comp31.database_demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import comp31.database_demo.model.*;
import comp31.database_demo.services.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainController {

    // Constructor injection of services
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

    // Endpoint for the home page
    @GetMapping("/")
    public String home() {
        return "signin";
    }

    // Endpoint for showing the home page
    @GetMapping("/home")
    public String showHomePage() {
        return "homepage";
    }

    // Endpoint for listing all products
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProductsWithPrice());
        return "products";
    }

    // Endpoint for displaying product details
    @GetMapping("/product/details/{productId}")
    public String productPage(@PathVariable Integer productId, Model model) {
        // Retrieve product details, feedbacks, available colors, available sizes
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            List<Feedback> feedbacks = feedbackService.getFeedbackByProductId(productId);
            Set<String> availableColors = productService.getAvailableColors(productId);
            Set<Double> availableSizes = productService.getAvailableSizes(productId);

            // Add attributes to the model
            model.addAttribute("product", product.get());
            model.addAttribute("feedbacks", feedbacks);
            model.addAttribute("availableColors", availableColors);
            model.addAttribute("availableSizes", availableSizes);
            model.addAttribute("cartItem", new CartItem());
            model.addAttribute("priceRange", productService.getPriceRange(productId));

            return "product-details";
        } else {
            return "productNotFound";
        }
    }
    
    // Endpoint for showing the sign-in form
    @GetMapping("/signin")
    public String showSignInForm(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    // Endpoint for processing sign-in
    @PostMapping("/signin")
    public String processSignIn(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // Check if the user is an admin or a regular user
        if (user.getUsername().equals("admin") && user.getPassword().equals("adminPass")) {
            return "redirect:/admin";
        } else if (userService.existsByUsername(user.getUsername())) {
            return "redirect:/";
        } else if (!userService.existsByUsername(user.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Wrong username");
            return "redirect:/signin";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Wrong password");
            return "redirect:/signin";
        }
    }

    // Endpoint for showing the sign-up form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Endpoint for processing sign-up
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user, Model model) {
        // Set user role and status, save user
        user.setRole("auth"); 
        user.setStatus("active"); 
        userService.saveUser(user);
        
        model.addAttribute("message", "User signed up successfully");
    
        return "redirect:/home";
    }

    // Endpoint for continuing as a guest
    @GetMapping("/guest")
    public String continueAsGuest() {                                                                                
        return "homepage";
    }

    // Endpoint for the admin page
    @GetMapping("/admin")
    public String adminPage(Model model) {
        // Get all users and add them to the model
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "adminpage";
    }

    // Endpoint for adding a product form
    @PostMapping("/addProductForm")
    public String addProduct(@ModelAttribute Product product, Model model) {
        // Add product and display success message
        productService.addProduct(product);
        model.addAttribute("message", "Product added successfully");
        return "redirect:/products"; 
    }

    // Endpoint for showing the update product form
    @GetMapping("/admin/updateProduct/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        // Retrieve product by ID and add it to the model
        Optional<Product> productOptional = productService.getProductById(id);

        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "updateProduct";
        } else {
            model.addAttribute("errorMessage", "Product not found");
            return "errorPage";   
        }
    }

    // Endpoint for updating a product
    @PostMapping("/admin/updateProduct/{id}")
    public String updateProduct(@PathVariable Integer id, @ModelAttribute Product product, Model model) {
        // Update product and display success message
        productService.updateProduct(id, product);
        model.addAttribute("message", "Product updated successfully");
        return "redirect:/productManagement";
    }    

    // Endpoint for deleting a product
    @GetMapping("admin/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Integer productId, Model model) {
        // Delete product and linked items, display success message
        List<CartItem> items = cartItemService.getCartItemsByProductId(productId);
        for (CartItem item : items) {
            cartItemService.deleteCartItem(item.getId());
        }
        List<Feedback> feedbacks = feedbackService.getFeedbackByProductId(productId);
        for (Feedback feedback : feedbacks) {
            feedbackService.deleteFeedback(feedback.getId());
        }

        productService.deleteProduct(productId);
        
        model.addAttribute("message", "Product and linked items deleted successfully");
        return "productManagement";
    }

    // Endpoint for viewing the cart
    @GetMapping("/cart/add/{orderId}")
    public String viewCart(@PathVariable int orderId, Model model) {
        model.addAttribute("orderId", orderId); 
        return "cart"; 
    }

    // Endpoint for adding or updating cart items
    @PostMapping("/cart/add/{orderId}")
    public ResponseEntity<String> addOrUpdateCartItems(@PathVariable int orderId, @RequestParam("numItem") int numItem) {
        return ResponseEntity.ok("Item added successfully");
    }

    // Endpoint for removing cart items
    @PostMapping("/cart/remove/{orderId}")
    public String removeCartItems(@PathVariable int orderId, @RequestParam("numItem") int numItem) {
        // Remove cart items and redirect to cart page
        orderService.removeCartItems(orderId, numItem);
        return "redirect:/cart/" + orderId;
    }

    // Endpoint for cart checkout
    @PostMapping("/cart/checkout/{orderId}")
    public String checkout(@PathVariable int orderId) {
        // Perform checkout and redirect to cart page
        orderService.checkout(orderId);
        return "redirect:/cart/" + orderId;
    }

    // Endpoint for product management
    @GetMapping("/productManagement")
    public String productManagement(Model model, @RequestParam(required = false) Integer productId) {
        // Get all products and add them to the model
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        // Get product to update (if specified) and add it to the model
        Optional<Product> productToUpdate = productId != null ? productService.getProductById(productId) : Optional.empty();
        model.addAttribute("product", productToUpdate.orElseGet(Product::new));

        model.addAttribute("productForm", new ProductForm());
        return "productManagement";
    }

    // Endpoint for adding a product
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute ProductForm productForm) {
        // Create a new product, add it, and redirect to product management
        Product product = new Product(productForm.getBrand(), 
                                      productForm.getType(), 
                                      productForm.getDescription(), 
                                      productForm.getCategory(), 
                                      true); 
        productService.addProduct(product);
        return "redirect:/productManagement";
    }
}
