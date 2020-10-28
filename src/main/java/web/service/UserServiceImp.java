package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service("userServiceImp")
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public boolean add(User user) {
      return userDao.add(user);
   }

   @Transactional
   @Override
   public void removeById(Long id) {
      userDao.removeById(id);
   }

   @Transactional
   @Override
   public void edit(User user) {
      userDao.edit(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional
   @Override
   public User getUserById(Long id) {
      return userDao.getUserById(id);
   }

   @Transactional
   @Override
   public User findByName(String username) {
      return userDao.findByName(username);
   }

   @Transactional
   @Override
   public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
      User user = findByName(name);
      return user;
   }


}
