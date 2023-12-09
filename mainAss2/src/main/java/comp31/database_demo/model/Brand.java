package comp31.database_demo.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


/**
 * Represents a brand in the system.
 */
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    /**
     * Gets the ID of the brand.
     *
     * @return the ID of the brand
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the brand.
     *
     * @param id the ID of the brand
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the brand.
     *
     * @return the name of the brand
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the brand.
     *
     * @param name the name of the brand
     */
    public void setName(String name) {
        this.name = name;
    }
 
}