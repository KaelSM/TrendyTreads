package comp31.database_demo.repos;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comp31.database_demo.model.Brand;

/**
 * This interface represents a repository for managing Brand entities.
 * It provides methods for CRUD operations and custom queries.
 */
@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {
    
    /**
     * Finds a list of brands by name.
     *
     * @param name the name of the brand to search for
     * @return a list of brands matching the given name
     */
    List<Brand> findByName(String name);
    
}