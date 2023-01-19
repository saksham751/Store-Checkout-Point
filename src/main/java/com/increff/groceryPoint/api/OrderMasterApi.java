package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.OrderMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
@Service
public class OrderMasterApi {
    @Autowired
    private OrderMasterDao orderDao;


    public void add(OrderMasterPojo omp) throws ApiException {
        omp.setTime(Date.from(Instant.now()));
        omp.setStatus("Pending");
        orderDao.add(omp);
    }


    public OrderMasterPojo get(int id) throws ApiException {
        OrderMasterPojo omp= orderDao.get(id);
        if (omp == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return omp;
    }

    public List<OrderMasterPojo> getAll() {
        return orderDao.getAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, OrderMasterPojo p) throws ApiException {
        OrderMasterPojo ex = orderDao.get(id);
        if (ex == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        ex.setStatus(p.getStatus());
        ex.setTime(p.getTime());
        orderDao.update(p,ex);
    }

    public List<OrderMasterPojo> getByDateFilter(Date start, Date end) throws ApiException{
        List<OrderMasterPojo> orderList= orderDao.getByDateFilter(start,end);
        return orderList;
    }

}
