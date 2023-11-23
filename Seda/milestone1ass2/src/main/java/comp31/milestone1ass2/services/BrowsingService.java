package comp31.milestone1ass2.services;

import java.util.List;
import org.springframework.stereotype.Service;
import comp31.milestone1ass2.model.entities.Product;
import comp31.milestone1ass2.model.repositories.ProductRepository;
@Service
public class BrowsingService {
   ProductRepository productRepository;

   
    public BrowsingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    public List<Product> findProductsByPriceRange(Integer price1, Integer price2) {
        return productRepository.findByPriceBetween(price1, price2);
    }
 
}
