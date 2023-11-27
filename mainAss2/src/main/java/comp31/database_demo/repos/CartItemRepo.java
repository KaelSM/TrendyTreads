package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Product;
import comp31.database_demo.model.User;

/***
 * CartItemRepo is an interface that extends CrudRepository
 * 
 * */
public interface CartItemRepo extends CrudRepository<CartItem, Integer> {

    /**
     * Deletes cart items by product ID.
     * 
     * @param productId the ID of the product
     */
    void deleteByProductId(Integer productId); 

    /**
     * Finds cart items by product ID.
     * 
     * @param productId the ID of the product
     * @return a list of cart items
     */
    List<CartItem> findByProductId(Integer productId);

    /**
     * Finds cart items by product ID and status.
     * 
     * @param productId the ID of the product
     * @param status the status of the cart items
     * @return a list of cart items
     */
    List<CartItem> findByProduct_IdAndStatus(Integer productId, String status);

    /**
     * Finds cart items by product ID and color.
     * 
     * @param productId the ID of the product
     * @param color the color of the cart items
     * @return a list of cart items
     */
    List<CartItem> findByProductIdAndColor(Integer productId, String color);

    /**
     * Finds cart items by product ID and size.
     * 
     * @param productId the ID of the product
     * @param size the size of the cart items
     * @return a list of cart items
     */
    List<CartItem> findByProductIdAndSize(Integer productId, Double size);

    /**
     * Finds cart items by user ID in the order.
     * 
     * @param userId the ID of the user
     * @return a list of cart items
     */
    @Query("SELECT ci FROM CartItem ci WHERE ci.order.user.id = :userId")
    List<CartItem> findByOrderUserId(@Param("userId") Integer userId);

    /**
     * Calculates the total price of cart items by status.
     * 
     * @param status the status of the cart items
     * @return the total price
     */
    @Query("SELECT SUM(c.price * c.quantity) FROM CartItem c WHERE c.status = :status")
    Double getTotalPriceByStatus(@Param("status") String status);

    /**
     * Calculates the total quantity of cart items by product ID and status.
     * 
     * @param productId the ID of the product
     * @param status the status of the cart items
     * @return the total quantity
     */
    @Query("SELECT SUM(c.quantity) FROM CartItem c WHERE c.product.id = :productId AND c.status = :status")
    Integer getTotalQuantityByProductIdAndStatus(@Param("productId") Integer productId, @Param("status") String status);
    
    /**
     * Finds cart items by product category.
     * 
     * @param category the category of the product
     * @return a list of cart items
     */
    @Query("SELECT c FROM CartItem c WHERE c.product.category = :category")
    List<CartItem> findByProductCategory(@Param("category") String category);

    /**
     * Counts the number of cart items by status.
     * 
     * @param status the status of the cart items
     * @return the count of cart items
     */
    int countByStatus(String status);

    /**
     * Finds cart items by price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return a list of cart items
     */
    List<CartItem> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Finds cart items by product brand.
     * 
     * @param brand the brand of the product
     * @return a list of cart items
     */
    @Query("SELECT c FROM CartItem c WHERE c.product.brand = :brand")
    List<CartItem> findByProductBrand(@Param("brand") String brand);

    /**
     * Updates the quantity of a cart item.
     * 
     * @param id the ID of the cart item
     * @param quantity the new quantity
     */
    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :id")
    void updateQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);

    /**
     * Finds cart items with quantity greater than a given value.
     * 
     * @param quantity the minimum quantity
     * @return a list of cart items
     */
    List<CartItem> findByQuantityGreaterThan(Integer quantity);

    /**
     * Finds cart items by status.
     * 
     * @param status the status of the cart items
     * @return a list of cart items
     */
    List<CartItem> findByStatus(String status);

    /**
     * Finds available sizes of a product by product ID.
     * 
     * @param productId the ID of the product
     * @return a list of available sizes
     */
    @Query("SELECT DISTINCT c.size FROM CartItem c WHERE c.product.id = :productId AND c.status = 'Available'")
    List<String> findAvailableSizesByProductId(@Param("productId") Integer productId);

    /**
     * Finds cart items by product ID and size.
     * 
     * @param productId the ID of the product
     * @param size the size of the cart items
     * @return a list of cart items
     */
    @Query("SELECT c FROM CartItem c WHERE c.product.id = :productId AND c.size = :size")
    List<CartItem> findByProductIdAndSize(@Param("productId") Integer productId, @Param("size") String size);

    /**
     * Updates the status of cart items by size and product ID.
     * 
     * @param size the size of the cart items
     * @param productId the ID of the product
     * @param newStatus the new status
     */
    @Modifying
    @Query("UPDATE CartItem c SET c.status = :newStatus WHERE c.size = :size AND c.product.id = :productId")
    void updateStatusBySizeAndProductId(@Param("size") String size, @Param("productId") Integer productId, @Param("newStatus") String newStatus);

}
