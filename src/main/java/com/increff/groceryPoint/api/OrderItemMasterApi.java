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
    public void addOrderItemApi(OrderItemMasterPojo p) throws ApiException {
        isOrderItemValid(p);
        InventoryMasterPojo inv= new InventoryMasterPojo();
        inv.setId(p.getProductId());
        inv.setQuantity(invApi.getInventoryApi(p.getProductId()).getQuantity()-p.getQuantity());
        invApi.updateInventoryApi(p.getProductId(),inv);
        orderItemDao.addOrderItemDao(p);
    }

    public OrderItemMasterPojo getOrderItemApi(int id) throws ApiException {
        checkOrderItemExists(id);
        return orderItemDao.selectOrderItemDao(id);
    }

    public List<OrderItemMasterPojo> getAllOrderItemApi() {
        return orderItemDao.selectAllOrderItemDao();
    }

    @Transactional
    public void updateOrderItemApi(int id, OrderItemMasterPojo p) throws ApiException {
        OrderItemMasterPojo ex = checkOrderItemExists(id);
        isOrderItemValid(p);
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        ex.setProductId(p.getProductId());
        InventoryMasterPojo inv= new InventoryMasterPojo();
        inv.setId(p.getProductId());
        inv.setQuantity(invApi.getInventoryApi(p.getProductId()).getQuantity()-p.getQuantity());
        invApi.updateInventoryApi(p.getProductId(),inv);
        orderItemDao.updateOrderDao(ex);

    }
    public void deleteOrderItemApi(int id) throws ApiException{
        checkOrderItemExists(id);
        orderItemDao.deleteOrderItemDao(id);
    }

    public OrderItemMasterPojo checkOrderItemExists(int id) throws ApiException{
        OrderItemMasterPojo p = orderItemDao.selectOrderItemDao(id);
        if (p == null) {
            throw new ApiException("Order Item with given ID does not exit, id: " + id);
        }
        return p;
    }

    public void isOrderItemValid(OrderItemMasterPojo p) throws ApiException{
        int productId=p.getProductId();
        productApi.getProductApi(productId);
        orderApi.getOrderApi(p.getOrderId());
        if(invApi.getInventoryApi(productId).getQuantity()<p.getQuantity()){
            throw new ApiException("Not enough Quantity Available");
        }

    }

    public List<OrderItemMasterPojo> getAllOrderItemOrderIdApi(Integer orderId) throws ApiException{
        return orderItemDao.getAllOrderItemOrderIdDao(orderId);
    }
}
