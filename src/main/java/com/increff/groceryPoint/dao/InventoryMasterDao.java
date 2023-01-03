package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryMasterDao extends AbstractDao{

    private static String select_all = "select I from InventoryMasterPojo I";
    private static String select = "select I from InventoryMasterPojo I where id =:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = ApiException.class)
    public void insertInventorytDao(InventoryMasterPojo I) {
        em.persist(I);
    }

    @Transactional
    public InventoryMasterPojo selectInventoryDao(int id){
        TypedQuery<InventoryMasterPojo> query = getQuery(select, InventoryMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional
    public List<InventoryMasterPojo> selectAllInventoryDao(){
        TypedQuery<InventoryMasterPojo> query = getQuery(select_all, InventoryMasterPojo.class);
        return query.getResultList();
    }

    @Transactional
    public void updateInventoryDao(InventoryMasterPojo p,InventoryMasterPojo ex) {
        ex.setQuantity(p.getQuantity());
    }

}
