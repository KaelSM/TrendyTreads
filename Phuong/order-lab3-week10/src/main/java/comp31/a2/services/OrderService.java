package comp31.a2.services;

import java.util.List;

import comp31.a2.model.entities.Order;
import comp31.a2.model.repositories.OrderRepo;

public class OrderService {

    OrderRepo orderRepo;
    
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;

//Long orderId, Integer userId, String createdAt, String address, String city, 
//String country, String cardNumber, String holderName, String expiryDate
        orderRepo.save(new Order(1, 1, "2021-10-10", 3, "1234 Main St", "Toronto", "Canada", "5698358", "John Doe", "10/29"));
        orderRepo.save(new Order(2, 1, "2021-11-11", 1, "5678 Main St", "Toronto", "Canada", "5698358", "John Doe", "10/29"));
        orderRepo.save(new Order(3, 2, "2021-12-06", 1, "9734 Nunez Prairie", "Rio Branco", "Brazil", "3652599", "Joy Morrow", "06/26"));
        orderRepo.save(new Order(4, 3, "2020-11-21", 2, "10249 Jennifer Squares", "Suzuka City", "Japan", "3652125", "Ana Day", "01/25"));
        orderRepo.save(new Order(5, 4, "2022-06-01", 7, "4547 Maldonado Ports", "Des Moines", "United States", "8625431", "Anthony Martin", "12/29"));
    }

    //find all entities
    public List<Order> findAllOrders() {
        return orderRepo.findAll();
    }

    //find an entity by its primary key
    public List<Order> findOrdersByUserId(Integer userId) {
        return orderRepo.findByUserId(userId);
    }

    //Deletes the entity the given id
    public List<Order> deleteOrderById(Integer orderId) {    
        return orderRepo.deleteOrderById(orderId);
    }

    //findBy followed by logical operators BETWEEN
    public List<Order> findBycreatAtBetween(String start, String end) {
        return orderRepo.findBycreatAtBetween(start, end);
    }

    //findBy followed by logical operators AND
    public List<Order> findBycreatAtAnduserId(String createdAt, Integer userId) {
        return orderRepo.findBycreatAtAnduserId(createdAt, userId);
    }

    //findBy followed by logical operators OR
    public List<Order> findBycardNumberOruserId(String cardNumber, Integer userId) {
        return orderRepo.findBycardNumberOruserId(cardNumber, userId);
    }

   // order the results 
   public List<Order> findByorderIdOrderBycreatedAtAsc() {
         return orderRepo.findByorderIdOrderBycreatedAtAsc();
    }

    // Limiting Number of Results
    List<Order> findBynumItemTop3(Integer numItem) {
        return orderRepo.findBynumItemTop3(numItem);
    }

    //ignore case
    List<Order> findBycityIgnoreCase(String city) {
        return orderRepo.findBycityIgnoreCase(city);
    }
}
