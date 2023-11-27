package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import comp31.database_demo.model.User;

/**
 *  UserRepo is an interface that extends CrudRepository
 */

public interface UserRepo extends CrudRepository<User, Integer>{
    
    // Find users by role
    List<User> findByRole(String role);
    
    // Find all users
    List<User> findAll();
    
    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find users by name containing (case-insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    // Find users with no orders
    @Query("SELECT u FROM User u LEFT JOIN u.orders o WHERE o IS NULL")
    List<User> findUsersWithNoOrders();

    // Count users by role
    @Query("SELECT COUNT(u), u.role FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();

    // Find users by address containing
    List<User> findByAddressContaining(String address);

    // Update user role
    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    void updateUserRole(@Param("id") Integer id, @Param("role") String role);

    // Find user by phone
    Optional<User> findByPhone(String phone);
}
