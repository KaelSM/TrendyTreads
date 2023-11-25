package comp31.database_demo.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
    * Constructs a new Product with the specified brand, type, description, and category.
    *
    * @param id          the id of the product
    * @param brand       the brand of the product
    * @param type        the type of the product
    * @param description the description of the product
    * @param category    the category of the product
    * @param feedbacks   the feedbacks of the product
    * @param cartItems   the cart items of the product
    * @return a new Product with the specified brand, type, description, and category
    *
    */

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String brand;
    @NotBlank
    private String type;
    @NotBlank
    private String description;
    @NotBlank
    private String category;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude // to prevent circular reference in Lombok's toString()
    List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude // to prevent circular reference in Lombok's toString()
    List<CartItem> cartItems;

    public Product(String brand, String type, String description, String category) {
        this.brand = brand;
        this.type = type;
        this.description = description;
        this.category = category;
    }

    
}