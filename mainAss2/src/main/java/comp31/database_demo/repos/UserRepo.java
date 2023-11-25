package comp31.database_demo.repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import comp31.database_demo.model.User;

/**
 *  UserRepo is an interface that extends CrudRepository
 * @param findAll() returns a list of all users
 * @param removeById(Integer id) removes the user with the given id
 * @param addUserByName(String name) adds a user with the given name
 * @param removeByUserName(String userName) removes the user with the given userName
 * @param  addUserByNameAndUserNameAndEmail(String name, String userName, String email) adds a user with the given name, userName, and email
 * @param addUserByNameAndUserNameAndEmailAndPhoneAndAddress(String name, String userName, String email, String phone, String address) adds a user with the given name, userName, email, phone, and address
 */

public interface UserRepo extends CrudRepository<User, Integer>{
    
    List<User> findByRole(String role);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    @Modifying
    @Query("UPDATE User u SET u.isActive = :isActive WHERE u.id = :id")
    void updateUserActivityStatus(@Param("id") Integer id, @Param("isActive") Boolean isActive);

    @Query("SELECT u FROM User u LEFT JOIN u.orders o WHERE o IS NULL")
    List<User> findUsersWithNoOrders();

        @Query("SELECT COUNT(u), u.role FROM User u GROUP BY u.role")
    List<Object[]> countUsersByRole();

    List<User> findByIsActive(Boolean isActive);

    List<User> findByAddressContaining(String address);

    @Query("SELECT u FROM User u WHERE u.registrationDate >= :startDate")
    List<User> findRecentlyRegisteredUsers(@Param("startDate") LocalDate startDate);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    void updateUserRole(@Param("id") Integer id, @Param("role") String role);

    @Modifying
    @Query("UPDATE User u SET u.isActive = false WHERE u.lastActiveDate < :cutoffDate")
    void bulkDeactivateInactiveUsers(@Param("cutoffDate") LocalDate cutoffDate);

    Optional<User> findByPhone(String phone);
}
