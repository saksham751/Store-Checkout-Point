package com.increff.groceryPoint.dto.Helper;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.DaySalesPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import com.increff.groceryPoint.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
@Service
public class ReportHelper {
    @Autowired
    private BrandMasterApi brandApi;
    public static Date getStart(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,00);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
    public static Date getEnd(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date getEndOfDay(Date day,Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }
    public static void validateReportForm(SalesReportForm salesReportForm) {

        if(salesReportForm.getEnd()==null) {
            salesReportForm.setEnd(new Date());
        }
        if(salesReportForm.getStart()==null) {
            salesReportForm.setStart(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        }
        salesReportForm.setBrand(StringUtil.toLowerCase(salesReportForm.getBrand().trim().toLowerCase()));
        salesReportForm.setCategory(StringUtil.toLowerCase(salesReportForm.getCategory().trim().toLowerCase()));
        salesReportForm.setStart(getStartOfDay(salesReportForm.getStart(),Calendar.getInstance()));
        salesReportForm.setEnd(getEndOfDay(salesReportForm.getEnd(),Calendar.getInstance()));
    }
    public static void validateFilterForm(posDaySalesForm daySalesForm){
        if(daySalesForm.getEnd()==null) {
            daySalesForm.setEnd(new Date());
        }
        if(daySalesForm.getStart()==null) {
            daySalesForm.setStart(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        }
    }
    public SalesReportData convertProductToReport(ProductMasterPojo p) throws ApiException
    {
        SalesReportData salesReport = new SalesReportData();
        salesReport.setId(p.getId());
        salesReport.setBarcode(p.getBarcode());
        salesReport.setMrp(p.getMrp());
        salesReport.setName(p.getName());
        salesReport.setQuantity(0);
        salesReport.setTotal(0);
        int brandCategoryId = p.getBrand_category();
        BrandMasterPojo brandPojo = brandApi.get(brandCategoryId);
        salesReport.setBrand(brandPojo.getBrand());
        salesReport.setCategory(brandPojo.getCategory());
        return salesReport;
    }

    public static posDaySalesData convertDaySalesPojotoData(DaySalesPojo salesPojo){
        posDaySalesData daySales = new posDaySalesData();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        daySales.setDate(formatter.format(salesPojo.getDate()));
        daySales.setTotalRevenue(salesPojo.getTotalRevenue());
        daySales.setInvoicedItemsCount(salesPojo.getInvoicedItemsCount());
        daySales.setInvoicedOrderCount(salesPojo.getInvoicedOrderCount());
        return daySales;
    }

}
