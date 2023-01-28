package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.jboss.logging.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class OrderMasterDao extends AbstractDao{
    private static String select_All="select order from OrderMasterPojo order";
    private static String select ="select order from OrderMasterPojo order where id =: id";
    private static String select_between="select order from OrderMasterPojo order where time between :start and :end";

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = ApiException.class)
    public int add(OrderMasterPojo order){
        em.persist(order);
        return order.getId();
    }

    @Transactional(readOnly = true)
    public OrderMasterPojo get(int id){
        TypedQuery<OrderMasterPojo> query = getQuery(select, OrderMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional(readOnly = true)
    public List<OrderMasterPojo> getAll(){
        TypedQuery<OrderMasterPojo> query = getQuery(select_All, OrderMasterPojo.class);
        return query.getResultList();
    }

    public void update(OrderMasterPojo p, OrderMasterPojo ex){

    }
    @Transactional(readOnly = true)
    public List<OrderMasterPojo> getByDateFilter(Date start, Date end){
        TypedQuery<OrderMasterPojo> query=em.createQuery(select_between, OrderMasterPojo.class);
        query.setParameter("start", start);
        query.setParameter("end",end);
        return query.getResultList();
    }
}
