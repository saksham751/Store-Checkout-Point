package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.groceryPoint.dto.HelperOrder.convert;

@Service
public class OrderMasterdto {
    private OrderMasterApi orderApi;

    public void addOrderMasterDto() throws ApiException{
        orderApi.addOrderApi();
    }
    public OrderMasterData getOrderDto(int id) throws ApiException {
        return convert(orderApi.getOrderApi(id));
    }

    public List<OrderMasterData> getAllOrderDto() throws ApiException{
        List<OrderMasterPojo> list = orderApi.getAllOrderApi();
        List<OrderMasterData> list2 = new ArrayList<OrderMasterData>();
        for (OrderMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
}
