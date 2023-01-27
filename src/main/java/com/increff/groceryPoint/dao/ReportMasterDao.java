package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.DaySalesPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class ReportMasterDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from DaySalesPojo p where date=:date";

    private static String SELECT_ALL = "select p from DaySalesPojo p";

    @Transactional(rollbackFor = ApiException.class)
    public void insert(DaySalesPojo p) {
        em().persist(p);
    }
    @Transactional(readOnly = true)
    public DaySalesPojo select(Date date) {
        TypedQuery<DaySalesPojo> query = getQuery(SELECT_BY_ID, DaySalesPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }
    @Transactional(readOnly = true)
    public List<DaySalesPojo> selectAll() {
        TypedQuery<DaySalesPojo> query = getQuery(SELECT_ALL, DaySalesPojo.class);
        return query.getResultList();
    }
}
