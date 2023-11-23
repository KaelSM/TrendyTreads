package comp31.database_demo.services;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.CartItem;
import comp31.database_demo.repos.CartItemRepo;

/*
 * CartItemService is a service class that handles the business logic for the CartItem model
 * @param saveCartItem(CartItem cartItem) saves the given cartItem
 * @param deleteCartItem(Integer cartItemId) deletes the cartItem with the given cartItemId
 * 
 */

@Service
public class CartItemService {
    
    @Autowired
    private CartItemRepo cartItemRepo;

    public List<CartItem> getCartItemsByStatus(String status) {
        return cartItemRepo.findByStatus(status);
    }

    public CartItem addOrUpdateCartItem(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }

    public void removeCartItemsByStatus(String status) {
        // Fetch items and remove them
        List<CartItem> items = cartItemRepo.findByStatus(status);
        cartItemRepo.deleteAll(items);
    }



    public CartItem updateCartItemStatus(Integer cartItemId, String newStatus) {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setStatus(newStatus);
            return cartItemRepo.save(cartItem);
        }
        // Handle the case where CartItem is not found
        // This might involve throwing an exception or returning null
    }

    // Additional methods as needed
}