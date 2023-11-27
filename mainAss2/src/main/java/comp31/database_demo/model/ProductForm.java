package comp31.database_demo.model;

/**
 * ProductForm represents a form for creating or updating a product.
 * It contains information such as the brand, type, description, and category of the product.
 */
public class ProductForm {
    private String brand;
    private String type;
    private String description;
    private String category;

    /**
     * Get the brand of the product.
     * 
     * @return the brand of the product
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the brand of the product.
     * 
     * @param brand the brand of the product
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get the type of the product.
     * 
     * @return the type of the product
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the product.
     * 
     * @param type the type of the product
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the description of the product.
     * 
     * @return the description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the product.
     * 
     * @param description the description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the category of the product.
     * 
     * @return the category of the product
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category of the product.
     * 
     * @param category the category of the product
     */
    public void setCategory(String category) {
        this.category = category;
    }
}

