package fr.projet.jee.Bean;

import javax.ejb.*;
import javax.inject.Inject;

import fr.projet.jee.Dao.WhiskeyDao;
import fr.projet.jee.Objets.Whiskey;

import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class WhiskeyBean {
    @Inject
    private WhiskeyDao _whiskeyDao;

    public List<Whiskey> getWhiskeys() {
        return _whiskeyDao.getAll();
    }

    public Whiskey getWhiskey(Long id) {
        return _whiskeyDao.findById(id);
    }

    public boolean add(Whiskey _whiskey) {
        return _whiskeyDao.create(_whiskey);
    }

    public boolean update(Long id, Whiskey _whiskey) {
        _whiskey.setid(id);
        return _whiskeyDao.update(_whiskey);
    }

    public boolean delete(Long id){
        return _whiskeyDao.delete(id);
    }
}
