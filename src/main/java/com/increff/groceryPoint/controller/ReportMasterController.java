package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.ReportMasterdto;
import com.increff.groceryPoint.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportMasterController {
        @Autowired
        private ReportMasterdto reportDto;

        @ApiOperation(value = "Get Sales report")
        @RequestMapping(path = "/api/sales-report", method = RequestMethod.POST)
        public List<SalesReportData> getSalesReport(@RequestBody DateFilterForm form) throws ApiException{
                return reportDto.getSalesReport(form);
        }
        
        @ApiOperation(value = "Get Revenue on product items with date as filter")
        @RequestMapping(path = "/api/sales-report-brand", method = RequestMethod.POST)
        public List<BrandSalesReport> getRevenueBrand(@RequestBody DateFilterForm form) throws ApiException
        {
                return reportDto.getBrandReport(form);
        }

        @ApiOperation(value = "Get Revenue on product items with date as filter")
        @RequestMapping(path = "/api/sales-report-category", method = RequestMethod.POST)
        public List<CategorySalesReport> getRevenueCategory(@RequestBody DateFilterForm form) throws ApiException
        {
                return reportDto.getCategoryReport(form);
        }
//
//        @ApiOperation(value = "Get Inventory Report")
//        @RequestMapping(path = "/api/inventory-report", method = RequestMethod.GET)
//        public List<InventoryReportModel> getInventoryReport() throws ApiException
//        {
//                return dto.getInventoryReport();
//        }


}
