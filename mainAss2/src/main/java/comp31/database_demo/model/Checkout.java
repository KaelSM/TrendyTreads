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
    private double totalAmount;
    private double finalPrice;
    private String paymentMethod;
    private String shipmentMethod;
    

    public Long getCheckoutId() {
        return id;
    }

    public void setCheckoutId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cartId) {
        this.cart = cartId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }    

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = this.cart.getTotalAmount();
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public String getShipmentMethod() {
        return this.shipmentMethod;
    }


    
    
    public Checkout() {}
    
}

    
    

