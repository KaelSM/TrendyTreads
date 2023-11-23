package comp31.database_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import comp31.database_demo.model.*;
import comp31.database_demo.repos.*;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final FeedbackRepo feedbackRepo;
    private final CartItemRepo cartItemRepo;

    public DataInitializer(ProductRepo productRepo, UserRepo userRepo,
                           OrderRepo orderRepo, FeedbackRepo feedbackRepo,
                           CartItemRepo cartItemRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.feedbackRepo = feedbackRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize Products
        Product product1 = new Product("BrandA", "Type1", "Description1", "Category1");
        Product product2 = new Product("BrandB", "Type2", "Description2", "Category2");
        productRepo.save(product1);
        productRepo.save(product2);

        // Initialize Users
        User user1 = new User("Name1", "email1@example.com", "password1", "Address1", "Phone1", "Role1", "Status1", null);
        userRepo.save(user1);

    }
    
}
