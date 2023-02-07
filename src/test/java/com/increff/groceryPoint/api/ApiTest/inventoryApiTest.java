package com.increff.groceryPoint.api.ApiTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.Assert.assertEquals;

public class inventoryApiTest extends AbstractUnitTest {
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    private Integer brandCategory;
    private Integer productId;
    @Before
    public void addBrandandProduct() throws ApiException {
        BrandMasterPojo brandCategoryPojo = new BrandMasterPojo();
        brandCategoryPojo.setBrand("testbrand");
        brandCategoryPojo.setCategory("testcategory");
        brandCategory=brandApi.add(brandCategoryPojo);
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(20.0);
        productId=productApi.add(productPojo);
    }

    @Test
    public void addTest() throws ApiException{
        InventoryMasterPojo invPojo = new InventoryMasterPojo();
        invPojo.setId(productId);
        invPojo.setQuantity(10);
        int id =invApi.add(invPojo);
        InventoryMasterPojo invData=invApi.get(id);
        assertEquals(Integer.valueOf(10),invData.getQuantity());
        assertEquals(Integer.valueOf(productId),invData.getId());
    }
    @Test(expected = ApiException.class)
    public void addTestDuplicate() throws ApiException{
        InventoryMasterPojo invPojo = new InventoryMasterPojo();
        invPojo.setId(productId);
        invPojo.setQuantity(10);
        int id =invApi.add(invPojo);
        invApi.get(id);
        invPojo.setQuantity(20);
        invApi.add(invPojo);
    }
    @Test
    public void updateTest() throws ApiException{
        InventoryMasterPojo invPojo = new InventoryMasterPojo();
        invPojo.setId(productId);
        invPojo.setQuantity(10);
        int id =invApi.add(invPojo);
        InventoryMasterPojo invPojo2 = new InventoryMasterPojo();
        invPojo2.setId(productId);
        invPojo2.setQuantity(20);
        invApi.update(productId,invPojo2);
        InventoryMasterPojo invData = invApi.get(productId);
        assertEquals(Integer.valueOf(20),invPojo2.getQuantity());
        assertEquals(Integer.valueOf(productId),invPojo2.getId());
    }
    @Test
    public void checkInvTest() throws ApiException{
        InventoryMasterPojo invPojo = new InventoryMasterPojo();
        invPojo.setId(productId);
        InventoryMasterPojo invPojo2=invApi.checkInvExists(productId);
        assertEquals(Integer.valueOf(0),invPojo2.getQuantity());
        assertEquals(Integer.valueOf(productId),invPojo2.getId());
    }
}
