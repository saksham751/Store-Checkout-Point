package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.*;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import lombok.experimental.Helper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class orderDtoTest extends AbstractUnitTest {
    @Autowired
    private OrderMasterdto orderDto;
    @Autowired
    private ProductMasterdto productDto;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterdto invDto;
    @Autowired
    private HelperOrder orderHelp;
    private Integer brandCategory,productId,invId;
    @Before
    public void addProduct() throws ApiException {
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
        InventoryMasterForm inventoryForm = new InventoryMasterForm();
        inventoryForm.setQuantity(20);
        inventoryForm.setId(productId);
        invId=invDto.add(inventoryForm);

    }
    @Test
    public void test_add() throws ApiException {
        int id=orderDto.add();
        OrderMasterData orderData=orderDto.get(id);
        assertEquals(Integer.valueOf(id),orderData.getId());
    }

    @Test
    public void test_reduceInventory() throws ApiException{
        OrderItemMasterPojo orderItem =new OrderItemMasterPojo();
        int id=orderDto.add();
        orderItem.setOrderId(id);
        orderItem.setProductId(productId);
        orderItem.setQuantity(2);
        orderItem.setSellingPrice(20.0);
        orderItem.setId(1);
        orderDto.reduceInventory(id,orderItem);
        InventoryMasterData invData=invDto.get(productId);
        assertEquals(Integer.valueOf(18),invData.getQuantity());
    }
    @Test
    public void test_convert() {
        OrderMasterPojo orderPojo = new OrderMasterPojo();
        orderPojo.setStatus("Placed");
        Date time= Date.from(Instant.now());
        orderPojo.setTime(time);
        orderPojo.setId(0);
        OrderMasterData orderData = orderHelp.convert(orderPojo);
        assertEquals(Integer.valueOf(0),orderData.getId());
        assertEquals(time,orderData.getTime());
        assertEquals("Placed",orderData.getStatus());
    }
}
