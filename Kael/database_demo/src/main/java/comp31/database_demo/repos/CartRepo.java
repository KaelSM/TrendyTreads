package comp31.database_demo.repos;

import org.springframework.data.repository.CrudRepository;
import comp31.database_demo.model.Cart;
import comp31.database_demo.model.CartItem;

import java.util.List;

public interface CartRepo extends CrudRepository<Cart, Integer> {
    List<Cart> findByProductsId(Integer productId);

    List<Cart> findByUserId(Integer userId);

    List<Cart> findByProductsName(String name);

    List<Cart> findBySession(String session);

   
    
}