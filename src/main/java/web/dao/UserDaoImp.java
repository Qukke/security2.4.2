package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

   @PersistenceContext
      private EntityManager em;

   @Override
   @Transactional
   public boolean add(User user) {
//      em.persist(user);
      User userFromDB = findByName(user.getName());
      if (userFromDB != null) {
         return false;
      }
      user.setRoles(Collections.singleton(new Role(1L, "USER")));
      em.persist(user);
      return true;
   }

   @Override
   @Transactional
   public void removeById(Long id) {
      User user = em.find(User.class, id);
      em.remove(user);
   }

   @Override
   @Transactional
   public void edit(User user) {
      System.out.println(user.getRoles());
      em.merge(user);
   }

   @Override
   @Transactional
   public List<User> listUsers() {
      return em.createQuery("select r from User r").getResultList();
   }

   @Transactional
   @Override
   public User getUserById(Long id) {
      return em.find(User.class, id);
   }


   @Transactional
   @Override
   public User findByName(String username) {
      List<User> user = em.createQuery("select r from User r WHERE r.name =:username").setParameter("username", username).getResultList();
      if (user.size() != 0) return user.get(0);
      else return null;
   }

}
