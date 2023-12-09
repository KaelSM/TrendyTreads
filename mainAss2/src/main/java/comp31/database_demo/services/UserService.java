package comp31.database_demo.services;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class represents a service for managing user-related operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepo UserRepo;

    /**
     * Saves a user to the database.
     * 
     * @param user the user to be saved
     * @return the saved user
     */
    public User saveUser(User user) {
        return UserRepo.save(user);
    }

    /**
     * Finds a user by their username.
     * 
     * @param username the username of the user to be found
     * @return the found user, or null if not found
     */
    public User findByUsername(String username) {
        return UserRepo.findByUsername(username);
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id the ID of the user to be deleted
     */
    public void deleteUser(Long id) {
        UserRepo.deleteById(id);
    }

    /**
     * Retrieves the current user based on the session.
     * 
     * @param session the HttpSession object representing the current session
     * @return the current user, or null if not found
     */
    public User getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return UserRepo.findById(userId).orElse(null); 
    }
}