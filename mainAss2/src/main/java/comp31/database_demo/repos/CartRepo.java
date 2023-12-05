package comp31.database_demo.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comp31.database_demo.model.Cart;



public interface CartRepo extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

    Optional<Cart> findById(Long id);

}
