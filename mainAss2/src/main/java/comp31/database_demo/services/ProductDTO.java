package comp31.database_demo.services;

import comp31.database_demo.model.Product;

public class ProductDTO {
    private Product product;
    private double minPrice;
    private double maxPrice;

    // Constructor
    public ProductDTO(Product product, double minPrice, double maxPrice) {
        this.product = product;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Integer getId() {
        return this.product.getId();
    }

    // Getters and Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getBrand() {
        return this.product.getBrand();
    }

    public String getType() {
        return this.product.getType();
    }

    public String getDescription() {
        return this.product.getDescription();
    }

    public String getCategory() {
        return this.product.getCategory();
    }

    public Boolean getAvailability() {
        return this.product.getAvailability();
    }

}
