package comp31.database_demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Product;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.CartItemRepo;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

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
            // If cart item is found, update its status
            CartItem cartItem = cartItemOptional.get();
            cartItem.setStatus(newStatus);
            return cartItemRepo.save(cartItem); // Save the updated cart item
        } else {
            throw new EntityNotFoundException("CartItem not found with ID: " + cartItemId);
        }
    }

    public int getQuantityByItemIdAndStatus(Integer itemId, String status) {
        return cartItemRepo.getTotalQuantityByProductIdAndStatus(itemId, status);}

     public Double getTotalPriceByStatus(String status) {
        return cartItemRepo.getTotalPriceByStatus(status);
    }

    public void bulkUpdateCartItemStatus(List<Integer> cartItemIds, String newStatus) {
        // Update the status of multiple cart items
        cartItemIds.forEach(id -> updateCartItemStatus(id, newStatus));
    }

    public void updateCartItemQuantity(Integer cartItemId, Integer quantity) {
        cartItemRepo.updateQuantity(cartItemId, quantity);
    }
    
    public void deleteCartItem(Integer cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    public boolean checkCartItemAvailability(Integer cartItemId, Integer requiredQuantity) {
    
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            // If cart item is found, check if the required quantity is available
            CartItem cartItem = cartItemOptional.get();
            return cartItem.getQuantity() >= requiredQuantity;
        } else {
            throw new EntityNotFoundException("CartItem not found with ID: " + cartItemId);
        }
    }

    public double calculateTotalPrice(Integer cartItemId) {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            // If cart item is found, calculate the total price
            CartItem cartItem = cartItemOptional.get();
            return cartItem.getQuantity() * cartItem.getPrice();
        } else {
            throw new EntityNotFoundException("CartItem not found with ID: " + cartItemId);
        }
    }

    public CartItem addItemToCart(CartItem cartItem) {
      
        return cartItemRepo.save(cartItem);
    }

    public void removeItemFromCart(Integer cartItemId) {
        
        cartItemRepo.deleteById(cartItemId);
    }

    public List<CartItem> getCartItemsByUserId(User user) {
        return cartItemRepo.findByUser(user);
    }

    public Optional<CartItem> getCartItemDetails(Integer cartItemId) {
       
        return cartItemRepo.findById(cartItemId);
    }

    public void reserveCartItem(Integer cartItemId) {
        updateCartItemStatus(cartItemId, "Reserved");
    }



    
}