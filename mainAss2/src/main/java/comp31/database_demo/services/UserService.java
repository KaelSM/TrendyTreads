package comp31.database_demo.services;

import org.springframework.stereotype.Service;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;

import java.util.List;
import java.util.Optional;

/*
 * UserService is a service class that handles the business logic for the User model
 * @param getUserById(Integer id) returns the user with the given id
 * @param saveUser(User user) saves the given user
 * @param deleteUser(Integer id) deletes the user with the given id
 * 
 */
@Service
public class UserService {
    UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getUserById(Integer id) {
        return userRepo.findById(id);
    }    

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    public User registerNewUser(User user) {
        return saveUser(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

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

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}