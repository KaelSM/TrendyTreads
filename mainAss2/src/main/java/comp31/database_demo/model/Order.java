package comp31.database_demo.model;

//import java.util.Set;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Constructs a new Order with the specified user, createdAt, numItem, address,
 * city, country, and paypalId.
 *
 * @param orderId    the id of the order
 * @param user       the user that the order is for
 * @param createdAt  the date and time that the order was created
 * @param numItem    the number of items in the order
 * @param address    the address that the order is being shipped to
 * @param city       the city that the order is being shipped to
 * @param country    the country that the order is being shipped to
 * @param status     the status of the order
 * @param paypalId   the paypal id of the order
 * @param cartItems  the cart items of the order
 * @return a new Order with the specified user, createdAt, numItem, address,
 *         city, country, and paypalId
 *
 */

@Entity
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column in Order table for User
    @ToString.Exclude // to prevent circular reference in Lombok's toString()
    private User user; // Each order is associated with one user

    private String createdAt;
    private Integer numItem; // This can be calculated from CartItems
    private String address;
    private String city;
    private String country;
    private String status;
    private String paypalId;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude // to prevent circular reference in Lombok's toString()
   // private Set<CartItem> cartItems; // One order can have multiple cart items
    List<CartItem> cartItems;


    public Order(User user, String createdAt, Integer numItem, String address, String city, String country,
          String status,  String paypalId) {
        this.user = user;
        this.createdAt = createdAt;
        this.numItem = numItem;
        this.address = address;
        this.city = city;
        this.country = country;
        this.status = status;
        this.paypalId = paypalId;
    }
}