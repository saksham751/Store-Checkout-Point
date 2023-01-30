package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.OrderItemUpdateForm;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;


@Service
public class HelperOrder {
    @Autowired
    private ProductMasterApi productpApi;
    public OrderMasterData convert(OrderMasterPojo p) {
        OrderMasterData d = new OrderMasterData();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        d.setTime(formatter.format(p.getTime()));
        d.setId(p.getId());
        d.setStatus(p.getStatus());
        return d;
    }

    public OrderItemMasterPojo convert(OrderItemMasterForm form) throws ApiException{
        OrderItemMasterPojo p=new OrderItemMasterPojo();
        ProductMasterPojo product = productpApi.getfromBarcode(form.getBarcode());
        p.setProductId(product.getId());
        p.setQuantity(form.getQuantity());
        p.setOrderId(form.getOrderId());
        p.setSellingPrice(product.getMrp()*form.getQuantity());
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
    public OrderItemMasterPojo convert(OrderItemUpdateForm form) throws ApiException{
        OrderItemMasterPojo data = new OrderItemMasterPojo();
        ProductMasterPojo product = productpApi.get(form.getProductId());
        data.setOrderId(form.getOrderId());
        data.setQuantity(form.getQuantity());
        data.setProductId(form.getProductId());
        data.setSellingPrice(form.getQuantity()*product.getMrp());
        return data;
    }

    public void isOrderItemUpdateValid(OrderItemUpdateForm form) throws ApiException{
        if(form.getQuantity()<1) {
            throw new ApiException("Quantity cannot be less than 1");
        }
    }

}
