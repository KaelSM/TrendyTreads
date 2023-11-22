package comp31.a2.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import comp31.a2.model.entities.Order;
import comp31.a2.services.OrderService;

@Controller
public class MainController {

    OrderService orderService;

    @GetMapping("/uc1")
    public String getUseCase1() {
        return "usecase1";
    }

    @GetMapping("/checkout")
    public String getRoot(Model model) {
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "index";
    }


}
