package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.pojo.DaySalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class ReportMasterDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from DaySalesPojo p where date=:date";

    private static String SELECT_ALL = "select p from DaySalesPojo p";

    public void insert(DaySalesPojo p) {
        em().persist(p);
    }
    public DaySalesPojo select(Date date) {
        TypedQuery<DaySalesPojo> query = getQuery(SELECT_BY_ID, DaySalesPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }
    public List<DaySalesPojo> selectAll() {
        TypedQuery<DaySalesPojo> query = getQuery(SELECT_ALL, DaySalesPojo.class);
        return query.getResultList();
    }
}
