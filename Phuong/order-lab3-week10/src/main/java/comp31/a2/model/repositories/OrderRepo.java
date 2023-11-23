package comp31.a2.model.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import comp31.a2.model.entities.Order;

/**
 * OrderRepo
 */
public interface OrderRepo extends ListCrudRepository<Order, Integer> {
    List<Order> findAll();

    List<Order> findByUserId(Integer userId);

    List<Order> deleteOrderById(Integer orderId);

    List<Order> findBycreatAtBetween(String start, String end);

    List<Order> findBycreatAtAnduserId(String createdAt, Integer userId);

    List<Order> findBycardNumberOruserId(String cardNumber, Integer userId);

    List<Order> findByorderIdOrderBycreatedAtAsc();

    List<Order> findBynumItemTop3(Integer numItem);

    List<Order> findBycityIgnoreCase(String city);

}