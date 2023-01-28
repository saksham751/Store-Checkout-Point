package com.increff.groceryPoint.api.ApiTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class brandApiTest extends AbstractUnitTest {
    @Autowired
    private BrandMasterApi brandApi;

    @Test
    public void addTest() throws ApiException {
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("sports");
        int id =brandApi.add(brandPojo);
        BrandMasterPojo brandData=brandApi.get(id);
        assertEquals("puma",brandData.getBrand());
        assertEquals("sports",brandData.getCategory());
    }
    @Test(expected = ApiException.class)
    public void checkUniqueTest() throws ApiException {
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("sports");
        int id =brandApi.add(brandPojo);
        brandApi.checkUnique(brandPojo);
    }
    @Test(expected = ApiException.class)
    public void deleteTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("sports");
        int id =brandApi.add(brandPojo);
        brandApi.delete(id);
        brandApi.get(id);
    }
    @Test(expected = ApiException.class)
    public void getCheckTest() throws ApiException{
        brandApi.getCheck(0);
    }

    @Test
    public void updateTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("puma");
        brandPojo.setCategory("sports");
        int id =brandApi.add(brandPojo);
        BrandMasterPojo brandPojo2=new BrandMasterPojo();
        brandPojo2.setBrand("testbrand");
        brandPojo2.setCategory("testcategory");
        brandApi.update(id,brandPojo2);
        assertEquals("testbrand",brandPojo2.getBrand());
        assertEquals("testcategory",brandPojo2.getCategory());
    }
}
