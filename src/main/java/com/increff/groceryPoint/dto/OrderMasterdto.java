package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
public class OrderMasterdto {
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private HelperOrder helpOrder;
    public void add() throws ApiException{
        OrderMasterPojo omp= new OrderMasterPojo();
        orderApi.add(omp);
    }
    public OrderMasterData get(int id) throws ApiException {
        return helpOrder.convert(orderApi.get(id));
    }

    public List<OrderMasterData> getAll() throws ApiException{
        List<OrderMasterPojo> list = orderApi.getAll();
        List<OrderMasterData> list2 = new ArrayList<OrderMasterData>();
        for (OrderMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }

    public void placeOrder(int id) throws ApiException {
        List<OrderItemMasterPojo> orderItemList = orderItemApi.getAllfromOrderId(id);
        for (OrderItemMasterPojo orderItem : orderItemList) {
            orderApi.placeOrder(id,orderItem);
        }
        OrderMasterPojo orderPojo = orderApi.get(id);
        orderPojo.setStatus("Placed");
        orderApi.update(id, orderPojo);
    }
}
