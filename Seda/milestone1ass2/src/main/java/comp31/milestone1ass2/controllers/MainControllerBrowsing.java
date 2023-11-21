package comp31.milestone1ass2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControllerBrowsing {
    
    @GetMapping("/browsing")
    public String getBrowsing() {
        return "browsing";
    }
}
