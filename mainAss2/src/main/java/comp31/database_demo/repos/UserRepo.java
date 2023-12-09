package comp31.database_demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comp31.database_demo.model.User;


/**
 * This interface represents a repository for managing User entities.
 * It extends the JpaRepository interface, providing CRUD operations for User entities.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the User object if found, null otherwise
     */
    User findByUsername(String username);
}