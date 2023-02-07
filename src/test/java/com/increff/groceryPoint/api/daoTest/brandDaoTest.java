package com.increff.groceryPoint.api.daoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class brandDaoTest extends AbstractUnitTest {
    @Autowired
    private BrandMasterDao brandDao;

    private Integer brandId;
    @Before
    public void addBrand() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory2");
        brandId=brandDao.add(brandPojo);
        brandPojo.setBrand("testbrand2");
        brandPojo.setCategory("testcategory");
        brandDao.add(brandPojo);
    }
    @Test
    public void addTest() throws ApiException {
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory");
        int id = brandDao.add(brandPojo);
        BrandMasterPojo brandData=brandDao.get(id);
        assertEquals("testbrand",brandData.getBrand());
        assertEquals("testcategory",brandData.getCategory());
    }
    @Test
    public void deleteTest() throws ApiException{
        brandDao.delete(brandId);
        Assert.assertNull(brandDao.get(brandId));
    }
    @Test
    public void getByBrandTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory");
        brandDao.add(brandPojo);
        List<BrandMasterPojo> brandData=brandDao.getByBrand("testbrand");
        for(BrandMasterPojo brandIterator : brandData) {
            assertEquals("testbrand", brandIterator.getBrand());
        }
    }
    @Test
    public void getByCategoryTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory");
        brandDao.add(brandPojo);
        List<BrandMasterPojo> brandData=brandDao.getByCategory("testcategory");
        for(BrandMasterPojo brandIterator : brandData) {
            assertEquals("testcategory", brandIterator.getCategory());
        }
    }
    @Test
    public void getByBrandCategoryTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory");
        brandDao.add(brandPojo);
        BrandMasterPojo brandData=brandDao.getByBrandCategory("testbrand","testcategory");
        assertEquals("testbrand",brandData.getBrand());
        assertEquals("testcategory",brandData.getCategory());
    }
    @Test
    public void checkUniqueTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory2");
        brandDao.checkUnique(brandPojo.getBrand(),brandPojo.getCategory());
    }
}
