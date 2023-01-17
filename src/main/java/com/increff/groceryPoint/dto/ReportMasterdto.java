package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.groceryPoint.dto.HelperReport.convertToReport;


@Service
public class ReportMasterdto {
    @Autowired
    OrderItemMasterApi orderItemApi;
    @Autowired
    OrderMasterApi orderApi;
    @Autowired
    ProductMasterApi productApi;
    @Autowired
    BrandMasterApi brandApi;
    @Autowired
    InventoryMasterApi inventoryApi;

    public List<SalesReportData> getSalesReport(ReportForm form) throws ApiException{

        List<OrderMasterPojo> orderList=orderApi.getByDateFilter(form.getStart(),form.getEnd());
//        for(OrderMasterPojo p:orderList){
//            System.out.println(p.getId());
//        }

        List<SalesReportData> salesData=new ArrayList<SalesReportData>();
        return salesData;
    }
//    public List<ProductRevenueData> getProductWiseReport(DateFilterForm form) throws ApiException
//    {
//        List<ProductRevenueData> list1 = new ArrayList<ProductRevenueData>();
//
//        List<ProductPojo> productPojoList = productApi.getAllProducts();
//
////        key: productId
//        HashMap<Integer, ProductRevenueData> map = new HashMap<>();
//
////        getting the list of all available products in map
//        for(ProductPojo p: productPojoList) {
//            ProductRevenueData productRevenueData = convert(p);
//            map.put(p.getId(), productRevenueData);
//        }
////      converting the date into required formate
//        String startDate = correctFormat(form.getStart()) + " 00:00:00";
//        String endDate = correctFormat(form.getEnd()) + " 23:59:59";
//
//        List<OrderPojo> orderPojoList = orderService.selectOrderByDateFilter(startDate, endDate);
//
////        setting the quantity and total
////          -> order wise filtered on basis of date
//        for(OrderPojo e: orderPojoList)
//        {
//            int orderId = e.getId();
//            List<OrderItemPojo> orderItemPojoList = orderItemService.getOrder(orderId);
//
//            for(OrderItemPojo p: orderItemPojoList)
//            {
//                int productId = p.getProductId();
//                int quantity = map.get(productId).getQuantity();
//                double total = map.get(productId).getTotal();
//
//                map.get(productId).setQuantity(quantity + p.getQuantity());
//                map.get(productId).setTotal(total + p.getQuantity()*p.getSellingPrice());
//            }
//        }
//
////        Converting map to list
//        for (Map.Entry<Integer, ProductRevenueData> e: map.entrySet())
//            list1.add(e.getValue());
//
//        return list1;
//    }
//
//    public List<BrandReportModel> getBrandReport(DateFilterForm form) throws ApiException {
//        List<BrandReportModel> res = new ArrayList<>();
//
//        HashMap<String, BrandReportModel> map = new HashMap<>();
//
//        List<ProductReportModel> list1 = getProductWiseReport(form);
//
//        for(ProductRevenueData p: list1)
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
//                BrandRevenueData b = new BrandRevenueData();
//                b.setBrand(p.getBrand());
//                b.setTotal(p.getTotal());
//                b.setQuantity(p.getQuantity());
//
//                map.put(p.getBrand(), b);
//            }
//        }
//
//        for(Map.Entry<String, BrandRevenueData> e: map.entrySet())
//            res.add(e.getValue());
//
//        return res;
//    }
//
//
//    String correctFormat(String date)
//    {
//        String res = date.replace('-', '/');
//        return res;
//    }
//
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