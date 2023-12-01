package comp31.database_demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import comp31.database_demo.model.*;
import comp31.database_demo.repos.UserRepo;
import comp31.database_demo.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

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
    public String productPage(@PathVariable Integer productId, Model model, HttpSession session) {
        logger.info("productPage called with productId: {}", productId);
        // Retrieve product details, feedbacks, available colors, available sizes
        Optional<Product> product = productService.getProductById(productId);

        Integer userId = userService.getCurrentUserId();
            model.addAttribute("userId", userId);

        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!userId is: {}", userId);

        if (product.isPresent()) {
            List<Feedback> feedbacks = feedbackService.getFeedbackByProductId(productId);
            Set<String> availableColors = productService.getAvailableColors(productId);
            Set<Double> availableSizes = productService.getAvailableSizes(productId);

            //String username = (String) request.getSession().getAttribute("username");

            //User user = userService.findByUsername(username);
           //Integer userId = userService.getCurrentUserId();
           // model.addAttribute("userId", userId);

            if(userId == null) 
                logger.info("userId is null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            else
                logger.info("userId is not null!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            model.addAttribute("product", product.get());
            model.addAttribute("feedbacks", feedbacks);
            model.addAttribute("availableColors", availableColors);
            model.addAttribute("availableSizes", availableSizes);
            model.addAttribute("cartItem", new CartItem());
            model.addAttribute("priceRange", productService.getPriceRange(productId));

            String username = userService.getCurrentUsername();

            model.addAttribute("username", username);


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

    // Endpoint for showing the sign-up form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Endpoint for processing sign-up
    @PostMapping("/signin")
    public String processSignIn(@ModelAttribute("user") User userForm, RedirectAttributes redirectAttributes, HttpSession session) {
    User user = userRepo.findByUsername(userForm.getUsername()); // Use the repository to find the user

    if (user != null && user.getPassword().equals(userForm.getPassword())) {
        // Correct password, set the user ID in the session
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        logger.info("------------------------------------User ID {} set in session------------", user.getId());
        return "homepage";
    } else if (user == null) {
        // User does not exist
        redirectAttributes.addFlashAttribute("errorMessage", "Wrong username");
        return "redirect:/signin";
    } else {
        // Password incorrect
        redirectAttributes.addFlashAttribute("errorMessage", "Wrong password");
        return "redirect:/signin";
    }
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

   /* @PostMapping("/cart/add/{orderId}")
    public String addToCart(@PathVariable("orderId") int orderId, CartItem cartItem) {
        Order order = orderService.getOrderById(orderId); // Assuming you have a service to fetch the order
        cartItem.setOrder(order); // Set the order object instead of ID
        cartItemService.addOrUpdateCartItem(cartItem);
        return "redirect:/cart";
    } */

    //This method should handle displaying the form
    @GetMapping("/cart/add/{orderId}")
    public String viewCart(@PathVariable("orderId") Integer orderId, Model model) {
        if (orderId == null) {
            // Handle the case where orderId is null
            // Redirect to an error page or show an error message
            return "errorPage"; // Redirect to a custom error page or handle it differently
        }
        model.addAttribute("orderId", orderId); 
        return "cart"; 
    } 

    @GetMapping("/cart/update/{orderId}")
    public String updateCart(@PathVariable("orderId") Integer orderId, Model model) {
        if (orderId == null) {
            // Handle the case where orderId is null
            // Redirect to an error page or show an error message
            return "errorPage"; // Redirect to a custom error page or handle it differently
        }
        model.addAttribute("orderId", orderId); 
        return "cart"; 
    } 

    // This method should handle the form submission
    @PostMapping("/cart/add/{orderId}")
public String addToCart(@PathVariable("orderId") int orderId,
                        @Valid @ModelAttribute CartItem cartItem,
                        BindingResult result,
                        Model model) {
    if (result.hasErrors()) {
        // Send back to the form page if there are errors
        return "cart";
    }

    // Validate that the order exists
    Order order = orderService.getOrderById(orderId);
    if (order == null) {
        model.addAttribute("errorMessage", "Invalid order ID");
        return "cart";
    }

    Optional<Product> productOptional = productService.getProductById(cartItem.getProductId());
    if (productOptional.isPresent()) {
        Product product = productOptional.get();
        cartItem.setProduct(product);
        cartItem.setOrder(order);
        cartItemService.addOrUpdateCartItem(cartItem); // This method should handle adding the cartItem to the Order
    } else {
        model.addAttribute("errorMessage", "Invalid product ID");
        return "cart";
    }

    // Redirect to a page where the user can view their cart
    return "redirect:/cart/view";
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

    @PostMapping("/feedback/add")
    public String addFeedback(@RequestParam Integer productId, 
                              @RequestParam String feedbackMessage, 
                              @RequestParam Integer rating,
                              @RequestParam Integer userId,
                              Model model) { // Added Model parameter
                                logger.info("addFeedback called with productId: {}, feedbackMessage: {}, rating: {}, userId: {}", productId, feedbackMessage, rating, userId);

        Product product = productService.findById(productId);
        User user = userService.findById(userId);

        if (user == null) {
            // Handle the case where the user doesn't exist or userId is null
            return "errorPage"; // Redirect to an error page or handle accordingly
        }

        Feedback feedback = new Feedback("\"" + feedbackMessage + "\" by " + user.getUsername(), rating, user, product); // Used userId instead of user
        feedbackService.saveFeedback(feedback);

        return "redirect:/product-details";
    }


    @PostMapping("/feedback/update/{id}")
    public String updateFeedback(@PathVariable Integer id, @ModelAttribute Feedback feedback, HttpServletRequest request) {
    Feedback existingFeedback = feedbackService.getFeedbackById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback not found"));
    
    existingFeedback.setFeedbackMessage(feedback.getFeedbackMessage());
    existingFeedback.setRating(feedback.getRating());
    feedbackService.saveFeedback(existingFeedback);

    if (existingFeedback.getProduct() != null) {
        return "redirect:/product/details/" + existingFeedback.getProduct().getId();
    } else {
        // Handle the case when Product is null
        // For example, redirect to a generic page or show an error message
        return "redirect:/some-other-page";
    }
    }

    @GetMapping("/feedback/delete/{feedbackId}")
    public String deleteFeedback(@PathVariable Integer feedbackId, Model model) {
        feedbackService.deleteFeedback(feedbackId);
        // Additional logic and model attributes as needed
        return "redirect:/feedbackManagement"; // Redirect to the appropriate view
    }

    @GetMapping("/feedback/update/{id}")
    public String showUpdateFeedbackForm(@PathVariable Integer id, Model model) {
    Optional<Feedback> feedbackOptional = feedbackService.getFeedbackById(id);

    if (feedbackOptional.isPresent()) {
        model.addAttribute("feedback", feedbackOptional.get());
        return "updateFeedback"; // Name of the Thymeleaf template
    } else {
        model.addAttribute("errorMessage", "Feedback not found");
        return "errorPage"; // Or any error handling page you have
    }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUserDetails(id, userDetails);
        return ResponseEntity.ok(updatedUser);
}

    @GetMapping("/profile")
    public String userProfile(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // Handle not logged in user, maybe redirect to login page
            return "redirect:/login";
        }

        User user = userService.findById(userId);
        if (user == null) {
            // Handle user not found scenario
            return "userNotFound";
        }

        model.addAttribute("user", user);
        return "userProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute User user, HttpSession session) {
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null || !userId.equals(user.getId())) {
        // Handle unauthorized access
        return "errorPage";
    }

    userService.updateUserDetails(userId, user);
    return "redirect:/profile";
    }

    @DeleteMapping("/deleteProfile/{userId}")
public String deleteProfile(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {
    if (userId == null) {
        redirectAttributes.addFlashAttribute("errorMessage", "Invalid user ID");
        return "redirect:/errorPage";
    }

    User user = userService.findById(userId);
    if (user == null) {
        redirectAttributes.addFlashAttribute("errorMessage", "User not found");
        return "redirect:/errorPage";
    }

    try {
        List<Feedback> feedbacks = feedbackService.getFeedbackByUserId(user);
        for (Feedback feedback : feedbacks) {
            feedbackService.deleteFeedback(feedback.getId());
        }
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("successMessage", "Profile deleted successfully");
        return "redirect:/"; 
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error occurred while deleting the profile");
        return "redirect:/errorPage";
    }
}



}
