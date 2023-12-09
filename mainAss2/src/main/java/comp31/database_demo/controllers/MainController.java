package comp31.database_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    
    /**
     * Renders the login form.
     * 
     * @return The name of the login view template.
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * Invalidates the current session and renders the logout view.
     * 
     * @param session The HttpSession object.
     * @return The name of the logout view template.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        session = null;
        return "logout";
    }

    /**
     * Invalidates the current session and redirects to the logout endpoint.
     * 
     * @param session The HttpSession object.
     * @return A redirect to the logout endpoint.
     */
    @PostMapping("/logout")
    public String logoutPost(HttpSession session) {
        session.invalidate();
        session = null;
        return "redirect:/logout"; 
    }

    /**
     * Renders the home view.
     * 
     * @return The name of the home view template.
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

        
    
    

     


    
