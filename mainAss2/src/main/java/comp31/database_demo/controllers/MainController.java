package comp31.database_demo.controllers;


import org.hibernate.mapping.List;
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
import comp31.database_demo.model.Product;
import comp31.database_demo.model.User;
import comp31.database_demo.services.UserService;
import comp31.database_demo.services.BrandService;
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
        Brand brand = brandService.getBrandById(id);
        brand.setName(newName);
        brandService.saveBrand(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brand updated successfully!");
        return "redirect:/management";
    }

    // Update Product
    @GetMapping("/updateProduct/{id}")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("brands", brandService.getAllBrands()); // To populate the brands dropdown
        return "updateProductForm"; // You need to create this Thymeleaf view
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam String newName, @RequestParam double newPrice, @RequestParam int newStock, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id);
        product.setName(newName);
        product.setPrice(newPrice);
        product.setStock(newStock);
        productService.saveProduct(product, product.getBrand().getId()); // Ensure this method saves the product and sets the brand correctly (see ProductService.java)
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        return "redirect:/management";
    }

    // Delete Brand
    @GetMapping("/management/deleteBrand/{id}")
    public String deleteBrand(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            brandService.deleteBrandAndRelatedProducts(id); // Ensure this method deletes the brand and its related products
            redirectAttributes.addFlashAttribute("successMessage", "Brand and related products deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting brand: " + e.getMessage());
        }
        return "redirect:/management";
    }

    // Delete Product
    @GetMapping("/management/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/management";
    }

    // View Products
    @GetMapping("/product")
    public String showProductsPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product"; // The name of your products view template
    }



/* product and brand end */

}

        
    
    

     


    
