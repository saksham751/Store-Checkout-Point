package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.model.DaySalesData;
import com.increff.groceryPoint.model.SalesReportForm;
import com.increff.groceryPoint.model.SalesReportData;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.DaySalesPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import com.increff.groceryPoint.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
@Service
public class HelperReport {
    @Autowired
    private BrandMasterApi brandApi;
//    public static InventoryReportForm convertToReport(BrandMasterPojo p)
//    {
//        InventoryReportForm inventoryReportModel = new InventoryReportForm();
//
//        inventoryReportModel.setBrand(p.getBrand());
//        inventoryReportModel.setCategory(p.getCategory());
//        inventoryReportModel.setCategory(p.getId());
//        inventoryReportModel.setQuantity(0);
//
//        return inventoryReportModel;
//    }
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
        salesReportForm.setBrand(StringUtil.toLowerCase(salesReportForm.getBrand()));
        salesReportForm.setCategory(StringUtil.toLowerCase(salesReportForm.getCategory()));
        salesReportForm.setStart(getStartOfDay(salesReportForm.getStart(),Calendar.getInstance()));
        salesReportForm.setEnd(getEndOfDay(salesReportForm.getEnd(),Calendar.getInstance()));
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
    protected static DaySalesData convertPojotoData(DaySalesPojo pojo)
    {
        DaySalesData dailyReportData = new DaySalesData();
        dailyReportData.setDate(pojo.getDate());
        dailyReportData.setTotalRevenue(pojo.getTotalRevenue());
        dailyReportData.setInvoicedItemsCount(pojo.getInvoicedItemsCount());
        dailyReportData.setInvoicedOrderCount(pojo.getInvoicedOrderCount());
        return dailyReportData;
    }
//    public static ReportForm convertToDatatime(DateFilterForm form){
////        String st=form.getStart().replace('-','/');
//        ReportForm reportfilter = new ReportForm();
////        System.out.println(form.getStart());
//        String startDate = form.getStart()+ " 00:00:00";
////        System.out.println(startDate+ " "+ Date.now());
//
//        Date strt = Date.parse(startDate, formatter);
//        reportfilter.setStart(strt);
//        String endDate = form.getEnd()+ " 23:59:59";
//        Date end = Date.parse(endDate, formatter);
//        reportfilter.setStart(end);
//        return reportfilter;
//    }

}
