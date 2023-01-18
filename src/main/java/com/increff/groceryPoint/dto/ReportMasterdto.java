package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.*;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private InventoryMasterApi inventoryApi;
    @Autowired
    private HelperReport help;
    public List<SalesReportData> getSalesReport(DateFilterForm form) throws ApiException{
        ReportForm reportfilter = convertToDatatime(form);
        List<SalesReportData> salesData=new ArrayList<SalesReportData>();

        List<ProductMasterPojo> productPojoList = productApi.getAll();


        HashMap<Integer, SalesReportData> map = new HashMap<>();
        for(ProductMasterPojo p: productPojoList) {
            SalesReportData salesReport = help.convertProductToReport(p);
            map.put(p.getId(), salesReport);
        }
        List<OrderMasterPojo> orderList=orderApi.getByDateFilter(reportfilter.getStart(),reportfilter.getEnd());
        if(orderList.isEmpty()){
            System.out.println(";)");
        }

        for(OrderMasterPojo e: orderList)
        {
            int orderId = e.getId();
            List<OrderItemMasterPojo> orderItemPojoList = orderItemApi.getAllfromOrderId(orderId);
            System.out.println("bye");
            for(OrderItemMasterPojo p: orderItemPojoList)
            {System.out.println("hello1");

                int productId = p.getProductId();
                int quantity = map.get(productId).getQuantity();
                double total = map.get(productId).getTotal();

                map.get(productId).setQuantity(quantity + p.getQuantity());
                map.get(productId).setTotal(total + p.getQuantity()*p.getSellingPrice());
                System.out.println(map.get(productId).getTotal());
                System.out.println("hello");
            }
        }
        for (Map.Entry<Integer, SalesReportData> e: map.entrySet())
            salesData.add(e.getValue());

        return salesData;
    }

    public List<BrandSalesReport> getBrandReport(DateFilterForm form) throws ApiException {
        List<BrandSalesReport> res = new ArrayList<>();

        HashMap<String, BrandSalesReport> map = new HashMap<>();

        List<SalesReportData> list1 = getSalesReport(form);

        for(SalesReportData p: list1)
        {
            if(map.containsKey(p.getBrand()))
            {
                int quantity = map.get(p.getBrand()).getQuantity();
                double total = map.get(p.getBrand()).getTotal();

                map.get(p.getBrand()).setQuantity(quantity + p.getQuantity());
                map.get(p.getBrand()).setTotal(total + p.getTotal());
            }
            else{
                BrandSalesReport b = new BrandSalesReport();
                b.setBrand(p.getBrand());
                b.setTotal(p.getTotal());
                b.setQuantity(p.getQuantity());

                map.put(p.getBrand(), b);
            }
        }

        for(Map.Entry<String, BrandSalesReport> e: map.entrySet())
            res.add(e.getValue());

        return res;
    }

    public List<CategorySalesReport> getCategoryReport(DateFilterForm form) throws ApiException {
        List<CategorySalesReport> res = new ArrayList<>();

        HashMap<String, CategorySalesReport> map = new HashMap<>();

        List<SalesReportData> list1 = getSalesReport(form);

        for(SalesReportData p: list1)
        {
            if(map.containsKey(p.getCategory()))
            {
                String key = p.getCategory();
                int quantity = map.get(key).getQuantity();
                double total = map.get(key).getTotal();

                map.get(key).setQuantity(quantity + p.getQuantity());
                map.get(key).setTotal(total + p.getTotal());
            }
            else{
                CategorySalesReport b = new CategorySalesReport();
                b.setCategory(p.getCategory());
                b.setTotal(p.getTotal());
                b.setQuantity(p.getQuantity());

                map.put(p.getCategory(), b);
            }
        }

        for(Map.Entry<String, CategorySalesReport> e: map.entrySet())
            res.add(e.getValue());


        return res;
    }
    String correctFormat(String date)
    {
        String res = date.replace('-', '/');
        return res;
    }

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