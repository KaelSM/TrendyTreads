package comp31.database_demo.repos;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import comp31.database_demo.model.Product;

/***
 * ProductRepo is an interface that extends CrudRepository
 *  @param findAll() returns a list of all products
 * @param findByBrand(String brand) returns a list of all products with the given brand
 * @param findByType(String type) returns a list of all products with the given type
 * @param removeByBrand(String brand) removes all products with the given brand
 * @param removeById(int id) removes the product with the given id
 * @param addProductByBrandAndTypeAndDescriptionAndCategory(String brand, String type, String description, String category) adds a product with the given brand, type, description, and category
 *  
 * */

public interface ProductRepo extends CrudRepository<Product, Integer> {
    
    List<Product> findAll();
    List<Product> findByBrand(String brand);
    List<Product> findByType(String type);
    void removeByBrand(String brand);
    void removeById(int id);
    Product save(Product product);
}