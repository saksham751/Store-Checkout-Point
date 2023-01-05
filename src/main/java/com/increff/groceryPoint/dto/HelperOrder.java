package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;

import static com.increff.groceryPoint.dto.HelperProduct.checkProductExists;


public class HelperOrder {
    @Autowired
    private static OrderMasterApi orderApi;
    @Autowired
    private static ProductMasterApi pApi;
    @Autowired
    private static InventoryMasterApi invApi;
    public static OrderMasterData convert(OrderMasterPojo p) {
        OrderMasterData d = new OrderMasterData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
        String formattedString = p.getTime().format(formatter);
        d.setTime(formattedString);
        return d;
    }

    public static OrderItemMasterPojo convert(OrderItemMasterForm form){
        OrderItemMasterPojo p=new OrderItemMasterPojo();
        p.setProductId(form.getProductId());
        p.setQuantity(form.getQuantity());
        return p;
    }
    public static OrderItemMasterData convert(OrderItemMasterPojo p){
        OrderItemMasterData data=new OrderItemMasterData();
        data.setProductId(p.getProductId());
        data.setQuantity(p.getQuantity());
        data.setId(p.getId());
        data.setSellingPrice(p.getSellingPrice());
        data.setOrderId(p.getOrderId());
        return data;
    }
    public static void checkOrderExists(int orderId) throws ApiException{
        if(orderApi.getOrderApi(orderId)!=null){
            throw new ApiException("Order Id already exists ");
        }
    }
    public static void checkQuantityExists(int id,int qty) throws ApiException{
        int availableQty=invApi.getInventoryApi(id).getQuantity();
        if(availableQty<qty){
            throw new ApiException("Not enough Quantity Available");
        }
    }
    public static void isOrderItemValid(OrderItemMasterForm form) throws ApiException{
        int productId=form.getProductId();
        pApi.getProductApi(productId);
        checkQuantityExists(productId,form.getQuantity());

    }
}
