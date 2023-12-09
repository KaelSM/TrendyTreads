package comp31.database_demo.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;

/**
 * Represents a shopping cart for a user.
 */
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products = new HashMap<>();

    private double totalAmount;

    /**
     * Gets the ID of the cart.
     *
     * @return The ID of the cart.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the cart.
     *
     * @param id The ID of the cart.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user associated with the cart.
     *
     * @return The user associated with the cart.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the cart.
     *
     * @param user The user associated with the cart.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the products in the cart along with their quantities.
     *
     * @return The products in the cart along with their quantities.
     */
    public Map<Product, Integer> getProducts() {
        return products;
    }

    /**
     * Sets the products in the cart along with their quantities.
     *
     * @param products The products in the cart along with their quantities.
     */
    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    } 

    /**
     * Gets the total amount of the cart.
     *
     * @return The total amount of the cart.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the cart.
     *
     * @param totalAmount The total amount of the cart.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
