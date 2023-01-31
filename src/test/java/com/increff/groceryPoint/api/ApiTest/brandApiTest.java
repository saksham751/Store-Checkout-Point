package com.increff.groceryPoint.api.ApiTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import io.swagger.annotations.Api;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class brandApiTest extends AbstractUnitTest {
    @Autowired
    private BrandMasterApi brandApi;

    @Before
    public void addSomeBrandData() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        BrandMasterPojo brandPojo2 = new BrandMasterPojo();
        BrandMasterPojo brandPojo3 = new BrandMasterPojo();
        brandPojo.setBrand("brandTest");
        brandPojo.setCategory("testCategory");
        brandApi.add(brandPojo);

        brandPojo2.setBrand("brandTest");
        brandPojo2.setCategory("testCategory2");
        brandApi.add(brandPojo2);

        brandPojo3.setBrand("Test");
        brandPojo3.setCategory("testCategory");
        brandApi.add(brandPojo3);
    }
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
    @Test
    public void getByBrandTest() throws ApiException{
        List<BrandMasterPojo> brandList= Arrays.asList(
                new BrandMasterPojo(),
                new BrandMasterPojo()
        );
        brandList.get(0).setBrand("brandTest");
        brandList.get(0).setCategory("testCategory");
        brandList.get(1).setBrand("brandTest");
        brandList.get(1).setCategory("testCategory2");
        List<BrandMasterPojo> brandData=brandApi.getByBrand("brandTest");
        assertEquals(brandList.get(0).getBrand(),brandData.get(0).getBrand());
        assertEquals(brandList.get(0).getCategory(),brandData.get(0).getCategory());
        assertEquals(brandList.get(1).getBrand(),brandData.get(1).getBrand());
        assertEquals(brandList.get(1).getCategory(),brandData.get(1).getCategory());
    }
    @Test
    public void getByCategoryTest() throws ApiException{
        List<BrandMasterPojo> brandList= Arrays.asList(
                new BrandMasterPojo(),
                new BrandMasterPojo()
        );
        brandList.get(0).setBrand("brandTest");
        brandList.get(0).setCategory("testCategory");
        brandList.get(1).setBrand("Test");
        brandList.get(1).setCategory("testCategory");
        List<BrandMasterPojo> brandData=brandApi.getByCategory("testCategory");
        assertEquals(brandList.get(0).getBrand(),brandData.get(0).getBrand());
        assertEquals(brandList.get(0).getCategory(),brandData.get(0).getCategory());
        assertEquals(brandList.get(1).getBrand(),brandData.get(1).getBrand());
        assertEquals(brandList.get(1).getCategory(),brandData.get(1).getCategory());
    }
    @Test
    public void getByBrandCategoryTest() throws ApiException{
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("brandTest");
        brandPojo.setCategory("testCategory");
        BrandMasterPojo brandData=brandApi.getByBrandCategory("brandTest","testCategory");
        assertEquals("brandTest",brandData.getBrand());
        assertEquals("testCategory",brandData.getCategory());
    }
}

