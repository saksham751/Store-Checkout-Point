package com.increff.groceryPoint.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductMasterDao extends AbstractDao {

    private static String delete_id = "delete from ProductPojo p where id=:id";
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insertProductDao(ProductMasterPojo p) {
        em.persist(p);
    }

    public int deleteProductDao(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public ProductMasterPojo selectProductDao(int id) {
        TypedQuery<ProductMasterPojo> query = getQuery(select_id, ProductMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductMasterPojo> selectAllProductDao() {
        TypedQuery<ProductMasterPojo> query = getQuery(select_all, ProductMasterPojo.class);
        return query.getResultList();
    }

    public void updateProductDao(ProductMasterPojo p) {
    }



}
