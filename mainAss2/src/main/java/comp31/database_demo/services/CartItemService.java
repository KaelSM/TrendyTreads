package comp31.database_demo.services;

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
    private final CartItemRepo CartItemRepo;

    public CartItemService(CartItemRepo CartItemRepo) {
        this.CartItemRepo = CartItemRepo;
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return CartItemRepo.save(cartItem);
    }

    public void deleteCartItem(Integer cartItemId) {
        CartItemRepo.deleteById(cartItemId);
    }

}