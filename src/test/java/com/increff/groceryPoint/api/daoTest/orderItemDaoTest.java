package com.increff.groceryPoint.api.daoTest;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.dao.BrandMasterDao;
import com.increff.groceryPoint.dao.InventoryMasterDao;
import com.increff.groceryPoint.dao.OrderItemMasterDao;
import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.HelperOrder;
import com.increff.groceryPoint.dto.OrderMasterdto;
import com.increff.groceryPoint.pojo.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class orderItemDaoTest extends AbstractUnitTest {
    @Autowired
    private OrderMasterdto orderDto;
    @Autowired
    private ProductMasterDao productDao;
    @Autowired
    private BrandMasterDao brandDao;
    @Autowired
    private InventoryMasterDao invDao;
    @Autowired
    private OrderItemMasterDao orderItemDao;
    @Autowired
    private HelperOrder orderHelp;
    private Integer brandCategory,productId,invId,orderId,orderItemId;
    @Before
    public void addProduct() throws ApiException {
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
        InventoryMasterPojo inventoryPojo = new InventoryMasterPojo();
        inventoryPojo.setQuantity(20);
        inventoryPojo.setId(productId);
        invId=invDao.add(inventoryPojo);
        orderId=orderDto.add();
    }

    @Test
    public void testAdd() throws ApiException {
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(40.0);
        int orderItemid=orderItemDao.add(orderItemPojo);
        OrderItemMasterPojo orderItemData = orderItemDao.get(orderItemid);
        assertEquals(Integer.valueOf(productId),orderItemData.getProductId());
        assertEquals(Integer.valueOf(2),orderItemData.getQuantity());
        assertEquals(Double.valueOf(40.0),orderItemData.getSellingPrice());
        assertEquals(Integer.valueOf(orderId),orderItemData.getOrderId());
    }
    @Test
    public void deleteTest() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(40.0);
        int orderItemid=orderItemDao.add(orderItemPojo);
        orderItemDao.delete(orderItemid);
        Assert.assertNull(orderItemDao.get(orderItemid));
    }
    @Test
    public void getAllOrderIdTest() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(40.0);
        int orderItemid=orderItemDao.add(orderItemPojo);
        List<OrderItemMasterPojo> orderItemList =  orderItemDao.getAllfromOrderId(orderId);
        for(OrderItemMasterPojo orderItemData:orderItemList){
            assertEquals(Integer.valueOf(productId),orderItemData.getProductId());
            assertEquals(Integer.valueOf(2),orderItemData.getQuantity());
            assertEquals(Double.valueOf(40.0),orderItemData.getSellingPrice());
            assertEquals(Integer.valueOf(orderId),orderItemData.getOrderId());
        }
    }
}
