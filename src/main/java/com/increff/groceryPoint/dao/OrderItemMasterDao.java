package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
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
    private static String select_orderproduct="select orderitem from OrderItemMasterPojo orderitem where orderId =: orderId and productId =: productId";
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = ApiException.class)
    public int add(OrderItemMasterPojo orderItem){
        em.persist(orderItem);
        return orderItem.getId();
    }
    @Transactional(readOnly = true)
    public OrderItemMasterPojo get(int id){
        TypedQuery<OrderItemMasterPojo> query = getQuery(select, OrderItemMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional(readOnly = true)
    public List<OrderItemMasterPojo> getAll(){
        TypedQuery<OrderItemMasterPojo> query = getQuery(select_All, OrderItemMasterPojo.class);
        return query.getResultList();
    }

    @Transactional(rollbackFor = ApiException.class)
    public int delete(int id){
        Query query = em.createQuery(delete_OrderItem);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public void update(OrderItemMasterPojo ex){

    }

    @Transactional(readOnly = true)
    public List<OrderItemMasterPojo> getAllfromOrderId(Integer orderId){
        TypedQuery<OrderItemMasterPojo> query = getQuery(select_orderId, OrderItemMasterPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public OrderItemMasterPojo get(Integer id, Integer productId) throws ApiException {
        TypedQuery<OrderItemMasterPojo> query = getQuery(select_orderproduct, OrderItemMasterPojo.class);
        query.setParameter("orderId", id);
        query.setParameter("productId", productId);
        return getSingle(query);
    }
}
