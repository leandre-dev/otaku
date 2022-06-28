package fr.projet.jee.Dao;


import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import fr.projet.jee.Objets.User;


public class UserDao {
    
    @PersistenceContext
    private EntityManager em;

    @Resource
    UserTransaction userTransaction;
    
    public boolean create(User user){
        try{
            userTransaction.begin();
            em.persist(user);
            userTransaction.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    
    public List<User> getAllUsers(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }
    
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }
    
    public User getUserByUName(String username){
        var res = em.createQuery("SELECT u FROM User u WHERE u.username LIKE '"+ username + "'", User.class).getResultList();
        return res.size() > 0 ? res.get(0) : null;
    }
}