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

    private static final String[] COLORS = new String[] {"Red", "Blue", "Green", "Yellow", "Black", "White"};
    private static final Double[] SIZES = new Double[] {36.0, 38.0, 40.0, 42.0, 44.0}; // Example sizes

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        String[] brands = {"Nike", "Adidas", "Reebok", "Puma", "Converse"};
        String[] types = {"Running Shoes", "Sneakers", "Trail Shoes", "Basketball Shoes", "Casual Shoes"};
        String[] descriptions = {"Comfortable and durable", "Lightweight and stylish", "Rugged and supportive", "High performance", "Classic design"};
        String[] categories = {"Sports", "Casual", "Outdoor", "Athletic", "Fashion"};
        Double[] sizes = {7.0, 8.0, 9.0, 10.0, 11.0};
        String[] colors = {"Black", "White", "Blue", "Red", "Green"};

        // Check if arguments are provided
        if (args.length == 0) {
            //logger.warn("No arguments provided. Defaulting to 10 items.");
        }
        // Set default number of items to 10 if no argument is provided
        Integer nItems = args.length > 0 ? Integer.parseInt(args[0]) : 10;

        try {
            for (int i = 0; i < nItems; i++) {
                // Create products and save them
                Product product = new Product(brands[i], types[i], descriptions[i], categories[i], i % 2 == 0);
                productRepo.save(product);
                //logger.info("Created Entity: {}", product.getBrand());

                for (int j = 0; j < 5; j++) { // Create multiple cart items for each product
                    String color = COLORS[random.nextInt(COLORS.length)];
                    Double size = SIZES[random.nextInt(SIZES.length)];
                    int price = 10 + random.nextInt(90); // Random price between 10 and 100
        
                    CartItem cartItem = new CartItem("CartItem" + i + "-" + j, 1, price, size, color, "Available", product, null);
                    cartItemRepo.save(cartItem);
                }
            }

            // Create and save Users
            User adminUser = new User("Admin User", "admin", "admin@example.com", "adminPass", "Admin Address", "1234567890", "ADMIN", "ACTIVE");
            User authUser = new User("Auth User", "auth", "auth@example.com", "authPass", "Auth Address", "0987654321", "AUTH", "ACTIVE");
            User guestUser = new User("Guest User", "guest", "guest@example.com", "guestPass", "Guest Address", "1230984567", "GUEST", "ACTIVE");

            userRepo.save(adminUser);
            userRepo.save(authUser);
            userRepo.save(guestUser);

            List<User> users = userRepo.findAll(); // Assuming you have a method to get all users
            List<Product> products = productRepo.findAll(); // Assuming you have a method to get all products

            for (Product product : products) {
                for (User user : users) {
                    Feedback feedback = new Feedback("Feedback for " + product.getBrand() + " by " + user.getUsername(), 
                                                    random.nextInt(5) + 1, // Random rating from 1 to 5
                                                    user, product);
                    feedbackRepo.save(feedback);
                    //logger.info("Created Feedback for Product: {} by User: {}", product.getBrand(), user.getUsername());
                }
            }
        
        } catch (Exception e) {
            System.err.println("Error during data initialization: " + e.getMessage());
                   
                }
    }}
    

