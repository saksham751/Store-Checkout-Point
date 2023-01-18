package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.model.DateFilterForm;
import com.increff.groceryPoint.model.InventoryReportModel;
import com.increff.groceryPoint.model.ReportForm;
import com.increff.groceryPoint.model.SalesReportData;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HelperReport {
    @Autowired
    private BrandMasterApi brandApi;
    public static InventoryReportModel convertToReport(BrandMasterPojo p)
    {
        InventoryReportModel inventoryReportModel = new InventoryReportModel();

        inventoryReportModel.setBrand(p.getBrand());
        inventoryReportModel.setCategory(p.getCategory());
        inventoryReportModel.setBrandCategory(p.getId());
        inventoryReportModel.setQuantity(0);

        return inventoryReportModel;
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

    public static ReportForm convertToDatatime(DateFilterForm form){
//        String st=form.getStart().replace('-','/');
        ReportForm reportfilter = new ReportForm();
//        System.out.println(form.getStart());
        String startDate = form.getStart()+ " 00:00:00 +05:30";
//        System.out.println(startDate+ " "+ ZonedDateTime.now());
        final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZZZZ");
        ZonedDateTime strt = ZonedDateTime.parse(startDate, formatter);
        reportfilter.setStart(strt);
        String endDate = form.getEnd()+ " 23:59:59 +05:30";
        ZonedDateTime end = ZonedDateTime.parse(endDate, formatter);
        reportfilter.setStart(end);
        return reportfilter;
    }

}
