package comp31.database_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    
    // Login
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session and redirect to the login page
        session.invalidate();
        session = null;
        return "logout";
    }

    @PostMapping("/logout")
    public String logoutPost(HttpSession session) {
    // Invalidate the session and redirect to the logout page
    session.invalidate();
    session = null;
    return "redirect:/logout"; // Redirect to the logout page
    }

    // Home page
    @GetMapping("/home")
    public String home() {
        return "home";
    }
   
    



}

        
    
    

     


    
