package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.InventoryMasterdto;
import com.increff.groceryPoint.dto.HelperBrand;
import com.increff.groceryPoint.dto.ProductMasterdto;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import static com.increff.groceryPoint.dto.HelperBrand.normalize;
import static com.increff.groceryPoint.dto.HelperBrand.validateBrandForm;
import static com.increff.groceryPoint.dto.HelperInventory.convertFormtoPojo;
import static com.increff.groceryPoint.dto.HelperInventory.convertPojotoData;
import static org.junit.Assert.assertEquals;

public class inventoryDtoTest extends AbstractUnitTest {
    @Autowired
    private InventoryMasterdto invDto;
    @Autowired
    private ProductMasterdto productDto;
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
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("testproduct");
        productForm.setBarcode("testb@rc0de");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        productId=productDto.add(productForm);
    }
    @Test
    public void testAdd() throws ApiException {
        InventoryMasterForm invForm = new InventoryMasterForm();
        invForm.setId(productId);
        invForm.setQuantity(10);
        int id =invDto.add(invForm);
        InventoryMasterData invData=invDto.get(id);
        assertEquals(Integer.valueOf(10),invData.getQuantity());
        assertEquals(Integer.valueOf(productId),invData.getId());
    }
    @Test(expected = ApiException.class)
    public void testAddNegative() throws ApiException {
        InventoryMasterForm invForm = new InventoryMasterForm();
        invForm.setQuantity(-10);
        int id =invDto.add(invForm);
    }
    @Test(expected = ApiException.class)
    public void testaddnull() throws ApiException {
        InventoryMasterForm invForm = new InventoryMasterForm();
        int id =invDto.add(invForm);
    }
    @Test
    public void testUpdate() throws ApiException{
        InventoryMasterForm invForm = new InventoryMasterForm();
        invForm.setQuantity(10);
        invForm.setId(productId);
        int id=invDto.add(invForm);
        InventoryMasterForm invForm2 = new InventoryMasterForm();
        invForm2.setQuantity(20);
        invForm2.setId(productId);
        invDto.update(id,invForm2);
        InventoryMasterData invData =invDto.get(id);
        assertEquals(Integer.valueOf(20),invData.getQuantity());
        assertEquals(Integer.valueOf(productId),invData.getId());
    }
    @Test(expected = ApiException.class)
    public void testdoesProductExists() throws ApiException {
        invDto.doesProductExists(0);
    }

    @Test
    public void testConvertFormtoPojo(){
        InventoryMasterForm invForm = new InventoryMasterForm();
        invForm.setId(productId);
        invForm.setQuantity(10);
        InventoryMasterPojo invPojo= convertFormtoPojo(invForm);
        assertEquals(Integer.valueOf(10),invPojo.getQuantity());
        assertEquals(Integer.valueOf(productId),invPojo.getId());
    }
    @Test
    public void testConvertPojotoData(){
        InventoryMasterPojo invPojo = new InventoryMasterPojo();
        invPojo.setId(productId);
        invPojo.setQuantity(10);
        InventoryMasterData invData= convertPojotoData(invPojo);
        assertEquals(Integer.valueOf(10),invData.getQuantity());
        assertEquals(Integer.valueOf(productId),invData.getId());
    }
}
