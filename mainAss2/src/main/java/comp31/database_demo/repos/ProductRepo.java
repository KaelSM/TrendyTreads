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
 *  
 * */

public interface ProductRepo extends CrudRepository<Product, Integer> {
    
    // Find products by brand
    List<Product> findByBrand(String brand);
    
    // Find products by type
    List<Product> findByType(String type);
    
    // Find all products
    List<Product> findAll();
    
    // Find products by category using a custom query
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") String category);

    // Find products by description containing a keyword using a custom query
    @Query("SELECT p FROM Product p WHERE p.description LIKE %:keyword%")
    List<Product> findByDescriptionContaining(@Param("keyword") String keyword);

    // Update product availability by brand using a custom query
    @Modifying
    @Query("UPDATE Product p SET p.availability = :availability WHERE p.brand = :brand")
    void updateProductAvailabilityByBrand(@Param("brand") String brand, @Param("availability") Boolean availability);
    
    // Find products by availability
    List<Product> findByAvailability(Boolean availability);

    // Count products by type using a custom query
    @Query("SELECT COUNT(p), p.type FROM Product p GROUP BY p.type")
    List<Object[]> countProductsByType();

    // Find products with the most feedback using a custom query
    @Query("SELECT p FROM Product p JOIN p.feedbacks f GROUP BY p.id ORDER BY COUNT(f) DESC")
    List<Product> findProductsWithMostFeedback();

    // Update product category by ID using a custom query
    @Modifying
    @Query("UPDATE Product p SET p.category = :category WHERE p.id = :id")
    void updateProductCategory(@Param("id") Integer id, @Param("category") String category);

    // Count products by category using a custom query
    @Query("SELECT COUNT(p), p.category FROM Product p GROUP BY p.category")
    List<Object[]> countProductsByCategory();

    // Delete product by ID using a custom query
    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :id")
    void deleteProductById(@Param("id") Integer id);
    
      
}