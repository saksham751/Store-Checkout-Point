package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class OrderMasterdto {
    @Autowired
    private OrderMasterApi orderApi;

    @Autowired
    private HelperOrder helpOrder;
    public void addOrderMasterDto() throws ApiException{
        OrderMasterPojo omp= new OrderMasterPojo();
        omp.setTime(ZonedDateTime.now());
        orderApi.addOrderApi(omp);
    }
    public OrderMasterData getOrderDto(int id) throws ApiException {
        return helpOrder.convert(orderApi.getOrderApi(id));
    }

    public List<OrderMasterData> getAllOrderDto() throws ApiException{
        List<OrderMasterPojo> list = orderApi.getAllOrderApi();
        List<OrderMasterData> list2 = new ArrayList<OrderMasterData>();
        for (OrderMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }
}
