package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private HelperReport help;
    public List<SalesReportData> getSalesReport(SalesReportForm form) throws ApiException{
        validate(form);
        Date startDate = form.getStart();
        Date endDate = form.getEnd();
        String brand = form.getBrand();
        String category = form.getCategory();

        List<OrderMasterPojo> orderList = orderApi.getByDateFilter(startDate,endDate);
        List<OrderItemMasterPojo> orderItemList = new ArrayList<OrderItemMasterPojo>();
        for(OrderMasterPojo orderPojo:orderList){
            List<OrderItemMasterPojo> orderItemPojoList = orderItemApi.getAllfromOrderId(orderPojo.getId());
            orderItemList.addAll(orderItemPojoList);
        }

        List<BrandMasterPojo> brandList = new ArrayList<BrandMasterPojo>();
        if(brand.equals("")&&category.equals("")){
            brandList = brandApi.getAll();
        }
        else if(brand.equals("")){
            brandList = brandApi.getByCategory(category);
        }
        else if(category.equals("")){
            brandList = brandApi.getByBrand(brand);
        }
        else{
            BrandMasterPojo brandCategoryPojo = brandApi.getByBrandCategory(brand,category);
            brandList.add(brandCategoryPojo);
        }
        List<SalesReportData> salesReportDataList = getSalesReportData(brandList,orderItemList);
        return salesReportDataList;
    }
    private List<SalesReportData> getSalesReportData(List<BrandMasterPojo> brandCategoryPojos,
                                                     List<OrderItemMasterPojo> orderItemPojos) throws ApiException {
        List<SalesReportData> salesReportDataList = new ArrayList<SalesReportData>();
        for(BrandMasterPojo brandCategoryPojo:brandCategoryPojos){
            SalesReportData salesReportData = new SalesReportData();
            salesReportData.setCategory(brandCategoryPojo.getCategory());
            salesReportData.setBrand(brandCategoryPojo.getBrand());
            Integer quantity = 0;
            Double revenue = 0.0;
            for(OrderItemMasterPojo orderItemPojo:orderItemPojos){
                ProductMasterPojo productPojo = productApi.get(orderItemPojo.getProductId());
                if(productPojo.getBrand_category()==brandCategoryPojo.getId()){
                    quantity += orderItemPojo.getQuantity();
                    revenue += (orderItemPojo.getQuantity())*(orderItemPojo.getSellingPrice());
                }
            }
            salesReportData.setQuantity(quantity);
            salesReportData.setTotal(revenue);
            salesReportDataList.add(salesReportData);
        }
        return salesReportDataList;
    }

    public List<InventoryReportForm> getInventoryReport() throws ApiException {

        Map<Integer,InventoryReportForm> brandIdToInventoryReportFormMap = new HashMap<Integer,InventoryReportForm>();
        List<ProductMasterPojo> productPojos = productApi.getAll();
        for(ProductMasterPojo productPojo:productPojos){
            InventoryMasterPojo inventoryPojo = invApi.get(productPojo.getId());
            Integer brandId = productPojo.getBrand_category();
            BrandMasterPojo brandCategoryPojo = brandApi.get(brandId);
            if(brandIdToInventoryReportFormMap.containsKey(brandId)==false){
                InventoryReportForm inventoryReportData = new InventoryReportForm();
                inventoryReportData.setBrand(brandCategoryPojo.getBrand());
                inventoryReportData.setCategory(brandCategoryPojo.getCategory());
                inventoryReportData.setQuantity(inventoryPojo.getQuantity());
                brandIdToInventoryReportFormMap.put(brandId,inventoryReportData);
            }
            else
            {
                InventoryReportForm inventoryReportData = brandIdToInventoryReportFormMap.get(brandId);
                Integer quant = inventoryReportData.getQuantity();
                quant+=inventoryPojo.getQuantity();
                inventoryReportData.setQuantity(quant);
                brandIdToInventoryReportFormMap.put(brandId,inventoryReportData);

            }
        }

        List<InventoryReportForm> inventoryReportDataList = new ArrayList<InventoryReportForm>();
        for(Map.Entry m:brandIdToInventoryReportFormMap.entrySet()){
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