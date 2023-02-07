package com.increff.groceryPoint.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BrandMasterDao extends AbstractDao<Integer> {

    private static String delete_id = "delete from BrandMasterPojo brandTable where id=:id";
    private static String select_id = "select brandTable from BrandMasterPojo brandTable where id=:id";
    private static String select_all = "select brandTable from BrandMasterPojo brandTable";
    private static String select_brand_category = "select brandTable from BrandMasterPojo brandTable where brand=:brand and category=:category";
    private static String select_brand = "select brandTable from BrandMasterPojo brandTable where brandTable.brand =: brand";
    private static String select_category = "select brandTable from BrandMasterPojo brandTable where brandTable.category =: category";
    @PersistenceContext
    private EntityManager em;
    //todo aadd common funtions to super class

//    @Transactional(rollbackFor = ApiException.class)
//    public int delete(int id) {
//        Query query = em.createQuery(delete_id);
//        query.setParameter("id", id);
//        return query.executeUpdate();
//    }
    @Transactional(readOnly = true)
    public BrandMasterPojo get(int id) {
        TypedQuery<BrandMasterPojo> query = getQuery(select_id, BrandMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional(readOnly = true)
    public List<BrandMasterPojo> getAll() {
        TypedQuery<BrandMasterPojo> query = getQuery(select_all, BrandMasterPojo.class);
        return query.getResultList();
    }
    public void updateBrandDao(BrandMasterPojo p) {

    }
    @Transactional(readOnly = true)
    public List<BrandMasterPojo> getByBrand(String brand){
        TypedQuery<BrandMasterPojo> query = getQuery(select_brand, BrandMasterPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }
    @Transactional(readOnly = true)
    public List<BrandMasterPojo> getByCategory(String category){
        TypedQuery<BrandMasterPojo> query = getQuery(select_category, BrandMasterPojo.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
    @Transactional(readOnly = true)
    public BrandMasterPojo getByBrandCategory(String brand, String category){
        TypedQuery<BrandMasterPojo> query = getQuery(select_brand_category,BrandMasterPojo.class);
        query.setParameter("brand",brand);
        query.setParameter("category",category);
        return getSingle(query);
    }
    @Transactional(readOnly = true)
    public BrandMasterPojo checkUnique(String brand, String Category){
        TypedQuery<BrandMasterPojo> query = getQuery(select_brand_category, BrandMasterPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", Category);
        return getSingle(query);
    }

}
