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
 * @param findAll() returns a list of all cartItems
 * @param findByStatusCartItems(String status) returns a list of all cartItems with the given status
 * @param updateByStatus(String status) updates the status of all cartItems with the given status
 * @param updateByStatusAndProductId(String status, Integer productId) updates the status of all cartItems with the given status and productId
 * @param removeByStatus(String status) removes all cartItems with the given status
 * @param addCartItemByProductIdAndStatus(Integer productId, String status) adds a cartItem with the given productId and status
 * 
 *  
 * */

public interface CartItemRepo extends CrudRepository<CartItem, Integer>{
    void deleteByProductId(Integer productId); 

    List<CartItem> findByProductId(Integer productId);

    List<CartItem> findByProduct_IdAndStatus(Integer productId, String status);

    List<CartItem> findByProductIdAndColor(Integer productId, String color);
    List<CartItem> findByProductIdAndSize(Integer productId, Double size);

    @Query("SELECT ci FROM CartItem ci WHERE ci.order.user.id = :userId")
    List<CartItem> findByOrderUserId(@Param("userId") Integer userId);

    @Query("SELECT SUM(c.price * c.quantity) FROM CartItem c WHERE c.status = :status")
    Double getTotalPriceByStatus(@Param("status") String status);

    @Query("SELECT SUM(c.quantity) FROM CartItem c WHERE c.product.id = :productId AND c.status = :status")
    Integer getTotalQuantityByProductIdAndStatus(@Param("productId") Integer productId, @Param("status") String status);
    
    @Query("SELECT c FROM CartItem c WHERE c.product.category = :category")
    List<CartItem> findByProductCategory(@Param("category") String category);

    int countByStatus(String status);

    List<CartItem> findByPriceBetween(Double minPrice, Double maxPrice);

     @Query("SELECT c FROM CartItem c WHERE c.product.brand = :brand")
     List<CartItem> findByProductBrand(@Param("brand") String brand);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :id")
    void updateQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);

    List<CartItem> findByQuantityGreaterThan(Integer quantity);

    List<CartItem> findByStatus(String status);

    @Query("SELECT DISTINCT c.size FROM CartItem c WHERE c.product.id = :productId AND c.status = 'Available'")
    List<String> findAvailableSizesByProductId(@Param("productId") Integer productId);

    @Query("SELECT c FROM CartItem c WHERE c.product.id = :productId AND c.size = :size")
    List<CartItem> findByProductIdAndSize(@Param("productId") Integer productId, @Param("size") String size);

    @Modifying
    @Query("UPDATE CartItem c SET c.status = :newStatus WHERE c.size = :size AND c.product.id = :productId")
    void updateStatusBySizeAndProductId(@Param("size") String size, @Param("productId") Integer productId, @Param("newStatus") String newStatus);

}
