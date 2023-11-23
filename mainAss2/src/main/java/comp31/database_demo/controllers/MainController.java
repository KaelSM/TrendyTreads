package comp31.database_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import comp31.database_demo.model.*;
import comp31.database_demo.services.*;


import java.util.List;

@Controller
public class MainController {

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
    
    // BEGIN: be15d9bcejpp
    @GetMapping("/product")
    public String getProducts(Model model) {
       List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }
}
