package comp31.database_demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;


import comp31.database_demo.model.Checkout;

/**
 * The CheckoutRepo interface represents a repository for managing Checkout entities.
 * It extends the JpaRepository interface, providing CRUD operations for the Checkout entity.
 */
public interface CheckoutRepo extends JpaRepository<Checkout, Long>{

    
} 
