package com.increff.groceryPoint.dao;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.DaySalesPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class ReportMasterDao extends AbstractDao{
    private static String SELECT_BY_ID = "select p from DaySalesPojo p where date=:date";

    private static String SELECT_ALL = "select p from DaySalesPojo p";

    private static String select_between="select daysales from DaySalesPojo daysales where date between :start and :end";

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

    @Transactional(readOnly = true)
    public List<DaySalesPojo> getByDateFilter(Date start, Date end){
        TypedQuery<DaySalesPojo> query= em().createQuery(select_between, DaySalesPojo.class);
        query.setParameter("start", start);
        query.setParameter("end",end);
        return query.getResultList();
    }
}
