package comp31.database_demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import comp31.database_demo.controllers.MainController;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

/**
 * UserService is a service class that handles the business logic for the User model.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    UserRepo userRepo;
     private final HttpSession httpSession;

    public UserService(UserRepo userRepo, HttpSession httpSession) {
        this.userRepo = userRepo;
        this.httpSession = httpSession;
    }

    /**
     * Returns the user with the given id.
     * @param id The id of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    public Optional<User> getUserById(Integer id) {
        return userRepo.findById(id);
    }    

    /**
     * Saves the given user.
     * @param user The user to be saved.
     * @return The saved user.
     */
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    /**
     * Deletes the user with the given id.
     * @param id The id of the user to be deleted.
     */
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    /**
     * Registers a new user.
     * @param user The user to be registered.
     * @return The registered user.
     */
    /**
     * Finds a user by their username.
     * @param username The username of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * Validates a user's credentials.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The validated user if the credentials are correct, or null if the credentials are incorrect.
     */
    public User validateUser(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Returns a list of all users.
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Checks if a user with the given username exists.
     * @param username The username to check.
     * @return true if a user with the given username exists, false otherwise.
     */
    public boolean existsByUsername(String username) {
        User user = userRepo.findByUsername(username);
        return user != null;
    }

    public User findById(Integer userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public Integer getCurrentUserId() {
        Integer userId = (Integer) httpSession.getAttribute("userId");
        return userId;
    }

    public String getCurrentUsername() {
        String username = (String) httpSession.getAttribute("username");
        logger.info("----Current Username from session: {}--------", username);
        return username;
    }

    public User updateUserDetails(Integer id, User updatedUserDetails) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        // Update the fields that are allowed to be updated
        user.setName(updatedUserDetails.getName());
        user.setUsername(updatedUserDetails.getUsername());
        user.setEmail(updatedUserDetails.getEmail());
        user.setAddress(updatedUserDetails.getAddress());
        user.setPhone(updatedUserDetails.getPhone());
        user.setStatus(updatedUserDetails.getStatus());
        user.setRole(updatedUserDetails.getRole());
        user.setPassword(updatedUserDetails.getPassword());

        return userRepo.save(user);
    }
}