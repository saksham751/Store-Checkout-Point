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
        public List<SalesReportData> getSalesReport(@RequestBody SalesReportForm form) throws ApiException{
                System.out.println(form.getStart()+" "+form.getEnd());
                return reportDto.getSalesReport(form);
        }
        @ApiOperation(value = "Get inventory Report")
        @RequestMapping(path = "/api/inventory-report", method = RequestMethod.GET)
        public List<InventoryReportForm> getInventoryReport() throws ApiException{
                return reportDto.getInventoryReport();
        }
        @ApiOperation(value = "Gets Brand Report")
        @RequestMapping(path = "/api/brand-report",method =RequestMethod.GET)
        public List<BrandMasterData> getBrandReport() throws ApiException{
                return reportDto.getBrandReport();
        }

        @ApiOperation(value="Gets pos_day_sales")
        @RequestMapping(path="/api/pos_day_sales_report",method = RequestMethod.GET)
        public List<pos_day_sales_Data> getpos_day_sales() throws ApiException{
                return reportDto.getpos_day_sales();
        }

}
