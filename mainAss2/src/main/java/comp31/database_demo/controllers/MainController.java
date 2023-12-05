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
    
    /*
    * TODO: this needs another look
    * something is wrong with the update_profile post mapping
    * it is not updating the user details
    * it is not redirecting to the profile page
    * it is not letting the user log in after updating the profile
    */
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

    @GetMapping("/admin/add")
    public String showAddForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandService.getAllBrands());
        return "admin/add"; // The view is within the 'admin' subdirectory
    }

    // Method to process adding a new brand
    @PostMapping("/admin/addBrand")
    public String addBrand(@ModelAttribute Brand brand, RedirectAttributes redirectAttributes) {
        brandService.saveBrand(brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brand added successfully!");
        return "redirect:/management"; // Redirect to the 'management' view
    }

    // Method to process adding a new product
    @PostMapping("/admin/addProduct")
    public String addProduct(@ModelAttribute Product product, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
        productService.saveProduct(product, brandId);
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/management"; // Correct redirect path
    }

    // Updating an existing brand
    @PostMapping("/updateBrand")
    public String updateBrand(@ModelAttribute Brand brand, RedirectAttributes redirectAttributes) {
        brandService.updateBrand(brand.getId(), brand);
        redirectAttributes.addFlashAttribute("successMessage", "Brand updated successfully!");
        return "redirect:/management";
    }

    // Updating an existing product
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam Long brandId, RedirectAttributes redirectAttributes) {
        productService.updateProduct(product.getId(), product, brandId);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        return "redirect:/management";
    }

    // Deleting an existing brand
    @GetMapping("/deleteBrand/{id}")
    public String deleteBrand(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        brandService.deleteBrand(id);
        redirectAttributes.addFlashAttribute("successMessage", "Brand and associated products deleted successfully!");
        return "redirect:/management";
    }

    // Deleting an existing product
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/management";
    }




/* product and brand end */

}

        
    
    

     


    
