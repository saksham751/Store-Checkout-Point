package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.OrderItemUpdateForm;
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

    public void add(OrderItemMasterForm form) throws ApiException {
        helpOrder.isOrderItemValid(form);
        OrderItemMasterPojo p=helpOrder.convert(form);
        OrderItemMasterPojo prevOrderItem=orderItemApi.get(p.getOrderId(), p.getProductId());
        if(prevOrderItem!=null){
            OrderItemUpdateForm updateForm = new OrderItemUpdateForm();
            updateForm.setOrderId(form.getOrderId());
            updateForm.setQuantity(form.getQuantity());
            updateForm.setProductId(p.getProductId());
            update(prevOrderItem.getId(),updateForm);
            return;
        }
        orderItemApi.add(p);
    }

    public void delete(int id) throws ApiException{
        orderItemApi.delete(id);
    }

    public OrderItemMasterData get(int id) throws ApiException {
        return helpOrder.convert(orderItemApi.get(id));
    }

    public List<OrderItemMasterData> getAll() throws ApiException{
        List<OrderItemMasterPojo> list = orderItemApi.getAll();
        List<OrderItemMasterData> list2 = new ArrayList<OrderItemMasterData>();
        for (OrderItemMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }

    public void update(int id, OrderItemUpdateForm form) throws ApiException {
        helpOrder.isOrderItemUpdateValid(form);
        OrderItemMasterPojo p=helpOrder.convert(form);
        p.setId(id);
        orderItemApi.update(id,p);
    }

    public List<OrderItemMasterData> getAllfromOrderId(Integer orderId) throws ApiException{
        List<OrderItemMasterPojo> list = orderItemApi.getAllfromOrderId(orderId);
        List<OrderItemMasterData> list2 = new ArrayList<OrderItemMasterData>();
        for (OrderItemMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }

}
