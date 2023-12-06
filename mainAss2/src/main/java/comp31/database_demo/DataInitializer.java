package comp31.database_demo;
import java.util.HashMap;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.database_demo.model.Brand;
import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Product;
import comp31.database_demo.model.Checkout;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.CartRepo;
import comp31.database_demo.services.BrandService;
import comp31.database_demo.services.CartService;
import comp31.database_demo.services.CheckoutService;
import comp31.database_demo.services.ProductService;
import comp31.database_demo.services.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CartRepo cartRepo;


    @Override
    public void run(String... args) throws Exception {

        // Create some users
        User admin = new User();
        admin.setUsername("admin");
        admin.setName("Administrator");
        admin.setPassword("adminpass"); 
        admin.setRole("ADMIN");
        userService.saveUser(admin);

        User user = new User();
        user.setUsername("user");
        user.setName("User");
        user.setPassword("userpass"); 
        user.setRole("USER");
        userService.saveUser(user);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setName("User2");
        user2.setPassword("userpass2");
        user2.setRole("USER");
        userService.saveUser(user2);

        // Create some brands

        Brand brand1 = new Brand();
        brand1.setName("DC");
        brandService.saveBrand(brand1);

        Brand brand2 = new Brand();
        brand2.setName("Timberland");
        brandService.saveBrand(brand2);

        Brand brand3 = new Brand();
        brand3.setName("Dr. Martens");
        brandService.saveBrand(brand3);

        Brand brand4 = new Brand();
        brand4.setName("Crocs");
        brandService.saveBrand(brand4);

        Brand brand5 = new Brand();
        brand5.setName("Nike");
        brandService.saveBrand(brand5);

        Brand brand6 = new Brand();
        brand6.setName("Puma");
        brandService.saveBrand(brand6);

        // Create some products

        Product product1 = new Product();
        product1.setName("COURT GRAFFIK");
        product1.setStock(100);
        product1.setPrice(90.00);
        product1.setBrand(brand1);
        productService.saveProduct(product1, brand1.getId());

        Product product2 = new Product();
        product2.setName("6-INCH PREMIUM WATERPROOF BOOTS");
        product2.setStock(30);
        product2.setPrice(198.00);
        product2.setBrand(brand2);
        productService.saveProduct(product2, brand2.getId());

        Product product3 = new Product();
        product3.setName("1460 SMOOTH LEATHER LACE UP BOOTS");
        product3.setStock(50);
        product3.setPrice(350.00);
        product3.setBrand(brand3);
        productService.saveProduct(product3, brand3.getId());

        Product product4 = new Product();
        product4.setName("KIDS' CLASSIC POKEMON CLOG");
        product4.setStock(20);
        product4.setPrice(45.00);
        product4.setBrand(brand4);
        productService.saveProduct(product4, brand4.getId());

        Product product5 = new Product();
        product5.setName("MANTECA 4 HI WR HIGH-TOP SHOES");
        product5.setStock(10);
        product5.setPrice(115.00);
        product5.setBrand(brand1);
        productService.saveProduct(product5, brand1.getId());

        Product product6 = new Product();
        product6.setName("WOODLAND BOOTS WINTER BOOTS");
        product6.setStock(80);
        product6.setPrice(125.00);
        product6.setBrand(brand1);
        productService.saveProduct(product6, brand1.getId());

        // Create some carts
        user = userService.findByUsername("user");
        product1 = productService.getProductById(1L);

        Cart cart1 = new Cart();
        cart1.setUser(user);
        Map<Product, Integer> productsInCart = new HashMap<>();
        productsInCart.put(product1, 2);
        cart1.setProducts(productsInCart);
        cartService.save(cart1); 

        user2 = userService.findByUsername("user2");
        product2 = productService.getProductById(2L);
        product3 = productService.getProductById(3L);

        Cart cart2 = new Cart();
        cart2.setUser(user2);
        Map<Product, Integer> productsInCart2 = new HashMap<>();
        productsInCart2.put(product2, 1);
        productsInCart2.put(product3, 5);
        cart2.setProducts(productsInCart2);
        cartService.save(cart2);

        // Create some checkouts
        Cart existingCart = cartRepo.findById(1L).orElseThrow(() -> new RuntimeException("Cart not found"));
        Checkout checkout = new Checkout();
        checkout.setCart(existingCart);
        checkout.setName("User Name");
        checkout.setAddress("1234 Main Street");
        checkout.setEmail("user@example.com");
        checkout.setPaymentMethod("Credit");
        checkoutService.saveCheckout(checkout); 

        Cart existingCart2 = cartRepo.findById(2L).orElseThrow(() -> new RuntimeException("Cart not found"));
        Checkout checkout2 = new Checkout();
        checkout2.setCart(existingCart2);
        checkout2.setName("User2 Name");
        checkout2.setAddress("1234 Main Street");
        checkout2.setEmail("user2@example.com");
        checkout2.setPaymentMethod("Paypal");
        checkoutService.saveCheckout(checkout2);

    }
}