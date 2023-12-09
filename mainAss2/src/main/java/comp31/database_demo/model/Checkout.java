package comp31.database_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private String name;
    private String address;
    private String email;
    private String paymentMethod;
    

    /**
     * Get the checkout ID.
     * 
     * @return The checkout ID.
     */
    public Long getCheckoutId() {
        return id;
    }

    /**
     * Set the checkout ID.
     * 
     * @param id The checkout ID to set.
     */
    public void setCheckoutId(Long id) {
        this.id = id;
    }

    /**
     * Get the ID of the checkout.
     * 
     * @return The ID of the checkout.
     */
    public Long getId() {
        return this.id;
    }    

    /**
     * Get the cart associated with the checkout.
     * 
     * @return The cart associated with the checkout.
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Set the cart associated with the checkout.
     * 
     * @param cart The cart to set.
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Get the name associated with the checkout.
     * 
     * @return The name associated with the checkout.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name associated with the checkout.
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the address associated with the checkout.
     * 
     * @return The address associated with the checkout.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address associated with the checkout.
     * 
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the email associated with the checkout.
     * 
     * @return The email associated with the checkout.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email associated with the checkout.
     * 
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the payment method associated with the checkout.
     * 
     * @return The payment method associated with the checkout.
     */
    public String getPaymentMethod() {
        return this.paymentMethod;
    }
    
    /**
     * Set the payment method associated with the checkout.
     * 
     * @param paymentMethod The payment method to set.
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }    

    /**
     * Default constructor for Checkout.
     */
    public Checkout() {}
    
}

    
    

