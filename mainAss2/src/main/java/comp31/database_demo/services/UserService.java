package comp31.database_demo.services;
import org.springframework.stereotype.Service;

import comp31.database_demo.model.User;
import comp31.database_demo.repos.UserRepo;
import jakarta.servlet.http.HttpSession;

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

    public User getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        // Fetch the user from the database or session
        return UserRepo.findById(userId).orElse(null); // Replace with your user fetching logic
    }
}