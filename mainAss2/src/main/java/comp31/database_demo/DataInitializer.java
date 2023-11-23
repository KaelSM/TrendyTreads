package comp31.database_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import comp31.database_demo.model.*;
import comp31.database_demo.repos.*;



@Component
public class DataInitializer implements CommandLineRunner {
    ProductRepo productRepo;
    UserRepo userRepo;
    OrderRepo orderRepo;
    FeedbackRepo feedbackRepo;
    CartItemRepo cartItemRepo;

    public DataInitializer(ProductRepo productRepo, UserRepo userRepo,
                           OrderRepo orderRepo, FeedbackRepo feedbackRepo,
                           CartItemRepo cartItemRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
        this.feedbackRepo = feedbackRepo;
        this.cartItemRepo = cartItemRepo;
    }
    
 //product 
    @Override
    public void run(String... args) throws Exception {
        Integer nItems = Integer.parseInt(args[0]);
        for (int i = 0; i < nItems; i++) {
            Product product = new Product("Brand"+i,"type"+i,"description"+i,"category"+i); 
            productRepo.save(product);
            System.out.println("Created Entity: " + product.getBrand());
      
        // Create CartItem
        String name = "CartItem" + i;
        Integer quantity = 1;  
        Integer price = 10 * i;  
        Double size = 1.0 + i;  
        String color = "Color" + i;  
        String status = "Status" + i;  
        Order order = null;  

        CartItem cartItem = new CartItem(name, quantity, price, size, color, status, product, order);
        cartItemRepo.save(cartItem);
        System.out.println("Created CartItem Entity for Product: " + product.getBrand());
        
        // Create and save Users
        User adminUser = new User("Admin User", "admin", "admin@example.com", "adminPass", "Admin Address", "1234567890", "ADMIN", "ACTIVE");
        User authUser = new User("Auth User", "auth", "auth@example.com", "authPass", "Auth Address", "0987654321", "AUTH", "ACTIVE");
        User guestUser = new User("Guest User", "guest", "guest@example.com", "guestPass", "Guest Address", "1230984567", "GUEST", "ACTIVE");

        userRepo.save(adminUser);
        userRepo.save(authUser);
        userRepo.save(guestUser);
    }
    }

  
        
        

    }
    

