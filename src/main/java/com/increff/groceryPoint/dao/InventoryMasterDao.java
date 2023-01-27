package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class InventoryMasterDao extends AbstractDao{

    private static String select_all = "select I from InventoryMasterPojo I";
    private static String select = "select I from InventoryMasterPojo I where id =:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = ApiException.class)
    public void add(InventoryMasterPojo I) {
        em.persist(I);
    }

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


    public void update(InventoryMasterPojo p, InventoryMasterPojo ex) {

    }

}
