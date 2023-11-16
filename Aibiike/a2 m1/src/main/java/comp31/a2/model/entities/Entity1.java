package comp31.a2.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Entity1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String description;

    public Entity1(String description) {
        System.out.println("Creating " + description);
        this.description = description;
    }

}
