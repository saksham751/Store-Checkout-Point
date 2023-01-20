package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.OrderItemMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemMasterApi {
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private OrderItemMasterDao orderItemDao;
    @Autowired
    private InventoryMasterApi invApi;
    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderItemMasterPojo p) throws ApiException {
        isOrderItemValid(p);
        orderItemDao.add(p);
    }

    public OrderItemMasterPojo get(int id) throws ApiException {
        checkOrderItemExists(id);
        return orderItemDao.get(id);
    }

    public List<OrderItemMasterPojo> getAll() {
        return orderItemDao.getAll();
    }

    @Transactional
    public void update(int id, OrderItemMasterPojo p) throws ApiException {
        OrderItemMasterPojo ex = checkOrderItemExists(id);
        Integer prevQty=ex.getQuantity();
        OrderItemMasterPojo  checkInv= p;
        checkInv.setQuantity(p.getQuantity()-ex.getQuantity());
        isOrderItemValid(checkInv);
        p.setQuantity(p.getQuantity()+ex.getQuantity());
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        ex.setProductId(p.getProductId());
        InventoryMasterPojo inv= new InventoryMasterPojo();
        inv.setId(p.getProductId());
        inv.setQuantity(invApi.get(p.getProductId()).getQuantity()-p.getQuantity()+prevQty);
        invApi.update(p.getProductId(),inv);
        orderItemDao.update(ex);

    }
    public void delete(int id) throws ApiException{
        checkOrderItemExists(id);
        orderItemDao.delete(id);
    }

    public OrderItemMasterPojo get(Integer id, Integer productId) throws ApiException{
        return orderItemDao.get(id,productId);
    }

    public OrderItemMasterPojo checkOrderItemExists(int id) throws ApiException{
        OrderItemMasterPojo p = orderItemDao.get(id);
        if (p == null) {
            throw new ApiException("Order Item with given ID does not exit, id: " + id);
        }
        return p;
    }

    public void isOrderItemValid(OrderItemMasterPojo p) throws ApiException{
        int productId=p.getProductId();
        productApi.get(productId);
        orderApi.get(p.getOrderId());
        if(invApi.get(productId).getQuantity()<p.getQuantity()){
            throw new ApiException("Not enough Quantity Available");
        }

    }

    public List<OrderItemMasterPojo> getAllfromOrderId(Integer orderId) throws ApiException{
        return orderItemDao.getAllfromOrderId(orderId);
    }
}
