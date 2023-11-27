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
 *  @param findAll() returns a list of all orders
 * @param findAllByUserId(Integer userId) returns a list of all orders with the given userId
 * @param findAllByCartItemId(Integer cartItemId) returns a list of all orders with the given cartItemId
 * @param findAllByCartItemAndUserId (Integer cartItemId, Integer userId) returns a list of all orders with the given cartItemId and userId
 * @param findAllByUserIdAndCartItemId(Integer userId, Integer cartItemId) returns a list of all orders with the given userId and cartItemId
 * @param findByPaypalId(String paypalId) returns a list of all orders with the given paypalId
 * @param addOrderById(Integer id) adds an order with the given id
 * @param addOrderByIdAndUserId(Integer id, Integer userId) adds an order with the given id and userId
 * @param addOrderByIdAndUserIdAndCartItemId(Integer id, Integer userId, Integer cartItemId) adds an order with the given id, userId, and cartItemId
 * 
 *  
 * */

public interface OrderRepo extends CrudRepository<Order, Integer>{
    List<Order> findAllByUserId(Integer userId);
    List<Order> findByPaypalId(String paypalId);
    List<Order> findAll();

    @Query("SELECT o FROM Order o JOIN o.cartItems c WHERE c.id = :cartItemId")
    List<Order> findAllByCartItemId(@Param("cartItemId") Integer cartItemId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND :cartItemId MEMBER OF o.cartItems")
    List<Order> findAllByUserIdAndCartItemId(@Param("userId") Integer userId, @Param("cartItemId") Integer cartItemId);

     List<Order> findByStatus(String status);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void updateOrderStatus(@Param("id") Integer id, @Param("status") String status);

    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int countByUserId(Integer userId);

    @Modifying
    @Query("DELETE FROM Order o WHERE o.status = :status")
    void deleteOrdersByStatus(@Param("status") String status);

    @Query("SELECT o FROM Order o JOIN o.cartItems c WHERE c.product.id = :productId")
    List<Order> findByProductId(@Param("productId") Integer productId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    int countByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") String status);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id IN :orderIds")
    void bulkUpdateOrderStatus(@Param("orderIds") List<Integer> orderIds, @Param("status") String status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt = :date")
    int countByDate(@Param("date") LocalDate date);

    @Query("SELECT o FROM Order o JOIN o.cartItems c WHERE c.quantity > :quantity")
    List<Order> findOrdersWithItemQuantityGreaterThan(@Param("quantity") Integer quantity);

    @Query("SELECT o.user.id, COUNT(o) FROM Order o GROUP BY o.user.id ORDER BY COUNT(o) DESC")
    List<Object[]> findMostFrequentBuyers();

}
