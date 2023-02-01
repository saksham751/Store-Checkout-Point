package com.increff.groceryPoint.api.ApiTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.Helper.HelperOrder;
import com.increff.groceryPoint.dto.OrderMasterdto;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class orderItemApiTest extends AbstractUnitTest {
    @Autowired
    private OrderMasterdto orderDto;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private HelperOrder orderHelp;
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
        orderId=orderDto.add();
    }

    @Test
    public void testAdd() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(40.0);
        int orderItemid=orderItemApi.add(orderItemPojo);
        OrderItemMasterPojo orderItemData = orderItemApi.get(orderItemid);
        assertEquals(Integer.valueOf(productId),orderItemData.getProductId());
        assertEquals(Integer.valueOf(2),orderItemData.getQuantity());
        assertEquals(Double.valueOf(40.0),orderItemData.getSellingPrice());
        assertEquals(Integer.valueOf(orderId),orderItemData.getOrderId());
    }

    @Test
    public void testUpdate() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(40.0);
        int orderItemId=orderItemApi.add(orderItemPojo);
        OrderItemMasterPojo orderItemUpdate= new OrderItemMasterPojo();
        orderItemUpdate.setProductId(productId);
        orderItemUpdate.setQuantity(4);
        orderItemUpdate.setSellingPrice(80.0);
        orderItemApi.update(orderItemId,orderItemUpdate);
        OrderItemMasterPojo orderItemData = orderItemApi.get(orderItemId);
        assertEquals(Integer.valueOf(productId),orderItemData.getProductId());
        assertEquals(Integer.valueOf(4),orderItemData.getQuantity());
        assertEquals(Double.valueOf(80.0),orderItemData.getSellingPrice());
        assertEquals(Integer.valueOf(orderId),orderItemData.getOrderId());
    }

    @Test(expected = ApiException.class)
    public void testDelete() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(40.0);
        int orderItemId=orderItemApi.add(orderItemPojo);
        orderItemApi.delete(orderItemId);
        orderItemApi.get(orderItemId);
    }
    @Test(expected = ApiException.class)
    public void testOrderItemExists() throws ApiException{
        orderItemApi.checkOrderItemExists(0);
    }
}
