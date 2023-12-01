package comp31.database_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.CartItem;
import comp31.database_demo.model.Order;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.OrderRepo;

import java.util.List;

/***
 * OrderService is a service class that handles the business logic for the Order model
 */
@Service
public class OrderService {
    private final OrderRepo OrderRepo;

    @Autowired
    private OrderRepo orderRepo;
    private CartItemService cartItemService;

    public OrderService(OrderRepo OrderRepo) {
        this.OrderRepo = OrderRepo;
    }

    /**
     * Creates a new order with the given user, creation date, number of items, address, city, country, status, and PayPal ID.
     * @param user the user associated with the order
     * @param createdAt the creation date of the order
     * @param numItem the number of items in the order
     * @param address the address for delivery
     * @param city the city for delivery
     * @param country the country for delivery
     * @param status the status of the order
     * @param paypalId the PayPal ID for payment
     * @return the created order
     */
    public Order createOrder(User user, String createdAt, Integer numItem, String address, String city, String country,
            String status, String paypalId) {
        Order order = new Order(user, createdAt, numItem, address, city, country, status, paypalId);
        return orderRepo.save(order);
    }

    /**
     * Retrieves a list of orders associated with the given user ID.
     * @param userId the ID of the user
     * @return a list of orders
     */
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepo.findAllByUserId(userId);
    }

    /**
     * Retrieves all orders.
     * @return a list of all orders
     */
    public List<Order> getAllOrders() {
        return OrderRepo.findAll();
    }

    /**
     * Deletes an order with the given order ID.
     * @param orderId the ID of the order to delete
     */
    public void deleteOrder(Integer orderId) {
        OrderRepo.deleteById(orderId);
    }

    /**
     * Retrieves an order with the given order ID.
     * @param orderId the ID of the order
     * @return the order, or null if not found
     */
    public Order getOrderById(Integer orderId) {
        return OrderRepo.findById(orderId).orElse(null);
    }

    /**
     * Updates an order with the given order ID and new order details.
     * @param orderId the ID of the order to update
     * @param order the updated order details
     * @return the updated order, or null if not found
     */
    public Order updateOrder(Integer orderId, Order order) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setAddress(order.getAddress());
            existingOrder.setCity(order.getCity());
            existingOrder.setCountry(order.getCountry());
            existingOrder.setCreatedAt(order.getCreatedAt());
            existingOrder.setNumItem(order.getNumItem());
            existingOrder.setPaypalId(order.getPaypalId());
            existingOrder.setStatus(order.getStatus());
            existingOrder.setUser(order.getUser());
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Updates the status of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param status the new status of the order
     * @return the updated order, or null if not found
     */
    public Order updateOrderStatus(Integer orderId, String status) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setStatus(status);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Updates the PayPal ID of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param paypalId the new PayPal ID of the order
     * @return the updated order, or null if not found
     */
    public Order updateOrderPaypalId(Integer orderId, String paypalId) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setPaypalId(paypalId);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Updates the number of items in an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param numItem the new number of items in the order
     * @return the updated order, or null if not found
     */
    public Order updateOrderNumItem(Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Updates the address of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param address the new address of the order
     * @return the updated order, or null if not found
     */
    public Order updateOrderAddress(Integer orderId, String address) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setAddress(address);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Updates the city of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param city the new city of the order
     * @return the updated order, or null if not found
     */
    public Order updateOrderCity(Integer orderId, String city) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setCity(city);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Updates the country of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param country the new country of the order
     * @return the updated order, or null if not found
     */
    public Order updateOrderCountry(Integer orderId, String country) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setCountry(country);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Adds or updates the number of items in the cart of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param numItem the new number of items in the cart
     * @return the updated order, or null if not found
     */
    public Order addOrUpdateCartItems(Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }   

    /**
     * Removes the number of items from the cart of an order with the given order ID.
     * @param orderId the ID of the order to update
     * @param numItem the number of items to remove from the cart
     * @return the updated order, or null if not found
     */
    public Order removeCartItems (Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Calculates the total price of an order with the given order ID and number of items.
     * @param orderId the ID of the order
     * @param numItem the number of items in the order
     * @return the updated order, or null if not found
     */
    public Order getTotalPrice (Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    /**
     * Checks out an order with the given order ID.
     * @param orderId the ID of the order to check out
     */
	public void checkout(int orderId) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setStatus("checked out");
            OrderRepo.save(existingOrder);
        }
	}

    /**
     * Creates a new order or updates an existing order.
     * @param order the order to create or update
     * @return the created or updated order
     */
    public Order createOrUpdateOrder(Order order) {
        return orderRepo.save(order);
    }

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    public Order getCurrentOrderByUserId(Integer userId) {
        return null;
    }

    public void addCartItemToOrder(Integer userId, CartItem cartItem) {
    Order order = checkAndCreateOrder(userId);
    cartItem.setOrder(order);
    cartItemService.addOrUpdateCartItem(cartItem);
}

    public Order checkAndCreateOrder(Integer userId) {
    // Assuming you have a method to get the latest or current order for a user
    Order currentOrder = getCurrentOrderByUserId(userId);

    if (currentOrder == null || currentOrder.isCheckoutComplete()) {
        // Create a new order if there is no current order or if the current order is already checked out
        currentOrder = new Order();
        // Set additional properties like userId, creation date, etc.
        // ...
        saveOrder(currentOrder);
    }
    return currentOrder;
    
}
   
}
