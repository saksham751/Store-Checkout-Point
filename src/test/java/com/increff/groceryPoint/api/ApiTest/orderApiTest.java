package com.increff.groceryPoint.api.ApiTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.Helper.OrderHelper;
import com.increff.groceryPoint.dto.OrderMasterDto;
import com.increff.groceryPoint.pojo.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class orderApiTest extends AbstractUnitTest {
    @Autowired
    private OrderMasterDto orderDto;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private OrderHelper orderHelp;
    private Integer brandCategory,productId,invId,orderId;
    @Before
    public void addProduct() throws ApiException {
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
        InventoryMasterPojo inventoryPojo = new InventoryMasterPojo();
        inventoryPojo.setQuantity(20);
        inventoryPojo.setId(productId);
        invId=invApi.add(inventoryPojo);
    }

    @Test
    public void testAdd() throws ApiException{
        OrderMasterPojo orderPojo = new OrderMasterPojo();
        orderPojo.setStatus("Pending");
        Date time=Date.from(Instant.now());
        orderPojo.setTime(time);
        int orderid=orderApi.add(orderPojo);
        OrderMasterPojo orderData = orderApi.get(orderid);
        assertEquals("Pending",orderData.getStatus());
    }

    @Test
    public void testUpdate() throws ApiException{
        OrderMasterPojo orderPojo = new OrderMasterPojo();
        orderPojo.setStatus("Pending");
        Date time=Date.from(Instant.now());
        orderPojo.setTime(time);
        int orderid=orderApi.add(orderPojo);
        OrderMasterPojo orderUpdate = new OrderMasterPojo();
        orderUpdate.setStatus("Placed");
        Date newTime=Date.from(Instant.now());
        orderUpdate.setTime(newTime);
        orderApi.update(orderid,orderUpdate);
        OrderMasterPojo orderData = orderApi.get(orderid);
        assertEquals("Placed",orderData.getStatus());
        assertEquals(newTime,orderData.getTime());
    }
    @Test(expected = ApiException.class)
    public void testUpdateNull() throws ApiException{
        OrderMasterPojo orderUpdate = new OrderMasterPojo();
        orderUpdate.setStatus("Placed");
        Date newTime=Date.from(Instant.now());
        orderUpdate.setTime(newTime);
        orderApi.update(0,orderUpdate);
    }
}
