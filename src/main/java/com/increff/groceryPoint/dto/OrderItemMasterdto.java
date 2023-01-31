package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.OrderItemUpdateForm;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;


@Service
public class OrderItemMasterdto {
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private HelperOrder helpOrder;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private OrderMasterApi orderApi;

    public int add(OrderItemMasterForm form) throws ApiException {
        isOrderItemValid(form);
        OrderItemMasterPojo p=helpOrder.convert(form);
        isOrderItemPojoValid(p);
        OrderItemMasterPojo prevOrderItem=new OrderItemMasterPojo();
        try{
            prevOrderItem=orderItemApi.get(p.getOrderId(), p.getProductId());
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!isNull(prevOrderItem)){
            OrderItemUpdateForm updateForm = new OrderItemUpdateForm();
            updateForm.setOrderId(form.getOrderId());
            updateForm.setQuantity(form.getQuantity());
            updateForm.setProductId(p.getProductId());
            update(prevOrderItem.getId(),updateForm);
            return prevOrderItem.getId();
        }
        return orderItemApi.add(p);
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

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, OrderItemUpdateForm form) throws ApiException {
        helpOrder.isOrderItemUpdateValid(form);
        OrderItemMasterPojo prevOrderItem = orderItemApi.checkOrderItemExists(id);
        OrderItemMasterPojo orderItemPojo=helpOrder.convert(form);
        orderItemPojo.setId(id);

        Integer qtyNeeded=orderItemPojo.getQuantity()-prevOrderItem.getQuantity();
        orderItemPojo.setQuantity(qtyNeeded);
        orderItemPojo.setSellingPrice(productApi.get(orderItemPojo.getProductId()).getMrp()*qtyNeeded);
        if(qtyNeeded>0) {
            isOrderItemPojoValid(orderItemPojo);
        }

        InventoryMasterPojo inv= new InventoryMasterPojo();
        inv.setId(orderItemPojo.getProductId());
        inv.setQuantity(invApi.get(orderItemPojo.getProductId()).getQuantity()-orderItemPojo.getQuantity());
        invApi.update(orderItemPojo.getProductId(),inv);
        orderItemApi.update(orderItemPojo.getId(),orderItemPojo);
    }

    public List<OrderItemMasterData> getAllfromOrderId(Integer orderId) throws ApiException{
        List<OrderItemMasterPojo> list = orderItemApi.getAllfromOrderId(orderId);
        List<OrderItemMasterData> list2 = new ArrayList<OrderItemMasterData>();
        for (OrderItemMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }
    public void isOrderItemValid(OrderItemMasterForm form) throws ApiException{
        if(form.getQuantity()==null){
            throw new ApiException("Quantity cannot be empty");
        }
        if(form.getQuantity()<1 ){
            throw new ApiException("Quantity cannot be less than 1 or empty");
        }
        if(form.getOrderId()<0){
            throw new ApiException("Enter a Valid OrderId");
        }
        productApi.getfromBarcode(form.getBarcode());

    }
    public void isOrderItemPojoValid(OrderItemMasterPojo p) throws ApiException{
        int productId=p.getProductId();
        productApi.get(productId);
        orderApi.get(p.getOrderId());
        if(invApi.get(productId).getQuantity()<p.getQuantity()){
            throw new ApiException("Not enough Quantity Available");
        }

    }

}
