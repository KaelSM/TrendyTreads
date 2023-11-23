package comp31.database_demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comp31.database_demo.model.Order;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.OrderRepo;

import java.util.List;

/***
 * OrderService is a service class that handles the business logic for the Order model
 * @param getAllOrders() returns a list of all orders
 * @param deleteOrder(Integer orderId) deletes the order with the given orderId
 * 
 */

@Service
public class OrderService {
    private final OrderRepo OrderRepo;

    @Autowired
    private OrderRepo orderRepo;

    public Order createOrder(User user, String createdAt, Integer numItem, String address, String city, String country, String status, String paypalId) {
        Order order = new Order(user, createdAt, numItem, address, city, country, status, paypalId);
        return orderRepo.save(order);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepo.findAllByUserId(userId);
    }

    public OrderService(OrderRepo OrderRepo) {
        this.OrderRepo = OrderRepo;
    }

    public List<Order> getAllOrders() {
        return OrderRepo.findAll();
    }

    public void deleteOrder(Integer orderId) {
        OrderRepo.deleteById(orderId);
    }  
}
