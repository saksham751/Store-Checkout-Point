package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.dto.Helper.HelperReport;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.increff.groceryPoint.dto.Helper.HelperBrand.convert;

import static com.increff.groceryPoint.dto.Helper.HelperReport.*;


@Service
public class ReportMasterdto {
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private HelperReport helper;
    @Autowired
    private ReportMasterApi reportApi;
    public List<SalesReportData> getSalesReport(SalesReportForm form) throws ApiException{
        validateReportForm(form);
        List<OrderMasterPojo> orderList = orderApi.getByDateFilter(form.getStart(),form.getEnd());
        List<OrderItemMasterPojo> orderItemList = new ArrayList<OrderItemMasterPojo>();
        for(OrderMasterPojo orderPojo:orderList){
            List<OrderItemMasterPojo> orderItemPojoList = orderItemApi.getAllfromOrderId(orderPojo.getId());
            orderItemList.addAll(orderItemPojoList);
        }

        List<BrandMasterPojo> brandList=reportApi.getBrandList(form.getBrand(),form.getCategory());
        List<SalesReportData> salesReportDataList = reportApi.getSalesReportData(brandList,orderItemList);
        return salesReportDataList;
    }

    public List<InventoryReportForm> getInventoryReport() throws ApiException {
        List<ProductMasterPojo> productList = productApi.getAll();
        Map<Integer,InventoryReportForm> invMap = new HashMap<Integer,InventoryReportForm>();
        for(ProductMasterPojo productPojo:productList){
            InventoryMasterPojo inventoryPojo = invApi.get(productPojo.getId());
            BrandMasterPojo brandPojo = brandApi.get( productPojo.getBrand_category());
            InventoryReportForm invReportData = new InventoryReportForm();
            if(invMap.containsKey( productPojo.getBrand_category())){
                invReportData = invMap.get( productPojo.getBrand_category());
                Integer qty = invReportData.getQuantity();
                qty+=inventoryPojo.getQuantity();
                invReportData.setQuantity(qty);
            }else {
                invReportData.setBrand(brandPojo.getBrand());
                invReportData.setCategory(brandPojo.getCategory());
                invReportData.setQuantity(inventoryPojo.getQuantity());
            }
            invMap.put( productPojo.getBrand_category(),invReportData);
        }
        List<InventoryReportForm> inventoryReportDataList = new ArrayList<InventoryReportForm>();
        for(Map.Entry m:invMap.entrySet()){
            inventoryReportDataList.add((InventoryReportForm) m.getValue());
        }
        return inventoryReportDataList;
    }

    public List<BrandMasterData> getBrandReport() throws ApiException{
        List<BrandMasterPojo> brandPojoList= brandApi.getAll();
        List<BrandMasterData> brandDataList= new ArrayList<BrandMasterData>();
        for(BrandMasterPojo b: brandPojoList){
            brandDataList.add(convert(b));
        }
        return brandDataList;
    }

    public void generate_pos_day_sales() throws ApiException{
        DaySalesPojo salesreport= new DaySalesPojo();
        Date start = helper.getStart();
        Date end =helper.getEnd();
        Integer items = 0;
        Double revenue = 0.0;
        List<OrderMasterPojo> orderPojoList = orderApi.getByDateFilter(start, end);
        Integer totalOrders = orderPojoList.size();
        for (OrderMasterPojo o : orderPojoList) {
            Integer id = o.getId();
            List<OrderItemMasterPojo> orderItemPojoList = orderItemApi.getAllfromOrderId(id);
            for (OrderItemMasterPojo i : orderItemPojoList) {
                items += i.getQuantity();
                revenue += i.getQuantity() * i.getSellingPrice();
            }
        }
        long millis=System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        salesreport.setDate(date);
        salesreport.setTotalRevenue(revenue);
        salesreport.setInvoicedItemsCount(items);
        salesreport.setInvoicedOrderCount(totalOrders);

        DaySalesPojo pojo = reportApi.getReportByDate(date);
        if (pojo == null) {
            reportApi.addReport(salesreport);
        } else {
            reportApi.update(date, salesreport);
        }
    }
    public List<pos_day_sales_Data> getpos_day_sales(pos_day_sales_Form posFilterForm) throws ApiException{
        validateFilterForm(posFilterForm);
        List<DaySalesPojo> daySales = reportApi.getpos_day_sales(posFilterForm.getStart(),posFilterForm.getEnd());
        List<pos_day_sales_Data> posData = new ArrayList<pos_day_sales_Data>();
        for(DaySalesPojo it:daySales){
            posData.add(helper.convertDaySalesPojotoData(it));
        }
        return posData;
    }
}