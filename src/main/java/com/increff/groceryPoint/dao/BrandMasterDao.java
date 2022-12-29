package com.increff.groceryPoint.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.groceryPoint.pojo.BrandMasterPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BrandMasterDao extends AbstractDao {

    private static String delete_id = "delete from BrandMasterPojo p where id=:id";
    private static String select_id = "select p from BrandMasterPojo p where id=:id";
    private static String select_all = "select p from BrandMasterPojo p";
    private static String select_brand_category = "select p from BrandMasterPojo p where brand=:brand and category=:category";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insertBrandDao(BrandMasterPojo p) {
        em.persist(p);
    }
    @Transactional
    public int deleteBrandDao(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional
    public BrandMasterPojo selectBrandDao(int id) {
        TypedQuery<BrandMasterPojo> query = getQuery(select_id, BrandMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional
    public List<BrandMasterPojo> selectAllBrandDao() {
        TypedQuery<BrandMasterPojo> query = getQuery(select_all, BrandMasterPojo.class);
        return query.getResultList();
    }
    @Transactional
    public void updateBrandDao(BrandMasterPojo p) {

    }
    @Transactional
    public BrandMasterPojo checkUnique(String brand, String Category){
        TypedQuery<BrandMasterPojo> query = getQuery(select_brand_category, BrandMasterPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", Category);
        return getSingle(query);
    }

}
