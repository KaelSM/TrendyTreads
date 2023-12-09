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
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CheckoutService checkoutService;

    /**
     * Endpoint for the register form.
     * 
     * @param model The model object for the view.
     * @return The name of the register view.
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }  

    /**
     * Endpoint for displaying the user profile.
     * 
     * @param model The model object for the view.
     * @param session The HttpSession object for session management.
     * @return The name of the profile view if the user is logged in, otherwise redirects to the login page.
     */
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

    /**
     * Endpoint for displaying the management page.
     * 
     * @param model The model object for the view.
     * @param session The HttpSession object for session management.
     * @return The name of the management view if the user is an admin, otherwise redirects to the login page.
     */
    @GetMapping("/management")
    public String showManagementPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = userService.findByUsername(username);

        if (user != null && "ADMIN".equals(user.getRole())) {
            model.addAttribute("brands", brandService.getAllBrands());
            model.addAttribute("products", productService.getAllProducts());
            List<Checkout> checkouts = checkoutService.getAllCheckouts(); 
            model.addAttribute("checkouts", checkouts);
            List<Checkout> orders = checkoutService.listAll(); 
            model.addAttribute("orders", orders);

            return "management"; 
        } else {
            return "redirect:/login"; 
        }
    }

    /**
     * Endpoint for registering a new user.
     * 
     * @param user The User object containing the user details.
     * @return Redirects to the login page after successful registration.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    /**
     * Endpoint for deleting a user.
     * 
     * @param userId The ID of the user to delete.
     * @return Redirects to the home page after successful deletion.
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/home";
    }

    /**
     * Endpoint for performing user login.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     * @param session The HttpSession object for session management.
     * @param redirectAttributes The RedirectAttributes object for flash messages.
     * @return Redirects to the management page if the user is an admin, otherwise redirects to the home page.
     *         If login fails, redirects to the login page with an error message.
     */
    @PostMapping("/perform_login")
    public String performLogin(@RequestParam String username, 
                            @RequestParam String password, 
                            HttpSession session, 
                            RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username); 
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/management"; 
            } else {
                return "redirect:/home"; 
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Invalid username or password");
            return "redirect:/login";
        }
    }
    
    /**
     * Endpoint for updating the user profile.
     * 
     * @param user The User object containing the updated user details.
     * @param session The HttpSession object for session management.
     * @param redirectAttributes The RedirectAttributes object for flash messages.
     * @return Redirects to the profile page after successful profile update.
     *         If there is an error, redirects to the profile page with an error message.
     */
    @PostMapping("/update_profile")
    public String updateProfile(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(user);
            session.setAttribute("username", user.getUsername());
            redirectAttributes.addFlashAttribute("profileMessage", "Profile updated successfully.");
            return "redirect:/profile"; 
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("profileError", "There was an error updating the profile.");
            return "redirect:/profile"; 
        }
    }
    
    /**
     * Endpoint for deleting a user with the specified ID.
     * 
     * @param id The ID of the user to delete.
     * @param redirectAttributes The RedirectAttributes object for flash messages.
     * @return Redirects to the login page after successful deletion.
     *         If there is an error, redirects to the login page with an error message.
     */
    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user.");
        }
        return "redirect:/login";
    }
}