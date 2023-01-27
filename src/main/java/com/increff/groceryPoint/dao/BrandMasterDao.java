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
public class BrandMasterDao extends AbstractDao {

    private static String delete_id = "delete from BrandMasterPojo p where id=:id";
    private static String select_id = "select p from BrandMasterPojo p where id=:id";
    private static String select_all = "select p from BrandMasterPojo p";
    private static String select_brand_category = "select p from BrandMasterPojo p where brand=:brand and category=:category";
    private static String select_brand = "select b from BrandMasterPojo b where b.brand =: brand";
    private static String select_category = "select b from BrandMasterPojo b where b.category =: category";
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackFor = ApiException.class)
    public int insertBrandDao(BrandMasterPojo p) {
        em.persist(p);
        return p.getId();
    }
    @Transactional(rollbackFor = ApiException.class)
    public int deleteBrandDao(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional(readOnly = true)
    public BrandMasterPojo selectBrandDao(int id) {
        TypedQuery<BrandMasterPojo> query = getQuery(select_id, BrandMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional(readOnly = true)
    public List<BrandMasterPojo> selectAllBrandDao() {
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
