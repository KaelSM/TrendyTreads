package comp31.database_demo.services;


import org.springframework.stereotype.Service;
import comp31.database_demo.model.Product;
import comp31.database_demo.repos.ProductRepo;

import java.util.List;
import java.util.Optional;

/*
 * ProductService is a service class that handles the business logic for the Product model
 * @param getProductById(Integer id) returns the product with the given id
 * @param getProductsByBrand(String brand) returns a list of all products with the given brand
 * @param saveProduct(Product product) saves the given product
 * @param deleteProduct(Integer id) deletes the product with the given id
 * 
 */

@Service
public class ProductService {
    private final ProductRepo productRepository;

    
    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return null;
    } 
}
