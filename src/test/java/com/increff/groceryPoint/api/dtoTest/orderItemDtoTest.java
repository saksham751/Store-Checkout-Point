package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.*;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.Order;

import static org.junit.Assert.assertEquals;

public class orderItemDtoTest extends AbstractUnitTest {
    @Autowired
    private OrderMasterdto orderDto;
    @Autowired
    private ProductMasterdto productDto;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterdto invDto;
    @Autowired
    private OrderItemMasterdto orderItemDto;
    @Autowired
    private HelperOrder orderHelp;
    private Integer brandCategory,productId,invId,orderId;
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
        orderId=orderDto.add();
    }

    @Test
    public void testAdd() throws ApiException{
        OrderItemMasterForm orderItemForm= new OrderItemMasterForm();
        orderItemForm.setOrderId(orderId);
        orderItemForm.setBarcode("testb@rc0de");
        orderItemForm.setQuantity(2);
        int orderItemid=orderItemDto.add(orderItemForm);
        OrderItemMasterData orderItemData = orderItemDto.get(orderItemid);
        assertEquals(Integer.valueOf(productId),orderItemData.getProductId());
        assertEquals(Integer.valueOf(2),orderItemData.getQuantity());
        assertEquals(Double.valueOf(40.0),orderItemData.getSellingPrice());
        assertEquals(Integer.valueOf(orderId),orderItemData.getOrderId());
    }
    @Test(expected = ApiException.class)
    public void testDelete() throws ApiException{
        OrderItemMasterForm orderItemForm= new OrderItemMasterForm();
        orderItemForm.setOrderId(orderId);
        orderItemForm.setBarcode("testb@rc0de");
        orderItemForm.setQuantity(2);
        int orderItemid=orderItemDto.add(orderItemForm);
        orderItemDto.delete(orderItemid);
        orderItemDto.get(orderItemid);
    }
    @Test(expected = ApiException.class)
    public void testIsOrderItemValid_qty() throws ApiException{
        OrderItemMasterForm orderItemForm= new OrderItemMasterForm();
        orderItemForm.setOrderId(orderId);
        orderItemForm.setBarcode("testb@rc0de");
        orderItemForm.setQuantity(-2);
        orderItemDto.isOrderItemValid(orderItemForm);
    }
    @Test(expected = ApiException.class)
    public void testIsOrderItemValid_orderId() throws ApiException{
        OrderItemMasterForm orderItemForm= new OrderItemMasterForm();
        orderItemForm.setOrderId(-1);
        orderItemForm.setBarcode("testb@rc0de");
        orderItemForm.setQuantity(2);
        orderItemDto.isOrderItemValid(orderItemForm);
    }
    @Test(expected = ApiException.class)
    public void testIsOrderItemPojoValid_orderId() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(0);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(2);
        orderItemDto.isOrderItemPojoValid(orderItemPojo);
    }
    @Test(expected = ApiException.class)
    public void testIsOrderItemPojoValid_qty() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(productId);
        orderItemPojo.setQuantity(30);
        orderItemDto.isOrderItemPojoValid(orderItemPojo);
    }
    @Test(expected = ApiException.class)
    public void test_isOrderItemPojoValid_productId() throws ApiException{
        OrderItemMasterPojo orderItemPojo= new OrderItemMasterPojo();
        orderItemPojo.setOrderId(orderId);
        orderItemPojo.setProductId(0);
        orderItemPojo.setQuantity(2);
        orderItemDto.isOrderItemPojoValid(orderItemPojo);
    }

    @Test
    public void testUpdate() throws ApiException{
        OrderItemMasterForm orderItemForm= new OrderItemMasterForm();
        orderItemForm.setOrderId(orderId);
        orderItemForm.setBarcode("testb@rc0de");
        orderItemForm.setQuantity(2);
        int orderItemid=orderItemDto.add(orderItemForm);
        OrderItemUpdateForm orderItemUpdate = new OrderItemUpdateForm();
        orderItemUpdate.setOrderId(orderId);
        orderItemUpdate.setProductId(productId);
        orderItemUpdate.setQuantity(4);
        orderItemDto.update(orderItemid,orderItemUpdate);
        OrderItemMasterData orderItemMasterData = orderItemDto.get(orderItemid);
        assertEquals(Integer.valueOf(productId),orderItemMasterData.getProductId());
        assertEquals(Integer.valueOf(4),orderItemMasterData.getQuantity());
        assertEquals(Double.valueOf(80.0),orderItemMasterData.getSellingPrice());
        assertEquals(Integer.valueOf(orderId),orderItemMasterData.getOrderId());
    }
}
