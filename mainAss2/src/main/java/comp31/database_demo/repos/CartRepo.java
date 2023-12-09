package comp31.database_demo.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import comp31.database_demo.model.Cart;



/**
 * The CartRepo interface provides methods for accessing and manipulating Cart entities in the database.
 */
public interface CartRepo extends JpaRepository<Cart, Long> {

    /**
     * Retrieves a Cart by the specified user ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the Cart if found, or an empty Optional if not found
     */
    Optional<Cart> findByUserId(Long userId);

    /**
     * Retrieves a Cart by the specified ID.
     *
     * @param id the ID of the Cart
     * @return an Optional containing the Cart if found, or an empty Optional if not found
     */
    Optional<Cart> findById(Long id);

}
