package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.increff.groceryPoint.dto.HelperBrand.convert;
import static com.increff.groceryPoint.dto.HelperReport.*;


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
    public List<DaySalesData> getAllDailyReport() throws ApiException
    {
        List<DaySalesPojo> dailyReportPojoList = reportApi.getAllReport();
        List<DaySalesData> dailyReportData = new ArrayList<>();

        for(DaySalesPojo p: dailyReportPojoList)
        {
            dailyReportData.add(helper.convertPojotoData(p));
        }

        return dailyReportData;
    }
//    @Scheduled(cron = "0 49 15 * * ?")
//    public void generateDailySalesReport() throws ApiException {
//        DaySalesPojo daySalesPojo = new DaySalesPojo();
//        daySalesPojo.setDate(new Date());
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE,-1);
//        Date yesterday = TimeUtil.getStartOfDay(calendar.getTime(),calendar);
////        System.out.println("From: " + yesterday.toString() + "\nTo: " + new Date().toString());
//
//        List<OrderPojo> orderPojoList = orderService.getByStartDateEndDate(yesterday, new Date());
//        List<OrderItemPojo> orderItemPojoList = new ArrayList<OrderItemPojo>();
//        for(OrderPojo orderPojo : orderPojoList) {
//            List<OrderItemPojo> orderItemPojo = orderItemService.getByOrderId(orderPojo.getId());
//            orderItemPojoList.addAll(orderItemPojo);
//        }
//
//        Double totalRevenue = 0d;
//        for (OrderItemPojo orderItem : orderItemPojoList) {
//            totalRevenue += orderItem.getSellingPrice() * orderItem.getQuantity();
//        }
//
//        daySalesPojo.setTotal_revenue(totalRevenue);
//        daySalesPojo.setInvoiced_orders_count(orderPojoList.size());
//        daySalesPojo.setInvoiced_items_count(orderItemPojoList.size());
//
//        reportService.add(daySalesPojo);
//
//    }
//
//    public List<DaySalesData> getDaySalesReport(){
//        return convert(reportService.getDaySales());
//    }
//
//    public List<BrandSalesReport> getBrandReport(DateFilterForm form) throws ApiException {
//        List<BrandSalesReport> res = new ArrayList<>();
//
//        HashMap<String, BrandSalesReport> map = new HashMap<>();
//
//        List<SalesReportData> list1 = getSalesReport(form);
//
//        for(SalesReportData p: list1)
//        {
//            if(map.containsKey(p.getBrand()))
//            {
//                int quantity = map.get(p.getBrand()).getQuantity();
//                double total = map.get(p.getBrand()).getTotal();
//
//                map.get(p.getBrand()).setQuantity(quantity + p.getQuantity());
//                map.get(p.getBrand()).setTotal(total + p.getTotal());
//            }
//            else{
//                BrandSalesReport b = new BrandSalesReport();
//                b.setBrand(p.getBrand());
//                b.setTotal(p.getTotal());
//                b.setQuantity(p.getQuantity());
//
//                map.put(p.getBrand(), b);
//            }
//        }
//
//        for(Map.Entry<String, BrandSalesReport> e: map.entrySet())
//            res.add(e.getValue());
//
//        return res;
//    }
//
//    public List<CategorySalesReport> getCategoryReport(DateFilterForm form) throws ApiException {
//        List<CategorySalesReport> res = new ArrayList<>();
//
//        HashMap<String, CategorySalesReport> map = new HashMap<>();
//
//        List<SalesReportData> list1 = getSalesReport(form);
//
//        for(SalesReportData p: list1)
//        {
//            if(map.containsKey(p.getCategory()))
//            {
//                String key = p.getCategory();
//                int quantity = map.get(key).getQuantity();
//                double total = map.get(key).getTotal();
//
//                map.get(key).setQuantity(quantity + p.getQuantity());
//                map.get(key).setTotal(total + p.getTotal());
//            }
//            else{
//                CategorySalesReport b = new CategorySalesReport();
//                b.setCategory(p.getCategory());
//                b.setTotal(p.getTotal());
//                b.setQuantity(p.getQuantity());
//
//                map.put(p.getCategory(), b);
//            }
//        }
//
//        for(Map.Entry<String, CategorySalesReport> e: map.entrySet())
//            res.add(e.getValue());
//
//
//        return res;
//    }
//    String correctFormat(String date)
//    {
//        String res = date.replace('-', '/');
//        return res;
//    }

//    public List<InventoryReportModel> getInventoryReport() {
//        List<BrandMasterPojo> brandPojoList = brandApi.getAll();
//        HashMap<Integer, InventoryReportModel> map = new HashMap<>();
//
//        for(BrandMasterPojo b: brandPojoList)
//            map.put(b.getId(), convertToReport(b));
//
//        List<InventoryMasterPojo> inventoryPojoList = inventoryApi.getAll();
//
//        HashMap<Integer, Integer> inventoryMap = new HashMap<>();
//
//        for(InventoryMasterPojo p: inventoryPojoList)
//            inventoryMap.put(p.getId(), p.getQuantity());
//
//        List<ProductMasterPojo> productPojoList = productApi.getAll();
//
//        for(ProductMasterPojo p: productPojoList)
//        {
//            int quantity = map.get(p.getBrand_category()).getQuantity();
//            int nquantity = 0;
//            if(inventoryMap.containsKey(p.getId()))
//                nquantity = inventoryMap.get(p.getId());
//
//            map.get(p.getBrand_category()).setQuantity(quantity + nquantity);
//        }
//
//        List<InventoryReportModel> list = new ArrayList<>();
//
//        for(Map.Entry<Integer, InventoryReportModel> e: map.entrySet())
//            list.add(e.getValue());
//
//        return list;
//    }

}