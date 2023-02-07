package com.increff.groceryPoint.api.daoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class productDaoTest extends AbstractUnitTest {
    @Autowired
    private ProductMasterDao productDao;
    @Autowired
    private BrandMasterDao brandDao;
    private Integer brandCategory,productId;
    @Before
    public void addBrand() throws ApiException {
        BrandMasterPojo brandCategoryPojo = new BrandMasterPojo();
        brandCategoryPojo.setBrand("testbrand");
        brandCategoryPojo.setCategory("testcategory");
        brandCategory=brandDao.add(brandCategoryPojo);
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct2");
        productPojo.setBarcode("testb@rc0de2");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        productId=productDao.add(productPojo);
    }
    @Test
    public void testAdd() throws ApiException {
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        productId=productDao.add(productPojo);
        ProductMasterPojo productData=productDao.get(productId);
        assertEquals("testproduct",productData.getName());
        assertEquals("testb@rc0de",productData.getBarcode());
        assertEquals(brandCategory,productData.getBrand_category());
        assertEquals(Double.valueOf(20.0),productData.getMrp());
    }

    @Test
    public void deleteTest() throws ApiException{
        productDao.delete(productId);
        Assert.assertNull(brandDao.get(productId));
    }
    @Test
    public void getTest() throws ApiException{
        ProductMasterPojo productPojo = productDao.get(productId);
        assertEquals("testproduct2", productPojo.getName());
        assertEquals("testb@rc0de2", productPojo.getBarcode());
        assertEquals(brandCategory, productPojo.getBrand_category());
        assertEquals(Double.valueOf(20.0), productPojo.getMrp());

    }
    @Test
    public void getfromBarcodeTest() throws ApiException{
        ProductMasterPojo productPojo = productDao.getfromBarcode("testb@rc0de2");
        assertEquals("testproduct2",productPojo.getName());
        assertEquals("testb@rc0de2",productPojo.getBarcode());
        assertEquals(brandCategory,productPojo.getBrand_category());
        assertEquals(Double.valueOf(20.0),productPojo.getMrp());
    }
}
