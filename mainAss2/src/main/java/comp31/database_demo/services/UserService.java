package comp31.database_demo.services;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    @Autowired
    private UserRepo UserRepo;

    public User saveUser(User user) {
        return UserRepo.save(user);
    }

    public User findByUsername(String username) {
        return UserRepo.findByUsername(username);
    }

    public void deleteUser(Long id) {
        UserRepo.deleteById(id);
    }

    
}