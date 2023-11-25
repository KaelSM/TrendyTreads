package comp31.database_demo.repos;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Product;

/***
 * CartItemRepo is an interface that extends CrudRepository
 *  @param findAll() returns a list of all cartItems
 * @param findByStatusCartItems(String status) returns a list of all cartItems with the given status
 * @param updateByStatus(String status) updates the status of all cartItems with the given status
 * @param updateByStatusAndProductId(String status, Integer productId) updates the status of all cartItems with the given status and productId
 * @param removeByStatus(String status) removes all cartItems with the given status
 * @param addCartItemByProductIdAndStatus(Integer productId, String status) adds a cartItem with the given productId and status
 * 
 *  
 * */

public interface CartItemRepo extends CrudRepository<CartItem, Integer>{
    /*List<CartItem> findAll();
    List<CartItem>  findByStatusCartItems(String status);
    List<CartItem> findByNameAndProduct (String name, Product product );
    String updateByStatus (String status);
    String updateByStatusAndProductId (String status, Integer productId);
    void removeByStatus(String status);
    void addCartItemByProductIdAndStatus(Integer productId, String status);
    */
    List<CartItem> findByStatus(String status);
    List<CartItem> findByNameAndProduct(String name, Product product);

    // If you need to find by Product ID, ensure a corresponding property exists in CartItem
    List<CartItem> findByProductId(Integer productId);
}
