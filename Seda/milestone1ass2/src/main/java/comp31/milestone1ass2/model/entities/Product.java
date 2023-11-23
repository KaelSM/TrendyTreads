package comp31.milestone1ass2.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id;
    String name;
    String description;
    Integer price;


    public Product(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
        System.out.println("Product created");
    }

}
