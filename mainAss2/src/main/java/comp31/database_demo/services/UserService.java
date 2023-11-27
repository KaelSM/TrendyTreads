package comp31.database_demo.services;

import org.springframework.stereotype.Service;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;

import java.util.List;
import java.util.Optional;

/**
 * UserService is a service class that handles the business logic for the User model.
 */
@Service
public class UserService {
    UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
    public User registerNewUser(User user) {
        return saveUser(user);
    }

    /**
     * Finds a user by their username.
     * @param username The username of the user.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * Validates a user's credentials.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The validated user if the credentials are correct, or null if the credentials are incorrect.
     */
    public User validateUser(String username, String password) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
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
        Optional<User> userOptional = userRepo.findByUsername(username);
        return userOptional.map(user -> user.getUsername().equals(username)).orElse(false);
    }
}