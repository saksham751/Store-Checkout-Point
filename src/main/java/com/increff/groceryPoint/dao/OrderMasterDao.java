package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderMasterDao extends AbstractDao{
    private static String select_All="select order from OrderMasterPojo order";
    private static String select ="select order from OrderMasterPojo order where id =: id";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addOrderDao(OrderMasterPojo order){
        em.persist(order);
    }

    @Transactional
    public OrderMasterPojo selectOrderDao(int id){
        TypedQuery<OrderMasterPojo> query = getQuery(select, OrderMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<OrderMasterPojo> selectAllOrderDao(){
        TypedQuery<OrderMasterPojo> query = getQuery(select_All, OrderMasterPojo.class);
        return query.getResultList();
    }
    @Transactional
    public void updateOrderDao(OrderMasterPojo p,OrderMasterPojo ex){
        ex.setTime(p.getTime());
    }
}
