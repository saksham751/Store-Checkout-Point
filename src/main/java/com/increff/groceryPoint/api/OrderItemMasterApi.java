package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.OrderItemMasterDao;
import com.increff.groceryPoint.dao.OrderMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.increff.groceryPoint.dto.HelperProduct.checkProductExists;

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
    public void addOrderItemApi(OrderItemMasterPojo p) throws ApiException {
        isOrderItemValid(p);
        orderItemDao.addOrderItemDao(p);
        //orderApi.addOrderApi(p);
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
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        ex.setProductId(p.getProductId());
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

}
