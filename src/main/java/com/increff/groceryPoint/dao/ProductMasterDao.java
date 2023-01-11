package com.increff.groceryPoint.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductMasterDao extends AbstractDao {

    private static String delete_id = "delete from ProductMasterPojo p where id=:id";
    private static String select_id = "select p from ProductMasterPojo p where id=:id";
    private static String select_all = "select p from ProductMasterPojo p";
    private static String select_barcode = "select p from ProductMasterPojo p where barcode=:barcode";

    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = ApiException.class)
    public void insertProductDao(ProductMasterPojo p) {
        em.persist(p);
    }
    @Transactional
    public int deleteProductDao(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional
    public ProductMasterPojo selectProductDao(int id) {
        TypedQuery<ProductMasterPojo> query = getQuery(select_id, ProductMasterPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional
    public List<ProductMasterPojo> selectAllProductDao() {
        TypedQuery<ProductMasterPojo> query = getQuery(select_all, ProductMasterPojo.class);
        return query.getResultList();
    }
    @Transactional
    public void updateProductDao(ProductMasterPojo ex) {

    }

    @Transactional
    public ProductMasterPojo getProductfromBarcode(String barcode){
        TypedQuery<ProductMasterPojo> query = getQuery(select_barcode, ProductMasterPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

}
