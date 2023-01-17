package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.ReportMasterdto;
import com.increff.groceryPoint.model.ReportForm;
import com.increff.groceryPoint.model.SalesReportData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.increff.groceryPoint.model.InventoryReportModel;
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
        @RequestMapping(path = "/api/sales-report", method = RequestMethod.GET)
        public List<SalesReportData> getSalesReport(@RequestBody ReportForm form) throws ApiException{
                return reportDto.getSalesReport(form);
        }

//        @ApiOperation(value = "Get Revenue on product items with date as filter")
//        @RequestMapping(path = "/api/revenue-product", method = RequestMethod.POST)
//        public List<ProductRevenueData> getProductWiseReport(@RequestBody DateFilterForm form) throws ApiException
//        {
//            return dto.getProductWiseReport(form);
//        }
//
//        @ApiOperation(value = "Get Revenue on product items with date as filter")
//        @RequestMapping(path = "/api/revenue-brand", method = RequestMethod.POST)
//        public List<BrandRevenueData> getRevenueBrand(@RequestBody DateFilterForm form) throws ApiException
//        {
//            return dto.getBrandReport(form);
//        }
//
//        @ApiOperation(value = "Get Revenue on product items with date as filter")
//        @RequestMapping(path = "/api/revenue-category", method = RequestMethod.POST)
//        public List<CategoryRevenueData> getRevenueCategory(@RequestBody DateFilterForm form) throws ApiException
//        {
//            return dto.getCategoryReport(form);
//        }

//        @ApiOperation(value = "Get Inventory Report")
//        @RequestMapping(path = "/api/inventory-report", method = RequestMethod.GET)
//        public List<InventoryReportModel> getInventoryReport() throws ApiException
//        {
//            return reportDto.getInventoryReport();
//        }


}
