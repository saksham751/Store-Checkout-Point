package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.InventoryMasterdto;
import com.increff.groceryPoint.dto.ProductMasterdto;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.increff.groceryPoint.dto.Helper.HelperInventory.convertFormtoPojo;
import static com.increff.groceryPoint.dto.Helper.HelperInventory.convertPojotoData;
import static org.junit.Assert.assertEquals;

public class inventoryDtoTest extends AbstractUnitTest {
    @Autowired
    private InventoryMasterdto invDto;
    @Autowired
    private ProductMasterdto productDto;
    @Autowired
    private BrandMasterApi brandApi;
    private Integer brandCategory;
    private Integer productId,productId2;
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
        ProductMasterForm productForm2 = new ProductMasterForm();
        productForm2.setProductName("testproduct2");
        productForm2.setBarcode("testb@rc0de2");
        productForm2.setBrand_category(brandCategory);
        productForm2.setMrp(40.0);
        productId2=productDto.add(productForm2);
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

    @Test
    public void testgetAll() throws ApiException{
        List<InventoryMasterForm> invList= Arrays.asList(
                new InventoryMasterForm(),
                new InventoryMasterForm()
        );
        invList.get(0).setId(productId);
        invList.get(0).setQuantity(10);
        invList.get(1).setId(productId2);
        invList.get(1).setQuantity(20);
        invDto.add(invList.get(0));
        invDto.add(invList.get(1));
        List<InventoryMasterData> invData=invDto.getAll();
        assertEquals(invList.get(0).getId(),invData.get(0).getId());
        assertEquals(invList.get(0).getQuantity(),invData.get(0).getQuantity());
        assertEquals(invList.get(1).getId(),invData.get(1).getId());
        assertEquals(invList.get(1).getQuantity(),invData.get(1).getQuantity());
    }
}
