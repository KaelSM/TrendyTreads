package comp31.milestone1ass2;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import comp31.milestone1ass2.model.entities.Product;
import comp31.milestone1ass2.model.repositories.ProductRepository;

@Component
public class InitProductData implements CommandLineRunner{
    ProductRepository productRepository;

 
    public InitProductData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Integer nItems = Integer.parseInt(args[0]);
        for (int i = 0; i < nItems; i++) {
            Product product = new Product("Product " + i, "Description for Product " + i, 10 * i); // Modify as needed
            productRepository.save(product);
            System.out.println("Created Entity: " + product.getName());
        }
    }
    
}
