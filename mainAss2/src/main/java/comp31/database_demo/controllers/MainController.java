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
import comp31.database_demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

/* get mapping */

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

/*  post mapping */

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
        if (user != null && user.getPassword().equals(password)) { // Password should be hashed in a real app
            session.setAttribute("username", username);
            return "redirect:/profile";
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
    public String updateProfile(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            // Directly saving the user details
            userService.saveUser(user); 
            redirectAttributes.addFlashAttribute("profileMessage", "Profile updated successfully.");
            return "redirect:/home";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("profileError", "There was an error updating the profile.");
            return "redirect:/profile";
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

        
    
    

     


    
