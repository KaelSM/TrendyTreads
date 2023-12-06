package comp31.database_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Checkout;
import comp31.database_demo.model.Product;
import comp31.database_demo.model.User;
import comp31.database_demo.services.UserService;
import comp31.database_demo.services.BrandService;
import comp31.database_demo.services.CartService;
import comp31.database_demo.services.CheckoutService;
import comp31.database_demo.services.ProductService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CheckoutService checkoutService;
    

/* user contoller */

    // Register user
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }  

    // Login
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Home page
    @GetMapping("/home")
    public String home() {
        return "home";
    }
   
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.findByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);
                return "profile";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/management")
    public String showManagementPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userService.findByUsername(username);

        if (user != null && "ADMIN".equals(user.getRole())) {
            model.addAttribute("brands", brandService.getAllBrands());
            model.addAttribute("products", productService.getAllProducts());
            return "management"; // Render the management view for admin users
        } else {
            return "redirect:/login"; // Redirect non-admin users to login page
        }
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    // Delete user (add necessary security checks)
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/home";
    }

   
    
    @PostMapping("/perform_login")
    public String performLogin(@RequestParam String username, 
                            @RequestParam String password, 
                            HttpSession session, 
                            RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username); // Ensure this is the correct attribute
            // Check the user's role
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/management"; // Redirect admin users to management page
            } else {
                return "redirect:/home"; // Redirect regular users to home page
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Invalid username or password");
            return "redirect:/login";
        }
    }
    
    @PostMapping("/update_profile")
    public String updateProfile(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(user);
            session.setAttribute("username", user.getUsername()); // Update session if username changes
            redirectAttributes.addFlashAttribute("profileMessage", "Profile updated successfully.");
            return "redirect:/profile"; // Redirect to the profile page to see changes
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("profileError", "There was an error updating the profile.");
            return "redirect:/profile"; // Stay on profile page if there's an error
        }
    }
    
    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
            // Invalidate session or do other cleanup as necessary
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user.");
        }
        return "redirect:/login";
    }

/* user contoller end*/

/* product and brand start */

    // Add Brand
    @PostMapping("/addBrand")
    public String addBrand(@RequestParam String name, RedirectAttributes redirectAttributes) {
        Brand brand = new Brand();
        brand.setName(name);
        brandService.saveBrand(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brand added successfully!");
        return "redirect:/management";
    }

    // Add Product
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String name, @RequestParam double price, @RequestParam int stock, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        Brand brand = brandService.getBrandById(brandId);
        product.setBrand(brand);
        productService.saveProduct(product, brandId); // Ensure this method saves the product and sets the brand correctly (see ProductService.java
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/management";
    }

    // Update Brand
    @GetMapping("/updateBrand/{id}")
    public String showUpdateBrandForm(@PathVariable Long id, Model model) {
        Brand brand = brandService.getBrandById(id);
        model.addAttribute("brand", brand);
        return "updateBrandForm"; // You need to create this Thymeleaf view
    }

    @PostMapping("/updateBrand/{id}")
    public String updateBrand(@PathVariable Long id, @RequestParam String newName, RedirectAttributes redirectAttributes) {
    try {
        Brand brand = brandService.getBrandById(id);
        brand.setName(newName);
        brandService.saveBrand(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brand updated successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error updating brand");
    }
    return "redirect:/management";
    }


    // Update Product
    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String newName, @RequestParam double newPrice, @RequestParam int newStock, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
    try {
        Product product = productService.getProductById(id);
        product.setName(newName);
        product.setPrice(newPrice);
        product.setStock(newStock);
        productService.updateProduct(id, product, brandId); // Assuming this method updates the product and sets the brand correctly
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Error updating product");
    }
    return "redirect:/management";
    }

    // Delete Brand
    @PostMapping("/deleteBrand")
    public String deleteBrand(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteBrand(id);
            redirectAttributes.addFlashAttribute("successMessage", "Brand and associated products deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting brand");
        }
        return "redirect:/management";
    }

    // Delete Product
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product");
        }
        return "redirect:/management";
    }

    // View Products
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("product", productService.getAllProducts());
        return "product"; // The name of your products view template
    }

    @GetMapping("/productDetails/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        // The return statement here constructs the view name dynamically based on the product ID.
        // For example, if the product ID is 1, it will return "1" which should match "1.html" in the templates directory.
        return "productDetails"; // Just convert the ID to a string to match the template name
    }

/* product and brand end */

/* cart and checkout start*/

// Add product to cart 
@PostMapping("/add-to-cart")
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
        Cart cart = cartService.getUserCart(userId);
        model.addAttribute("cart", cart);
        return "cart"; // Name of the Thymeleaf template for the cart page
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

    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute Checkout checkout, HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("checkoutError", "User is not recognized.");
            return "redirect:/login";
        }

        try {
            Cart cart = cartService.getUserCart(userId); // Retrieve the cart associated with the user
            // Extract the necessary details from the checkout object and pass them to the processCheckout method
            checkoutService.processCheckout(
                cart.getId(), // Assuming cart ID is needed
                checkout.getName(),
                checkout.getAddress(),
                checkout.getEmail(),
                checkout.getPhone(),
                checkout.getCountry(),
                checkout.getPayMethord()
            );
            return "redirect:/order-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("checkoutError", "Checkout process failed: " + e.getMessage());
            return "redirect:/cart";
        }
    }

/* cart and checkout end*/

}

        
    
    

     


    
