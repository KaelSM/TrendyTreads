package comp31.database_demo.repos;

import java.util.List;
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
    
    List<User> findAll();
    void deleteById(Integer id);
    User findByUserName(String userName);    

}
