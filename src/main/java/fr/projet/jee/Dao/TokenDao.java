package fr.projet.jee.Dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import fr.projet.jee.Objets.Token;

public class TokenDao {
    
    @PersistenceContext
    private EntityManager em;

    @Resource
    UserTransaction userTransaction;

    public List<Token> read(Long user_id) {
        try {
            return em.createQuery("SELECT t FROM Token t WHERE t.user_id = :id and t.endValidity is null", Token.class).setParameter("id", user_id).getResultList();
        }catch (Exception e){
            return null;
        }
    }
    
    public List<Token> getAll(){
        return em.createQuery("select t from Token t", Token.class).getResultList();
    }

    public Token getById(Long id) {
        Logger.getGlobal().log(Level.SEVERE, "JPA Find " +  id);
        return em.find(Token.class, id);
    }
    
    public Token getTokenByValue(String value){
        var res = em.createQuery("SELECT t FROM Token t WHERE t.value LIKE '"+ value + "'", Token.class).getResultList();
        return res.size() > 0 ? res.get(0) : null;
    }
    
    public boolean create(Token _token){
        try{
            userTransaction.begin();
            em.persist(_token);
            userTransaction.commit();
            
            Logger.getGlobal().log(Level.SEVERE, "JPA Token Created " + _token.getValue());
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "JPA Token C error " + e.getMessage());
            return false;
        }
    }

    public boolean terminate(Token _token) {
        try{
            _token.setEndValidity(LocalDateTime.now());

            userTransaction.begin();
            em.merge(_token);
            userTransaction.commit();

            Logger.getGlobal().log(Level.SEVERE, "JPA Terminated " + _token.getValue());
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "JPA U error " + e.getMessage());
            return false;
        }
    }
}
