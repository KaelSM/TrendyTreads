package comp31.database_demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.database_demo.model.User;
import comp31.database_demo.services.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setUsername("admin");
        admin.setName("Administrator");
        admin.setPassword("adminpass"); // You should encrypt this
        admin.setRole("ADMIN");
        userService.saveUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setName("User");
        user.setPassword("userpass"); // And this too
        user.setRole("USER");
        userService.saveUser(user);
    }
}