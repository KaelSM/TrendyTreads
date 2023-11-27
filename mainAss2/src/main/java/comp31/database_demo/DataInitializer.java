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

    private static final String[] COLORS = new String[] { "Red", "Blue", "Green", "Yellow", "Black", "White" };
    private static final Double[] SIZES = new Double[] { 36.0, 38.0, 40.0, 42.0, 44.0 }; 

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        String[] brands = { "Nike", "Adidas", "Reebok", "Puma", "Converse" };
        String[] types = { "Running Shoes", "Sneakers", "Trail Shoes", "Basketball Shoes", "Casual Shoes" };
        String[] descriptions = { "Comfortable and durable", "Lightweight and stylish", "Rugged and supportive",
                "High performance", "Classic design" };
        String[] categories = { "Sports", "Casual", "Outdoor", "Athletic", "Fashion" };
        Double[] sizes = { 7.0, 8.0, 9.0, 10.0, 11.0 };
        String[] colors = { "Black", "White", "Blue", "Red", "Green" };

        
        if (args.length == 0) {
            System.out.println("No arguments provided. Initializing with default values.");
        }
       
        Integer nItems = args.length > 0 ? Integer.parseInt(args[0]) : 10;

        try {
            for (int i = 0; i < nItems; i++) {
                
                Product product = new Product(brands[i], types[i], descriptions[i], categories[i], i % 2 == 0);
                productRepo.save(product);

                for (int j = 0; j < 5; j++) { 
                    String color = COLORS[random.nextInt(COLORS.length)];
                    Double size = SIZES[random.nextInt(SIZES.length)];
                    int price = 10 + random.nextInt(90); 

                    CartItem cartItem = new CartItem("CartItem" + i + "-" + j, 1, price, size, color, "Available",
                            product, null);
                    cartItemRepo.save(cartItem);
                }
            }

        } catch (Exception e) {
            System.err.println("Error during data initialization: " + e.getMessage());

        }
        
        User adminUser = new User("Admin User", "admin", "admin@example.com", "adminPass", "Admin Address",
                "1234567890", "ADMIN", "ACTIVE");
        User authUser = new User("Auth User", "auth", "auth@example.com", "authPass", "Auth Address", "0987654321",
                "AUTH", "ACTIVE");
        User guestUser = new User("Guest User", "guest", "guest@example.com", "guestPass", "Guest Address",
                "1230984567", "GUEST", "ACTIVE");

        userRepo.save(adminUser);
        userRepo.save(authUser);
        userRepo.save(guestUser);

        List<User> users = userRepo.findAll(); 
        List<Product> products = productRepo.findAll(); 

        for (Product product : products) {
            for (User user : users) {
                Feedback feedback = new Feedback("Feedback for " + product.getBrand() + " by " + user.getUsername(),
                        random.nextInt(5) + 1, 
                        user, product);
                feedbackRepo.save(feedback);
                
            }
        }
    }
}
