package com.increff.groceryPoint.api.daoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;

import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dao.InventoryMasterDao;
import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class inventoryDaoTest extends AbstractUnitTest {
    @Autowired
    private InventoryMasterDao invDao;
    @Autowired
    private ProductMasterDao productDao;
    @Autowired
    private BrandMasterDao brandDao;
    private Integer brandCategory;
    private Integer productId;
    @Before
    public void addBrandandProduct() throws ApiException {
        BrandMasterPojo brandCategoryPojo = new BrandMasterPojo();
        brandCategoryPojo.setBrand("testbrand");
        brandCategoryPojo.setCategory("testcategory");
        brandCategory=brandDao.add(brandCategoryPojo);
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        productId=productDao.add(productPojo);
    }

    @Test
    public void addTest() throws ApiException{
        InventoryMasterPojo invPojo = new InventoryMasterPojo();
        invPojo.setId(productId);
        invPojo.setQuantity(10);
        int id =invDao.add(invPojo);
        InventoryMasterPojo invData=invDao.get(id);
        assertEquals(Integer.valueOf(10),invData.getQuantity());
        assertEquals(Integer.valueOf(productId),invData.getId());
    }
}
