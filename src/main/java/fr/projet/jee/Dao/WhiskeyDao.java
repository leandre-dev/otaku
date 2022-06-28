package fr.projet.jee.Dao;

import fr.projet.jee.Objets.Whiskey;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhiskeyDao {
    
    @PersistenceContext
    private EntityManager em;

    @Resource
    UserTransaction userTransaction;

    public List<Whiskey> getAll(){
        return em.createQuery("select w from Whiskey w", Whiskey.class).getResultList();
    }

    public Whiskey findById(Long id) {
        Logger.getGlobal().log(Level.SEVERE, "JPA Find " +  id);
        return em.find(Whiskey.class, id);
    }

    public boolean create(Whiskey _whiskey){
        try{
            userTransaction.begin();
            em.persist(_whiskey);
            userTransaction.commit();
            
            Logger.getGlobal().log(Level.SEVERE, "JPA Created " + _whiskey.getName());
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "JPA C error " + e.getMessage());
            return false;
        }
    }

    public boolean update(Whiskey _whiskey) {
        try{
            userTransaction.begin();
            em.merge(_whiskey);
            userTransaction.commit();

            Logger.getGlobal().log(Level.SEVERE, "JPA Updated " + _whiskey.getName());
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "JPA U error " + e.getMessage());
            return false;
        }
    }

    public boolean delete(Long id) {
        try{
            userTransaction.begin();
            var _whiskey = em.find(Whiskey.class, id);
            if(_whiskey == null) {
                Logger.getGlobal().log(Level.SEVERE, "JPA NOOOT Deleted " + id);
                return false;
            }

            em.remove(_whiskey);
            userTransaction.commit();
            Logger.getGlobal().log(Level.SEVERE, "JPA Deleted " + _whiskey.getName());
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "JPA D error " + e.getMessage());
            return false;
        }
    }
}
/*
 * cours
 * Courscours10. 
 * 
*/