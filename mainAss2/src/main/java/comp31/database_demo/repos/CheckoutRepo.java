package comp31.database_demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;


import comp31.database_demo.model.Checkout;

public interface CheckoutRepo extends JpaRepository<Checkout, Long>{

    
} 
