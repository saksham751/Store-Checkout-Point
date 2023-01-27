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
    private OrderItemMasterDao orderItemDao;
    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderItemMasterPojo p) throws ApiException {
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
    public void update(OrderItemMasterPojo p,OrderItemMasterPojo ex) throws ApiException {
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        ex.setProductId(p.getProductId());
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


    public List<OrderItemMasterPojo> getAllfromOrderId(Integer orderId) throws ApiException{
        return orderItemDao.getAllfromOrderId(orderId);
    }
}
