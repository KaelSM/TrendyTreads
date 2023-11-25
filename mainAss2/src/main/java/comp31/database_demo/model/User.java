package comp31.database_demo.model;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "users") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String address;

    @NotNull
    private String phone;

    @NotNull
    private String role;

    @NotNull
    private String status;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>(); // Initialize the list

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Feedback> feedbacks = new ArrayList<>(); // Initialize the list

    
    public User(String name, String username, String email, String password, String address, String phone, String role, String status) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        if (username != null) {
            return username;
        }
        return null;
    }
}
