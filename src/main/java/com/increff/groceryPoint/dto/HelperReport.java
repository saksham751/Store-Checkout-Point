package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.model.InventoryReportModel;
import com.increff.groceryPoint.model.ReportForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HelperReport {
    protected static InventoryReportModel convertToReport(BrandMasterPojo p)
    {
        InventoryReportModel inventoryReportModel = new InventoryReportModel();

        inventoryReportModel.setBrand(p.getBrand());
        inventoryReportModel.setCategory(p.getCategory());
        inventoryReportModel.setBrandCategory(p.getId());
        inventoryReportModel.setQuantity(0);

        return inventoryReportModel;
    }



}
