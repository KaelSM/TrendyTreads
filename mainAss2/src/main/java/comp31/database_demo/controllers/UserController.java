package comp31.database_demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import comp31.database_demo.model.User;
import comp31.database_demo.model.Checkout;
import comp31.database_demo.services.ProductService;
import comp31.database_demo.services.UserService;
import comp31.database_demo.services.BrandService;
import comp31.database_demo.services.CheckoutService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CheckoutService checkoutService;
    // Register user
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
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

            // Add a list of checkout orders to the model
            List<Checkout> orders = checkoutService.listAll(); // Assuming checkoutService has a method to list all checkouts
            model.addAttribute("orders", orders);

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
    
}