package comp31.database_demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
    * Constructs a new User with the specified name, email, password, address, phone, role, and status.
    *
    * @param id          the id of the user
    * @param name        the name of the user
    * @param email       the email of the user
    * @param password    the password of the user
    * @param address     the address of the user
    * @param phone       the phone of the user
    * @param role        the role of the user
    * @param status      the status of the user
    * @param orders      the orders of the user
    * @param feedbacks   the feedbacks of the user
    * @return a new User with the specified name, email, password, address, phone, role, and status
    *
    */

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String role;
    private String status;

    @OneToMany(mappedBy = "user")
    //private Set<Order> orders; // One user can have multiple orders
    @ToString.Exclude // to prevent circular reference in Lombok's toString()
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    //private Set<Feedback> feedbacks; // One user can provide multiple feedback entries
    @ToString.Exclude // to prevent circular reference in Lombok's toString()
    List<Feedback> feedbacks;
    
    public User(String name, String userName, String email, String password, String address, String phone, String role, String status) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }
}
