package com.increff.groceryPoint.api.ApiTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.ProductMasterdto;
import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class productApiTest extends AbstractUnitTest {
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    private Integer brandCategory;
    @Before
    public void addBrand() throws ApiException {
        BrandMasterPojo brandCategoryPojo = new BrandMasterPojo();
        brandCategoryPojo.setBrand("testbrand");
        brandCategoryPojo.setCategory("testcategory");
        brandCategory=brandApi.add(brandCategoryPojo);
    }
    @Test
    public void testAdd() throws ApiException {
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        int id =productApi.add(productPojo);
        ProductMasterPojo productData=productApi.get(id);
        assertEquals("testproduct",productData.getName());
        assertEquals("testb@rc0de",productData.getBarcode());
        assertEquals(brandCategory,productData.getBrand_category());
        assertEquals(Double.valueOf(20.0),productData.getMrp());
    }
    @Test(expected = ApiException.class)
    public void testCheckbarcode() throws ApiException{
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        productApi.add(productPojo);
        productApi.checkBarcodeExists("testb@rc0de");
    }
    @Test
    public void testUpdate() throws ApiException{
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        int id=productApi.add(productPojo);
        ProductMasterPojo productPojo2 = new ProductMasterPojo();
        productPojo2.setName("producttest");
        productPojo2.setBarcode("testb@rc0de");
        productPojo2.setBrand_category(brandCategory);
        productPojo2.setMrp(20.0);
        productApi.update(id,productPojo2);
        ProductMasterPojo productPojo_2 =productApi.get(id);
        assertEquals("producttest",productPojo_2.getName());
        assertEquals("testb@rc0de",productPojo_2.getBarcode());
        assertEquals(brandCategory,productPojo_2.getBrand_category());
        assertEquals(Double.valueOf(20.0),productPojo_2.getMrp());
    }
    @Test(expected = ApiException.class)
    public void testDelete() throws ApiException{
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        int id =productApi.add(productPojo);
        productApi.delete(id);
        productApi.get(id);
    }
}
