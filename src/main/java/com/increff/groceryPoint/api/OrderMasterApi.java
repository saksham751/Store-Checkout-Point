package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.OrderMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static com.increff.groceryPoint.dto.HelperProduct.checkProductExists;


@Service
public class OrderMasterApi {
    @Autowired
    private OrderMasterDao oDao;


    public void addOrderApi() throws ApiException {
        OrderMasterPojo omp= new OrderMasterPojo();
        omp.setTime(ZonedDateTime.now());
        oDao.addOrderDao(omp);
    }


    public OrderMasterPojo getOrderApi(int id) throws ApiException {
        OrderMasterPojo omp= oDao.selectOrderDao(id);
        if (omp == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return omp;
    }

    public List<OrderMasterPojo> getAllOrderApi() {
        return oDao.selectAllOrderDao();
    }


    public void updateOrderApi(int id, OrderMasterPojo p) throws ApiException {
        OrderMasterPojo ex = oDao.selectOrderDao(id);
        if (ex == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        oDao.updateOrderDao(p,ex);

    }

}
