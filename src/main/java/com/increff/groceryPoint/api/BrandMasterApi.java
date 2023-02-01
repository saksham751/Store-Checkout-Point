package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
//todo upload kaise hota hai
@Service
public class BrandMasterApi {
    @Autowired
    private BrandMasterDao brandDao;

    public int add(BrandMasterPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        return brandDao.add(brandPojo);
    }

    public int delete(int id) throws ApiException{
        getCheck(id);
        brandDao.delete(id);
        return id;
    }
    public BrandMasterPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<BrandMasterPojo> getAll() {
        return brandDao.getAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, BrandMasterPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        BrandMasterPojo ex = getCheck(id);
        ex.setCategory(brandPojo.getCategory());
        ex.setBrand(brandPojo.getBrand());
    }

    public BrandMasterPojo getCheck(int id) throws ApiException {
        BrandMasterPojo p = brandDao.get(id);
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
