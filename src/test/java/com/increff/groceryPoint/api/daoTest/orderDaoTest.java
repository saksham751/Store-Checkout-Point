package com.increff.groceryPoint.api.daoTest;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.dao.*;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.HelperOrder;
import com.increff.groceryPoint.dto.OrderMasterdto;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class orderDaoTest extends AbstractUnitTest {
    @Autowired
    private OrderMasterdto orderDto;
    @Autowired
    private ProductMasterDao productDao;
    @Autowired
    private BrandMasterDao brandDao;
    @Autowired
    private InventoryMasterDao invDao;
    @Autowired
    private OrderMasterDao orderDao;
    @Autowired
    private OrderItemMasterDao orderItemDao;
    @Autowired
    private HelperOrder orderHelp;
    private Integer brandCategory,productId,invId,orderId;
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
    }

    @Test
    public void testAdd() throws ApiException{
        OrderMasterPojo orderPojo = new OrderMasterPojo();
        orderPojo.setStatus("Pending");
        Date time=Date.from(Instant.now());
        orderPojo.setTime(time);
        int orderid=orderDao.add(orderPojo);
        OrderMasterPojo orderData = orderDao.get(orderid);
        assertEquals("Pending",orderData.getStatus());
    }
    @Test
    public void getByDateFilterTest() throws ApiException{
        OrderMasterPojo orderPojo = new OrderMasterPojo();
        orderPojo.setStatus("Pending");
        Date time=Date.from(Instant.now());
        orderPojo.setTime(time);
        int orderid=orderDao.add(orderPojo);
        orderPojo.setStatus("Pending");
        Date d = Date.from(Instant.now());
        Date dateBefore = new Date(d.getTime() - 2 * 24 * 3600 * 1000l );
        Date dateBefore2 = new Date(d.getTime() - 1 * 24 * 3600 * 1000l );
        orderPojo.setTime(dateBefore);
        List<OrderMasterPojo> orderList =  orderDao.getByDateFilter(dateBefore2,time);
        for(OrderMasterPojo orderData:orderList){
            assertEquals("Pending",orderData.getStatus());
            assertEquals(time,orderData.getTime());
        }

    }
}
