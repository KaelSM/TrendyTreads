package comp31.a2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import comp31.a2.services.UserService;

@Controller
@RequestMapping("/user")
public class UserWebController {

    @Autowired
    private UserService userService;
}
