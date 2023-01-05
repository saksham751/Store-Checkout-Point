package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.groceryPoint.dto.HelperOrder.*;

@Service
public class OrderItemMasterdto {
    @Autowired
    private OrderItemMasterApi orderItemApi;

    public void addOrderItemDto(OrderItemMasterForm form) throws ApiException {
        isOrderItemValid(form);
        OrderItemMasterPojo p=convert(form);
        orderItemApi.addOrderItemApi(p);
    }

    public void deleteOrderItemDto(int id) throws ApiException{
        orderItemApi.deleteOrderItemApi(id);
    }

    public OrderItemMasterData getOrderItemDto(int id) throws ApiException {
        return convert(orderItemApi.getOrderItemApi(id));
    }

    public List<OrderItemMasterData> getAllOrderItemDto() throws ApiException{
        List<OrderItemMasterPojo> list = orderItemApi.getAllOrderItemApi();
        List<OrderItemMasterData> list2 = new ArrayList<OrderItemMasterData>();
        for (OrderItemMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void updateOrderItemDto(int id, OrderItemMasterForm form) throws ApiException {
        isOrderItemValid(form);
        OrderItemMasterPojo p=convert(form);
        orderItemApi.updateOrderItemApi(id,p);
    }



}
