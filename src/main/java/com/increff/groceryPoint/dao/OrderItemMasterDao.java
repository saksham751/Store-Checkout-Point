package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemMasterDao extends AbstractDao{
    private static String select_All="select orderitem from OrderItemMasterPojo orderitem";
    private static String select ="select orderitem from OrderItemMasterPojo orderitem where id =: id";
    private static String delete_OrderItem="delete from OrderItemMasterPojo o where id =: id";
    private static String select_orderId="select orderitem from OrderItemMasterPojo orderitem where orderId =: orderId";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addOrderItemDao(OrderItemMasterPojo orderItem){
        em.persist(orderItem);
    }
    @Transactional
    public OrderItemMasterPojo selectOrderItemDao(int id){
        TypedQuery<OrderItemMasterPojo> query = getQuery(select, OrderItemMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<OrderItemMasterPojo> selectAllOrderItemDao(){
        TypedQuery<OrderItemMasterPojo> query = getQuery(select_All, OrderItemMasterPojo.class);
        return query.getResultList();
    }

    @Transactional
    public int deleteOrderItemDao(int id){
        Query query = em.createQuery(delete_OrderItem);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional
    public void updateOrderDao(OrderItemMasterPojo ex){

    }

    @Transactional
    public List<OrderItemMasterPojo> getAllOrderItemOrderIdDao(Integer orderId){
        TypedQuery<OrderItemMasterPojo> query = getQuery(select_orderId, OrderItemMasterPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }
}
