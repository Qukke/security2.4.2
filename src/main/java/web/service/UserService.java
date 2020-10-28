package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    boolean add(User user);
    void removeById(Long id);
    void edit(User user);
    List<User> listUsers();
    User getUserById(Long id);
    User findByName(String username);
}
