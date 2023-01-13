package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
//todo shift update brandcode to dao
@Service
public class BrandMasterApi {
    @Autowired
    private BrandMasterDao dao;
    //private Helper help;

    public void add(BrandMasterPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        dao.insertBrandDao(brandPojo);
    }
//todo remove transactional
//todo rename to only add,delete etc. and add transactional usig rollbackfor and readonly
    public void delete(int id) throws ApiException{
        getCheck(id);
        dao.deleteBrandDao(id);
    }
    public BrandMasterPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<BrandMasterPojo> getAll() {
        return dao.selectAllBrandDao();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, BrandMasterPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        BrandMasterPojo ex = getCheck(id);
        ex.setCategory(brandPojo.getCategory());
        ex.setBrand(brandPojo.getBrand());
    }

    public BrandMasterPojo getCheck(int id) throws ApiException {
        BrandMasterPojo p = dao.selectBrandDao(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public void checkUnique(BrandMasterPojo brandPojo) throws ApiException {
        if (!Objects.isNull(dao.checkUnique(brandPojo.getBrand(), brandPojo.getCategory()))) {
            throw new ApiException(brandPojo.getBrand() + " - " + brandPojo.getCategory() + " pair already exists");
        }
    }
}
