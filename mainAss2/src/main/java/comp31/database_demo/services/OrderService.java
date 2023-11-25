package comp31.database_demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.Order;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.OrderRepo;

import java.util.List;

/***
 * OrderService is a service class that handles the business logic for the Order
 * model
 * 
 * @param getAllOrders()      returns a list of all orders
 * @param deleteOrder(Integer orderId) deletes the order with the given orderId
 * 
 */

@Service
public class OrderService {
    private final OrderRepo OrderRepo;

    @Autowired
    private OrderRepo orderRepo;

    public OrderService(OrderRepo OrderRepo) {
        this.OrderRepo = OrderRepo;
    }

    public Order createOrder(User user, String createdAt, Integer numItem, String address, String city, String country,
            String status, String paypalId) {
        Order order = new Order(user, createdAt, numItem, address, city, country, status, paypalId);
        return orderRepo.save(order);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepo.findAllByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return OrderRepo.findAll();
    }

    public void deleteOrder(Integer orderId) {
        OrderRepo.deleteById(orderId);
    }

    public Order getOrderById(Integer orderId) {
        return OrderRepo.findById(orderId).orElse(null);
    }

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

    public Order updateOrderStatus(Integer orderId, String status) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setStatus(status);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order updateOrderPaypalId(Integer orderId, String paypalId) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setPaypalId(paypalId);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order updateOrderNumItem(Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order updateOrderAddress(Integer orderId, String address) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setAddress(address);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order updateOrderCity(Integer orderId, String city) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setCity(city);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order updateOrderCountry(Integer orderId, String country) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setCountry(country);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order addOrUpdateCartItems(Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }   

    public Order removeCartItems (Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

    public Order getTotalPrice (Integer orderId, Integer numItem) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setNumItem(numItem);
            return OrderRepo.save(existingOrder);
        }
        return null;
    }

	public void checkout(int orderId) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            existingOrder.setStatus("checked out");
            OrderRepo.save(existingOrder);
        }
	}

    
}
