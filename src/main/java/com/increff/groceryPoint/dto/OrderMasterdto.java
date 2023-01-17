package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class OrderMasterdto {
    @Autowired
    private OrderMasterApi orderApi;

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
        OrderMasterPojo orderPojo = orderApi.get(id);
        orderPojo.setStatus("Placed");
        orderApi.update(id, orderPojo);
    }
}
