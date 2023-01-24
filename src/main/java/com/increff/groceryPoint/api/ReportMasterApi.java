package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.ReportMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.DaySalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReportMasterApi {
    @Autowired
    private ReportMasterDao reportDao;

    @Transactional
    public void addReport(DaySalesPojo pojo) throws ApiException {
        try {
            reportDao.insert(pojo);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public List<DaySalesPojo> getAllReport() throws ApiException {
        try {
            return reportDao.selectAll();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public DaySalesPojo getReportByDate(Date date) throws ApiException {
        return reportDao.select(date);
    }

    @Transactional
    public void update(Date date, DaySalesPojo newPojo)
    {
        DaySalesPojo pojo = reportDao.select(date);
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
    }
}
