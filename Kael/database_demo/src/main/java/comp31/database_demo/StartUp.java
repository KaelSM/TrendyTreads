package comp31.database_demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.database_demo.model.Cart;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.CartRepo;
import comp31.database_demo.repos.ProductRepo;

@Component
public class StartUp implements CommandLineRunner {

    private final ProductRepo productRepo;
    private final CartRepo cartRepo;

    public StartUp(ProductRepo productRepo, CartRepo cartRepo) {
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creating sample products
        Product product1 = new Product("Product 1", 19.99);
        Product product2 = new Product("Product 2", 29.99);
        Product product3 = new Product("Product 3", 39.99);

        productRepo.save(product1);
        productRepo.save(product2);
        productRepo.save(product3);

        // Creating a sample cart and adding products to it
        Cart cart = new Cart();
        cart.addProduct(product1);
        cart.addProduct(product2);

        cartRepo.save(cart);

        // Creating another cart and adding products to it
        Cart cart2 = new Cart();
        cart2.addProduct(product2);
        cart2.addProduct(product3);
        
        cartRepo.save(cart2);
        
    }
}
