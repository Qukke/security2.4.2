package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
   boolean add(User user);
   void removeById(Long id);
   void edit(User user);
   List<User> listUsers();
   User getUserById(Long id);
   User findByName(String username);
}
