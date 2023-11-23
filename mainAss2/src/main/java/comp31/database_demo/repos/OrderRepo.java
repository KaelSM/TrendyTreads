package comp31.database_demo.repos;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

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

    
    void addOrderById(Integer id);
    void addOrderByIdAndUserId(Integer id, Integer userId);
    void addOrderByIdAndUserIdAndCartItemId(Integer id, Integer userId, Integer cartItemId);
    
    List<Order> findAll();
    List<Order> findAllByUserId(Integer userId);
    List<Order> findAllByCartItemId(Integer cartItemId);
    List<Order> findAllByCartItemAndUserId (Integer cartItemId, Integer userId);
    List<Order> findAllByUserIdAndCartItemId(Integer userId, Integer cartItemId);
    List<Order> findByPaypalId(String paypalId);
}
