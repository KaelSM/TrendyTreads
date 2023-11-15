package comp31.database_demo.repos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import comp31.database_demo.model.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
   
    List<CartItem> findByCartId(Integer cartId);
    List<CartItem> findByCartUserId(Integer userId);
    List<CartItem> findByProductId(Integer productId);
    List<CartItem> findByCartIdAndProductId(Integer cartId, Integer productId);
    List<CartItem> findByQuantityGreaterThan(Integer quantity);

    Integer findTotalQuantityByProductId(Integer productId);

    
}
