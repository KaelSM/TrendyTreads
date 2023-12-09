package comp31.database_demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a product in the inventory.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private int stock;
    private double price;

    /**
     * Retrieves the ID of the product.
     *
     * @return The ID of the product.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id The ID of the product.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the brand of the product.
     *
     * @return The brand of the product.
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the product.
     *
     * @param brand The brand of the product.
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    /**
     * Retrieves the stock quantity of the product.
     *
     * @return The stock quantity of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the product.
     * Throws an exception if the stock quantity is negative.
     *
     * @param stock The stock quantity of the product.
     * @throws IllegalArgumentException if the stock quantity is negative.
     */
    public void setStock(int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
        this.stock = stock;
    }   

    /**
     * Retrieves the price of the product.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * Throws an exception if the price is negative.
     *
     * @param price The price of the product.
     * @throws IllegalArgumentException if the price is negative.
     */
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

}
