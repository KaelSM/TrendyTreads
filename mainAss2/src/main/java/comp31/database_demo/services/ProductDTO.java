package comp31.database_demo.services;

import comp31.database_demo.model.Product;

public class ProductDTO {
    private Product product;
    private double minPrice;
    private double maxPrice;

    /**
     * Constructs a new ProductDTO object.
     * 
     * @param product   the product object
     * @param minPrice  the minimum price
     * @param maxPrice  the maximum price
     */
    public ProductDTO(Product product, double minPrice, double maxPrice) {
        this.product = product;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Returns the ID of the product.
     * 
     * @return the ID of the product
     */
    public Integer getId() {
        return this.product.getId();
    }

    // Getters and Setters

    /**
     * Returns the product object.
     * 
     * @return the product object
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product object.
     * 
     * @param product the product object to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Returns the minimum price.
     * 
     * @return the minimum price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Sets the minimum price.
     * 
     * @param minPrice the minimum price to set
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * Returns the maximum price.
     * 
     * @return the maximum price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Sets the maximum price.
     * 
     * @param maxPrice the maximum price to set
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Returns the brand of the product.
     * 
     * @return the brand of the product
     */
    public String getBrand() {
        return this.product.getBrand();
    }

    /**
     * Returns the type of the product.
     * 
     * @return the type of the product
     */
    public String getType() {
        return this.product.getType();
    }

    /**
     * Returns the description of the product.
     * 
     * @return the description of the product
     */
    public String getDescription() {
        return this.product.getDescription();
    }

    /**
     * Returns the category of the product.
     * 
     * @return the category of the product
     */
    public String getCategory() {
        return this.product.getCategory();
    }

    /**
     * Returns the availability of the product.
     * 
     * @return the availability of the product
     */
    public Boolean getAvailability() {
        return this.product.getAvailability();
    }

}
