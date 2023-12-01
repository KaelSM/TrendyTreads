package comp31.database_demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Order;
import comp31.database_demo.model.Product;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.CartItemRepo;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

/*
 * This class provides the service methods for managing cart items.
 */
@Service
public class CartItemService {
    
    @Autowired
    private CartItemRepo cartItemRepo;

    private OrderService orderService;

    /**
     * Retrieves a list of cart items based on the specified status.
     * 
     * @param status The status of the cart items to retrieve.
     * @return A list of cart items with the specified status.
     */
    public List<CartItem> getCartItemsByStatus(String status) {
        return cartItemRepo.findByStatus(status);
    }

    /**
     * Adds or updates a cart item.
     * 
     * @param cartItem The cart item to add or update.
     * @return The added or updated cart item.
     */
    public CartItem addOrUpdateCartItem(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }

    /**
     * Removes cart items with the specified status.
     * 
     * @param status The status of the cart items to remove.
     */
    public void removeCartItemsByStatus(String status) {
        List<CartItem> items = cartItemRepo.findByStatus(status);
        cartItemRepo.deleteAll(items);
    }

    /**
     * Updates the status of a cart item.
     * 
     * @param cartItemId The ID of the cart item to update.
     * @param newStatus The new status of the cart item.
     * @return The updated cart item.
     * @throws EntityNotFoundException If the cart item with the specified ID is not found.
     */
    public CartItem updateCartItemStatus(Integer cartItemId, String newStatus) {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setStatus(newStatus);
            return cartItemRepo.save(cartItem); 
        } else {
            throw new EntityNotFoundException("CartItem not found with ID: " + cartItemId);
        }
    }

    /**
     * Retrieves the quantity of a cart item based on the item ID and status.
     * 
     * @param itemId The ID of the cart item.
     * @param status The status of the cart item.
     * @return The quantity of the cart item.
     */
    public int getQuantityByItemIdAndStatus(Integer itemId, String status) {
        return cartItemRepo.getTotalQuantityByProductIdAndStatus(itemId, status);
    }

    /**
     * Retrieves the total price of cart items with the specified status.
     * 
     * @param status The status of the cart items.
     * @return The total price of the cart items.
     */
    public Double getTotalPriceByStatus(String status) {
        return cartItemRepo.getTotalPriceByStatus(status);
    }

    /**
     * Updates the status of multiple cart items.
     * 
     * @param cartItemIds The IDs of the cart items to update.
     * @param newStatus The new status of the cart items.
     */
    public void bulkUpdateCartItemStatus(List<Integer> cartItemIds, String newStatus) {
        // Update the status of multiple cart items
        cartItemIds.forEach(id -> updateCartItemStatus(id, newStatus));
    }

    /**
     * Updates the quantity of a cart item.
     * 
     * @param cartItemId The ID of the cart item to update.
     * @param quantity The new quantity of the cart item.
     */
    public void updateCartItemQuantity(Integer cartItemId, Integer quantity) {
        cartItemRepo.updateQuantity(cartItemId, quantity);
    }
    
    /**
     * Deletes a cart item.
     * 
     * @param cartItemId The ID of the cart item to delete.
     */
    public void deleteCartItem(Integer cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    /**
     * Checks if a cart item is available in the required quantity.
     * 
     * @param cartItemId The ID of the cart item to check.
     * @param requiredQuantity The required quantity of the cart item.
     * @return true if the cart item is available in the required quantity, false otherwise.
     * @throws EntityNotFoundException If the cart item with the specified ID is not found.
     */
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

    /**
     * Calculates the total price of a cart item.
     * 
     * @param cartItemId The ID of the cart item.
     * @return The total price of the cart item.
     * @throws EntityNotFoundException If the cart item with the specified ID is not found.
     */
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

    /**
     * Adds an item to the cart.
     * 
     * @param cartItem The cart item to add.
     * @return The added cart item.
     */
    public CartItem addItemToCart(CartItem cartItem) {
        return cartItemRepo.save(cartItem);
    }

    /**
     * Removes an item from the cart.
     * 
     * @param cartItemId The ID of the cart item to remove.
     */
    public void removeItemFromCart(Integer cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }

    /**
     * Retrieves the details of a cart item.
     * 
     * @param cartItemId The ID of the cart item.
     * @return An Optional containing the cart item if found, or an empty Optional if not found.
     */
    public Optional<CartItem> getCartItemDetails(Integer cartItemId) {
        return cartItemRepo.findById(cartItemId);
    }

    /**
     * Retrieves a list of cart items based on the product ID.
     * 
     * @param productId The ID of the product.
     * @return A list of cart items with the specified product ID.
     */
    public List<CartItem> getCartItemsByProductId(Integer productId) {
        return cartItemRepo.findByProduct_Id(productId);
    }

    /**
     * Reserves a cart item by updating its status to "Reserved".
     * 
     * @param cartItemId The ID of the cart item to reserve.
     */
    public void reserveCartItem(Integer cartItemId) {
        updateCartItemStatus(cartItemId, "Reserved");
    }

    /**
     * Retrieves a cart item by its ID.
     * 
     * @param id The ID of the cart item.
     * @return The cart item with the specified ID, or null if not found.
     */
    public CartItem getCartItemById(Integer id) {
        return cartItemRepo.findById(id).orElse(null);
    }

    public void addCartItemToOrder(CartItem cartItem, Order order) {
        cartItem.setOrder(order);
        cartItemRepo.save(cartItem);
    }

    public Order checkAndCreateOrder(Integer userId) {
        // Assuming you have a method to get the latest or current order for a user
        Order currentOrder = orderService.getCurrentOrderByUserId(userId);
    
        if (currentOrder == null || currentOrder.isCheckoutComplete()) {
            // Create a new order if there is no current order or if the current order is already checked out
            currentOrder = new Order();
            // Set additional properties like userId, creation date, etc.
            // ...
            orderService.saveOrder(currentOrder);
        }
        return currentOrder;
    }

    public void checkoutOrder(Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            order.setStatus("Checked Out"); // Update status or use an enum
            orderService.saveOrder(order);
        }
    }

    public void addCartItemToCart(Integer userId, CartItem cartItem) {
        Order order = orderService.checkAndCreateOrder(userId);
        cartItem.setOrder(order);
        // Logic to save or update the cart item
        // ...
    }
    
}