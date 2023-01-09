package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OrderItemMasterdto {
    @Autowired
    private OrderItemMasterApi orderItemApi;

    @Autowired
    private HelperOrder helpOrder;

    public void addOrderItemDto(OrderItemMasterForm form) throws ApiException {
        helpOrder.isOrderItemValid(form);
        OrderItemMasterPojo p=helpOrder.convert(form);
        orderItemApi.addOrderItemApi(p);
    }

    public void deleteOrderItemDto(int id) throws ApiException{
        orderItemApi.deleteOrderItemApi(id);
    }

    public OrderItemMasterData getOrderItemDto(int id) throws ApiException {
        return helpOrder.convert(orderItemApi.getOrderItemApi(id));
    }

    public List<OrderItemMasterData> getAllOrderItemDto() throws ApiException{
        List<OrderItemMasterPojo> list = orderItemApi.getAllOrderItemApi();
        List<OrderItemMasterData> list2 = new ArrayList<OrderItemMasterData>();
        for (OrderItemMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }

    public void updateOrderItemDto(int id, OrderItemMasterForm form) throws ApiException {
        helpOrder.isOrderItemValid(form);
        OrderItemMasterPojo p=helpOrder.convert(form);
        orderItemApi.updateOrderItemApi(id,p);
    }



}
