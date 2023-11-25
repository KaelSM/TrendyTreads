package comp31.database_demo;

import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import comp31.database_demo.model.*;
import comp31.database_demo.repos.*;


    @Component
    public class DataInitializer implements CommandLineRunner {
    // Repositories
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final OrderRepo orderRepo;
    private final FeedbackRepo feedbackRepo;
    private final CartItemRepo cartItemRepo;

    // Constructor
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
        // Check if arguments are provided
        if (args.length == 0) {
            logger.warn("No arguments provided. Defaulting to 10 items.");
        }

        // Set default number of items to 10 if no argument is provided
        Integer nItems = args.length > 0 ? Integer.parseInt(args[0]) : 10;

        try {
            for (int i = 0; i < nItems; i++) {
                // Create products and save them
                Product product = new Product("Brand" + i, "type" + i, "description" + i, "category" + i);
                productRepo.save(product);
                logger.info("Created Entity: {}", product.getBrand());

                // Create CartItems
                CartItem cartItem = new CartItem("CartItem" + i, 1, 10 * i, 1.0 + i, 
                                                "Color" + i, "Status" + i, product, null);
                cartItemRepo.save(cartItem);
                logger.info("Created CartItem Entity for Product: {}", product.getBrand());
            }

            // Create and save Users
            User adminUser = new User("Admin User", "admin", "admin@example.com", "adminPass", "Admin Address", "1234567890", "ADMIN", "ACTIVE");
            User authUser = new User("Auth User", "auth", "auth@example.com", "authPass", "Auth Address", "0987654321", "AUTH", "ACTIVE");
            User guestUser = new User("Guest User", "guest", "guest@example.com", "guestPass", "Guest Address", "1230984567", "GUEST", "ACTIVE");

            userRepo.save(adminUser);
            userRepo.save(authUser);
            userRepo.save(guestUser);

            Random random = new Random();
            List<User> users = userRepo.findAll(); // Assuming you have a method to get all users
            List<Product> products = productRepo.findAll(); // Assuming you have a method to get all products

            for (Product product : products) {
                for (User user : users) {
                    Feedback feedback = new Feedback("Feedback for " + product.getBrand() + " by " + user.getUsername(), 
                                                    random.nextInt(5) + 1, // Random rating from 1 to 5
                                                    user, product);
                    feedbackRepo.save(feedback);
                    logger.info("Created Feedback for Product: {} by User: {}", product.getBrand(), user.getUsername());
                }
            }
        
        } catch (Exception e) {
            logger.error("Error during data initialization: {}", e.getMessage());
        } 
    }
}
    

