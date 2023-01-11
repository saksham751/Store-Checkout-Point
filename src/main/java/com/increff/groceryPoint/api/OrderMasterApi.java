package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.OrderMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


@Service
public class OrderMasterApi {
    @Autowired
    private OrderMasterDao orderDao;


    public void addOrderApi(OrderMasterPojo omp) throws ApiException {
        omp.setTime(ZonedDateTime.now());
        omp.setStatus("Pending");
        orderDao.addOrderDao(omp);
    }


    public OrderMasterPojo getOrderApi(int id) throws ApiException {
        OrderMasterPojo omp= orderDao.selectOrderDao(id);
        if (omp == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return omp;
    }

    public List<OrderMasterPojo> getAllOrderApi() {
        return orderDao.selectAllOrderDao();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void updateOrderApi(int id, OrderMasterPojo p) throws ApiException {
        OrderMasterPojo ex = orderDao.selectOrderDao(id);
        if (ex == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        ex.setStatus(p.getStatus());
        ex.setTime(p.getTime());
        orderDao.updateOrderDao(p,ex);
    }


}
