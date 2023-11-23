package comp31.database_demo.services;


import org.springframework.stereotype.Service;
import comp31.database_demo.model.Order;
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
