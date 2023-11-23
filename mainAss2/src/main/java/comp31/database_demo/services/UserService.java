package comp31.database_demo.services;

import org.springframework.stereotype.Service;
import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;

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
    private final UserRepo UserRepo;

    public UserService(UserRepo UserRepo) {
        this.UserRepo = UserRepo;
    }

    public Optional<User> getUserById(Integer id) {
        return UserRepo.findById(id);
    }    

    public User saveUser(User user) {
        return UserRepo.save(user);
    }

    public void deleteUser(Integer id) {
        UserRepo.deleteById(id);
    }


}