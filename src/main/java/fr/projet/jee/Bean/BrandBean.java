package fr.projet.jee.Bean;

import java.util.List;

import javax.ejb.*;
import javax.inject.Inject;

import fr.projet.jee.Dao.BrandDao;
import fr.projet.jee.Objets.Brand;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BrandBean {
    @Inject
    private BrandDao _brandDao;

    public List<Brand> getBrands() {
        return _brandDao.getAll();
    }
    public Brand getBrand(Long id) {
        return _brandDao.findById(id);
    }

    public boolean add(Brand _brand) {
        return _brandDao.create(_brand);
    }
}
