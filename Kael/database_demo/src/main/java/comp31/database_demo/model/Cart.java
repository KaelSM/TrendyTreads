package comp31.database_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Assuming each cart is related to a single user
    // You can add a user field here if needed, e.g., private User user;
    // Ensure that you have a User entity defined as well

    @OneToMany(mappedBy = "cart")
    private List<Product> products = new ArrayList<>();

    // Add methods to manipulate the cart, like addProduct, removeProduct, etc.

    // Method to add a product to the cart
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // Method to remove a product from the cart
    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}

