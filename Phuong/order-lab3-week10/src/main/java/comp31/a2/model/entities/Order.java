package comp31.a2.model.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer orderId;
    Integer userId;
    String createdAt;
    Integer numItem;
    //Shipping information
    String address;
    String city;
    String country;
    //Payment information
    String cardNumber;
    String holderName;
    String expiryDate;

//    String description;

    public Order(Integer orderId, Integer userId, String createdAt, Integer numItem, String address, String city, String country, String cardNumber, String holderName, String expiryDate) {
//        System.out.println("Creating " + description);
//        this.description = description;
        this.userId = userId;
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.numItem = numItem;
        this.address = address;
        this.city = city;
        this.country = country;
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expiryDate = expiryDate;

    }

}
