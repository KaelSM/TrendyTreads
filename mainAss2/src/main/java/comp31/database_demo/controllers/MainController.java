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

    @GetMapping("/")
    public String home() {
        return "signin";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "homepage";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProductsWithPrice());
        return "products";
    }

    @GetMapping("/product/details/{productId}")
    public String productPage(@PathVariable Integer productId, Model model) {
        Optional<Product> product = productService.getProductById(productId);

        if (product.isPresent()) {
            List<Feedback> feedbacks = feedbackService.getFeedbackByProductId(productId);
            Set<String> availableColors = productService.getAvailableColors(productId);
            Set<Double> availableSizes = productService.getAvailableSizes(productId);

            model.addAttribute("product", product.get());
            model.addAttribute("feedbacks", feedbacks);
            model.addAttribute("availableColors", availableColors);
            model.addAttribute("availableSizes", availableSizes);

            // Assuming you have a method to get the price range
            model.addAttribute("cartItem", new CartItem());
            model.addAttribute("priceRange", productService.getPriceRange(productId));

            return "product-details";
        } else {
            return "productNotFound";
        }
    }
    

   @GetMapping("/signin")
    public String showSignInForm(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

   @PostMapping("/signin")
    public String processSignIn(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (user.getUsername().equals("admin") && user.getPassword().equals("adminPass")) {
            // If the user is admin, redirect to the admin page
            return "redirect:/admin";
        } else if (userService.existsByUsername(user.getUsername())) {
            // If the username and password match, redirect to the home page
            return "redirect:/";
        } else if (!userService.existsByUsername(user.getUsername())) {
            // Check if the username exists
            redirectAttributes.addFlashAttribute("errorMessage", "Wrong username");
            return "redirect:/signin";
        } else {
            // For other cases, assume the password is wrong
            redirectAttributes.addFlashAttribute("errorMessage", "Wrong password");
            return "redirect:/signin";
        }
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user, Model model) {
        // Save the user to the database
        user.setRole("auth"); // Set the user role to auth
        user.setStatus("active"); // Assuming there's a setStatus method
        userService.saveUser(user);
        
        model.addAttribute("message", "User signed up successfully");
    
        return "redirect:/home";
    }

    @GetMapping("/guest")
public String continueAsGuest() {
    // Logic for guest users
    return "redirect:/home";
}



    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "adminpage";
    }


    @PostMapping("/addProductForm")
    public String addProduct(@ModelAttribute Product product, Model model) {
    productService.addProduct(product);
    model.addAttribute("message", "Product added successfully");
    return "redirect:/products"; // Redirect to the products page
}


    @PostMapping("/updateProduct/{productId}")
    public String updateProduct(@PathVariable Integer productId, @ModelAttribute Product product, Model model) {
        productService.updateProduct(productId, product);
        model.addAttribute("message", "Product updated successfully");
        return "redirect:/productManagement";
    }

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Integer productId, Model model) {
        productService.deleteProduct(productId);
        model.addAttribute("message", "Product deleted successfully");
        return "redirect:/productManagement";
    }

    @GetMapping("/cart/add/{orderId}")
    public String viewCart(@PathVariable int orderId, Model model) {
        // Your logic to handle the view cart request
        model.addAttribute("orderId", orderId); // Add this to pass orderId to the template
        // Other model attributes
        return "cart"; // Replace with your Thymeleaf template name
    }

    @PostMapping("/cart/add/{orderId}")
    public ResponseEntity<String> addOrUpdateCartItems(@PathVariable int orderId, @RequestParam("numItem") int numItem) {
        // Your logic to add or update cart items
        return ResponseEntity.ok("Item added successfully");
    }

    @PostMapping("/cart/remove/{orderId}")
    public String removeCartItems(@PathVariable int orderId, @RequestParam("numItem") int numItem) {
    orderService.removeCartItems(orderId, numItem);
    return "redirect:/cart/" + orderId;
    }

    @PostMapping("/cart/checkout/{orderId}")
    public String checkout(@PathVariable int orderId) {
        orderService.checkout(orderId);
        return "redirect:/cart/" + orderId;
    }

    @GetMapping("/productManagement")
    public String productManagement(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("productForm", new ProductForm());
        return "productManagement";
    }

    // Method to handle the form submission
    /*@PostMapping("/addProduct")
    public String addProduct(@ModelAttribute ProductForm productForm) {
        Product product = new Product(productForm.getBrand(), 
                                      productForm.getType(), 
                                      productForm.getDescription(), 
                                      productForm.getCategory(), 
                                      true); // Assuming new products are available by default
        //productService.saveProduct(product);
        return "redirect:/admin/productManagement";
    }*/
}
