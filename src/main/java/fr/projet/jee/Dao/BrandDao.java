package fr.projet.jee.Dao;

import fr.projet.jee.Objets.Brand;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrandDao {
    
    @PersistenceContext
    private EntityManager em;

    @Resource
    UserTransaction userTransaction;

    public List<Brand> getAll(){
        return em.createQuery("select a from Brand a", Brand.class).getResultList();
    }

    public Brand findById(Long id) {
        Logger.getGlobal().log(Level.SEVERE, "JPA Find " +  id);
        return em.find(Brand.class, id);
    }

    public boolean create(Brand _brand){
        try{
            userTransaction.begin();
            em.persist(_brand);
            userTransaction.commit();
            
            Logger.getGlobal().log(Level.SEVERE, "JPA Created " + _brand.getName());
            return true;
        }catch (Exception e){
            Logger.getGlobal().log(Level.SEVERE, "JPA C error " + e.getMessage());
            return false;
        }
    }
}
