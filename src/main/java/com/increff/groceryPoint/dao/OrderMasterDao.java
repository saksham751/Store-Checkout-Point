package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.jboss.logging.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderMasterDao extends AbstractDao{
    private static String select_All="select order from OrderMasterPojo order";
    private static String select ="select order from OrderMasterPojo order where id =: id";
    private static String select_between="select order from OrderMasterPojo order where time>=:start and time<=:end";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(OrderMasterPojo order){
        em.persist(order);
    }

    @Transactional
    public OrderMasterPojo get(int id){
        TypedQuery<OrderMasterPojo> query = getQuery(select, OrderMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    @Transactional
    public List<OrderMasterPojo> getAll(){
        TypedQuery<OrderMasterPojo> query = getQuery(select_All, OrderMasterPojo.class);
        return query.getResultList();
    }
    @Transactional
    public void update(OrderMasterPojo p, OrderMasterPojo ex){

    }
//    @Query(value = "SELECT * FROM PAYMENT_MASTER WHERE LAST_UPDATED >= :startDate AND LAST_UPDATED <= :endDate", nativeQuery = true)
//    public List<OrderMasterPojo> getByDateFilter(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Transactional(readOnly = true)
    public List<OrderMasterPojo> getByDateFilter(ZonedDateTime start, ZonedDateTime end){
        TypedQuery<OrderMasterPojo> query=em().createQuery(select_between, OrderMasterPojo.class);
        query.setParameter("start", start);
        query.setParameter("end",end);
        return query.getResultList();
    }
}
