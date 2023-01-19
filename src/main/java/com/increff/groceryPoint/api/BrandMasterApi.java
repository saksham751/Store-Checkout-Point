package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
//todo shift update brandcode to dao
@Service
public class BrandMasterApi {
    @Autowired
    private BrandMasterDao brandDao;
    //private Helper help;

    public void add(BrandMasterPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        brandDao.insertBrandDao(brandPojo);
    }
//todo remove transactional
//todo rename to only add,delete etc. and add transactional usig rollbackfor and readonly
    public void delete(int id) throws ApiException{
        getCheck(id);
        brandDao.deleteBrandDao(id);
    }
    public BrandMasterPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<BrandMasterPojo> getAll() {
        return brandDao.selectAllBrandDao();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, BrandMasterPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        BrandMasterPojo ex = getCheck(id);
        ex.setCategory(brandPojo.getCategory());
        ex.setBrand(brandPojo.getBrand());
    }

    public BrandMasterPojo getCheck(int id) throws ApiException {
        BrandMasterPojo p = brandDao.selectBrandDao(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public List<BrandMasterPojo> getByBrand(String brand){
        return brandDao.getByBrand(brand);
    }
    public List<BrandMasterPojo> getByCategory(String category){
        return brandDao.getByCategory(category);
    }
    public BrandMasterPojo getByBrandCategory(String brand,String category){
        return brandDao.getByBrandCategory(brand,category);
    }
    public void checkUnique(BrandMasterPojo brandPojo) throws ApiException {
        if (!Objects.isNull(brandDao.checkUnique(brandPojo.getBrand(), brandPojo.getCategory()))) {
            throw new ApiException(brandPojo.getBrand() + " - " + brandPojo.getCategory() + " pair already exists");
        }
    }

}
