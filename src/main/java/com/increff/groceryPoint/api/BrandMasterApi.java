package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.BrandDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandPojo;
import com.increff.groceryPoint.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.groceryPoint.api.Helper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class BrandMasterApi {
    @Autowired
    private BrandDao dao;
    private Helper help;
    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException {
        help.normalize(p);
        if(StringUtil.isEmpty(p.getBrand())) {
            throw new ApiException("name cannot be empty");
        }
        checkUnique(p);
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        help.normalize(p);
        checkUnique(p);
        BrandPojo ex = getCheck(id);
        ex.setCategory(p.getCategory());
        ex.setBrand(p.getBrand());
        dao.update(ex);
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public void checkUnique(BrandPojo brandPojo) throws ApiException {
        if (!Objects.isNull(dao.checkUnique(brandPojo.getBrand(), brandPojo.getCategory()))) {
            throw new ApiException(brandPojo.getBrand() + " - " + brandPojo.getCategory() + " pair already exists");
        }
    }
}
