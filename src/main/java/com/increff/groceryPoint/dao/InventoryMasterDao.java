package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionScoped;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class InventoryMasterDao extends AbstractDao<Integer>{
    //todo use completenames like Inventory for I
    private static String select_all = "select Inventory from InventoryMasterPojo Inventory";
    private static String select = "select Inventory from InventoryMasterPojo Inventory where id =:id";
    private static String delete = "delete from InventoryMasterPojo Inventory where id=:id";
    @PersistenceContext
    private EntityManager em;

//    @Transactional(rollbackFor = ApiException.class)
//    public Integer delete(int id) {
//        Query query = em.createQuery(delete);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }
    @Transactional(readOnly = true)
    public InventoryMasterPojo get(int id){
        TypedQuery<InventoryMasterPojo> query = getQuery(select, InventoryMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional(readOnly = true)
    public List<InventoryMasterPojo> getAll(){
        TypedQuery<InventoryMasterPojo> query = getQuery(select_all, InventoryMasterPojo.class);
        return query.getResultList();
    }

}
