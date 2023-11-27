package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import comp31.database_demo.model.Order;

/***
 * OrderRepo is an interface that extends CrudRepository
 * 
 *  
 * */

public interface OrderRepo extends CrudRepository<Order, Integer>{
    // Retrieves all orders by user ID
    List<Order> findAllByUserId(Integer userId);
    
    // Retrieves all orders by PayPal ID
    List<Order> findByPaypalId(String paypalId);
    
    // Retrieves all orders
    List<Order> findAll();

    // Retrieves all orders by cart item ID
    @Query("SELECT o FROM Order o JOIN o.cartItems c WHERE c.id = :cartItemId")
    List<Order> findAllByCartItemId(@Param("cartItemId") Integer cartItemId);

    // Retrieves all orders by user ID and cart item ID
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND :cartItemId MEMBER OF o.cartItems")
    List<Order> findAllByUserIdAndCartItemId(@Param("userId") Integer userId, @Param("cartItemId") Integer cartItemId);

    // Retrieves all orders by status
    List<Order> findByStatus(String status);

    // Updates the status of an order
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void updateOrderStatus(@Param("id") Integer id, @Param("status") String status);

    // Retrieves all orders between two dates
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Counts the number of orders by user ID
    int countByUserId(Integer userId);

    // Deletes orders by status
    @Modifying
    @Query("DELETE FROM Order o WHERE o.status = :status")
    void deleteOrdersByStatus(@Param("status") String status);

    // Retrieves all orders by product ID
    @Query("SELECT o FROM Order o JOIN o.cartItems c WHERE c.product.id = :productId")
    List<Order> findByProductId(@Param("productId") Integer productId);

    // Counts the number of orders by user ID and status
    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    int countByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") String status);

    // Updates the status of multiple orders
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id IN :orderIds")
    void bulkUpdateOrderStatus(@Param("orderIds") List<Integer> orderIds, @Param("status") String status);

    // Counts the number of orders by date
    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt = :date")
    int countByDate(@Param("date") LocalDate date);

    // Retrieves all orders with item quantity greater than a specified quantity
    @Query("SELECT o FROM Order o JOIN o.cartItems c WHERE c.quantity > :quantity")
    List<Order> findOrdersWithItemQuantityGreaterThan(@Param("quantity") Integer quantity);

    // Retrieves the most frequent buyers, ordered by the number of orders
    @Query("SELECT o.user.id, COUNT(o) FROM Order o GROUP BY o.user.id ORDER BY COUNT(o) DESC")
    List<Object[]> findMostFrequentBuyers();

}
