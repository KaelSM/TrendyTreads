package comp31.a2.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import comp31.a2.model.entities.User;
import java.util.Optional; 


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findById(Long userId);
}
