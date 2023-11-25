package comp31.database_demo.model;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** 
    * Constructs a new CartItem with the specified name, quantity, price, size, color, status, product, and order.
    *
    * @param id          the id of the cart item
    * @param name        the name of the cart item
    * @param quantity    the quantity of the cart item
    * @param price       the price of the cart item
    * @param size        the size of the cart item
    * @param color       the color of the cart item
    * @param status      the status of the cart item
    * @param product     the product that the cart item is for
    * @param order       the order that the cart item is for
    * @return a new CartItem with the specified name, quantity, price, size, color, status, product, and order
    */

@Entity
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
   
    private Integer quantity;
    @NotNull
    private Integer price;
    @NotNull
    private Double size;
    @NotNull
    private String color;
    @NotNull
    private String status;

    @ManyToOne
    @JoinColumn(name = "product_id") // Foreign key column for Product
    @ToString.Exclude //this is to prevent the circular reference
    private Product product; // Each cart item is associated with one product

    @ManyToOne
    @JoinColumn(name = "order_id") // Foreign key column for Order
    @ToString.Exclude //this is to prevent the circular reference
    private Order order; // Each cart item is associated with one order

    public CartItem(String name, Integer quantity, Integer price, Double size, String color, String status,
            Product product, Order order) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.color = color;
        this.status = status;
        this.product = product;
        this.order = order;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", status='" + status + '\'' +
                ", product=" + (product != null ? product.getId() : null) +
                ", order=" + (order != null ? order.getOrderId() : null) +
                '}';
    }
}
