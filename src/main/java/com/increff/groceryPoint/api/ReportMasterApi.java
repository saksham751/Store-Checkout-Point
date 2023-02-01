package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.ReportMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.SalesReportData;
import com.increff.groceryPoint.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportMasterApi {
    @Autowired
    private ReportMasterDao reportDao;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private ProductMasterApi productApi;
    @Transactional
    public void addReport(DaySalesPojo pojo) throws ApiException {
        try {
            reportDao.insert(pojo);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public List<DaySalesPojo> getAllReport() throws ApiException {
        try {
            return reportDao.selectAll();
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public DaySalesPojo getReportByDate(Date date) throws ApiException {
        return reportDao.select(date);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Date date, DaySalesPojo newPojo)
    {
        DaySalesPojo pojo = reportDao.select(date);
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
    }
    @Transactional(readOnly = true)
    public List<DaySalesPojo> getpos_day_sales(Date start, Date end) throws ApiException{
        return reportDao.getByDateFilter(start,end);
    }
    public List<BrandMasterPojo> getBrandList(String brand, String category){
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
        return brandList;
    }

    public List<SalesReportData> getSalesReportData(List<BrandMasterPojo> brandCategoryPojos, List<OrderItemMasterPojo> orderItemPojos) throws ApiException {
        List<SalesReportData> salesReportDataList = new ArrayList<SalesReportData>();

        for(BrandMasterPojo brandCategoryPojo:brandCategoryPojos){
            SalesReportData salesReportData = new SalesReportData();
            salesReportData.setCategory(brandCategoryPojo.getCategory());
            salesReportData.setBrand(brandCategoryPojo.getBrand());
            Integer qty = 0;Double revenue = 0.0;
            for(OrderItemMasterPojo orderItemPojo:orderItemPojos){
                ProductMasterPojo productPojo = new ProductMasterPojo();
                try {
                    productPojo = productApi.get(orderItemPojo.getProductId());
                }catch(Exception e){
                    continue;
                }
                if(productPojo.getBrand_category()==brandCategoryPojo.getId()){
                    qty += orderItemPojo.getQuantity();
                    revenue += (orderItemPojo.getQuantity())*(orderItemPojo.getSellingPrice());
                }
            }
            salesReportData.setQuantity(qty);
            salesReportData.setTotal(revenue);
            salesReportDataList.add(salesReportData);
        }
        return salesReportDataList;
    }
}
