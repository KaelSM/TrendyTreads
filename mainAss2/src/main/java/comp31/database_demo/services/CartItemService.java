package comp31.database_demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Product;
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
    public List<CartItem> findByProductBrand(String brand, Product product) {
        return cartItemRepo.findByProductBrand(brand,product);
    }
    
}