package comp31.database_demo.repos;

import org.springframework.data.repository.CrudRepository;
import comp31.database_demo.model.Product;
import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Integer> {
    // Find products by their name
    List<Product> findByName(String name);

    // Find products within a specified price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Find products by size
    List<Product> findBySize(String size);

    // Find products by brand
    List<Product> findByBrand(String brand);

    // Find products by color
    List<Product> findByColor(String color);

    List<Product> findProductsMostAddedToCart();
    
}
