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

    public void addBrandApi(BrandMasterPojo p) throws ApiException {
        //help.normalize(p);
        //checkUnique(p);
        dao.insertBrandDao(p);
    }
//todo remove transactional

    public void deleteBrandApi(int id) throws ApiException{
        getCheck(id);
        dao.deleteBrandDao(id);
    }


    public BrandMasterPojo getBrandApi(int id) throws ApiException {
        return getCheck(id);
    }

    public List<BrandMasterPojo> getAllBrandApi() {
        return dao.selectAllBrandDao();
    }

    @Transactional
    public void updateBrandApi(int id, BrandMasterPojo p) throws ApiException {
        checkUnique(p);
        BrandMasterPojo ex = getCheck(id);
        ex.setCategory(p.getCategory());
        ex.setBrand(p.getBrand());
        dao.updateBrandDao(ex);
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
