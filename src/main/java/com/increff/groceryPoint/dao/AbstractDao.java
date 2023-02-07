package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.AbstractPojo;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class AbstractDao<ID> {

    @PersistenceContext
    private EntityManager em;

    protected <T> T getSingle(TypedQuery<T> query) {
        return query.getResultList().stream().findFirst().orElse(null);
    }

    protected <T> TypedQuery<T> getQuery(String jpql, Class<T> clazz) {
        return em.createQuery(jpql, clazz);
    }

    protected EntityManager em() {
        return em;
    }

    @Transactional(rollbackFor = ApiException.class)
    public <T extends AbstractPojo<ID>> ID add(T p) {
        em.persist(p);
        return p.getId();
    }

	@Transactional(rollbackFor = ApiException.class)
	public <T extends AbstractPojo<?>> void delete(T p) {
		em.remove(p);
	}


}
