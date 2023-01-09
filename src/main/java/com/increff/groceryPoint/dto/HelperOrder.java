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
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static com.increff.groceryPoint.dto.HelperProduct.checkProductExists;


@Service
public class HelperOrder {
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private ProductMasterApi pApi;
    @Autowired
    private InventoryMasterApi invApi;
    public OrderMasterData convert(OrderMasterPojo p) {
        OrderMasterData d = new OrderMasterData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
        String formattedString = p.getTime().format(formatter);
        d.setTime(formattedString);
        d.setId(p.getId());
        return d;
    }

    public OrderItemMasterPojo convert(OrderItemMasterForm form) throws ApiException{
        OrderItemMasterPojo p=new OrderItemMasterPojo();
        p.setProductId(form.getProductId());
        p.setQuantity(form.getQuantity());
        p.setOrderId(form.getOrderId());
        p.setSellingPrice(pApi.getProductApi(form.getProductId()).getMrp()*form.getQuantity());
        return p;
    }
    public OrderItemMasterData convert(OrderItemMasterPojo p){
        OrderItemMasterData data=new OrderItemMasterData();
        data.setProductId(p.getProductId());
        data.setQuantity(p.getQuantity());
        data.setId(p.getId());
        data.setSellingPrice(p.getSellingPrice());
        data.setOrderId(p.getOrderId());
        return data;
    }
    public void checkOrderExists(int orderId) throws ApiException{
        if(orderApi.getOrderApi(orderId)==null){
            throw new ApiException("Order Id does not exist");
        }
    }
    public void checkQuantityExists(int id,int qty) throws ApiException{
        int availableQty=invApi.getInventoryApi(id).getQuantity();
        if(availableQty<qty){
            throw new ApiException("Not enough Quantity Available");
        }
    }
    public void isOrderItemValid(OrderItemMasterForm form) throws ApiException{
        int productId=form.getProductId();
        pApi.getProductApi(productId);
        checkOrderExists(form.getOrderId());
        checkQuantityExists(productId,form.getQuantity());


    }
}
