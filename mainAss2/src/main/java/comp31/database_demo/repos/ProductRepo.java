package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import comp31.database_demo.model.Product;
import jakarta.transaction.Transactional;

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
    
    List<Product> findByBrand(String brand);
    List<Product> findByType(String type);
    List<Product> findAll();
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE p.description LIKE %:keyword%")
    List<Product> findByDescriptionContaining(@Param("keyword") String keyword);

    @Modifying
    @Query("UPDATE Product p SET p.availability = :availability WHERE p.brand = :brand")
    void updateProductAvailabilityByBrand(@Param("brand") String brand, @Param("availability") Boolean availability);
    List<Product> findByAvailability(Boolean availability);

    @Query("SELECT COUNT(p), p.type FROM Product p GROUP BY p.type")
    List<Object[]> countProductsByType();

    @Query("SELECT p FROM Product p JOIN p.feedbacks f GROUP BY p.id ORDER BY COUNT(f) DESC")
    List<Product> findProductsWithMostFeedback();

    @Modifying
    @Query("UPDATE Product p SET p.category = :category WHERE p.id = :id")
    void updateProductCategory(@Param("id") Integer id, @Param("category") String category);

    @Query("SELECT COUNT(p), p.category FROM Product p GROUP BY p.category")
    List<Object[]> countProductsByCategory();

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteProductById(@Param("id") Integer id);
    
      
}