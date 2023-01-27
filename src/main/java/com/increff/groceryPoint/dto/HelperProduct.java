package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import com.increff.groceryPoint.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.isNull;

public class HelperProduct {
    public static void normalize(ProductMasterPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName().trim()));
        p.setBarcode(p.getBarcode().trim().toLowerCase());
    }
    public static ProductMasterData convert(ProductMasterPojo p) {
        ProductMasterData d = new ProductMasterData();
        d.setBrand_category(p.getBrand_category());
        d.setProductName(p.getName());
        d.setMrp(p.getMrp());
        d.setBarcode(p.getBarcode());
        d.setId(p.getId());
        return d;
    }

    public static ProductMasterPojo convert(ProductMasterForm f) {
        ProductMasterPojo p = new ProductMasterPojo();
        p.setBrand_category(f.getBrand_category());
        p.setName(f.getProductName().trim().toLowerCase());
        p.setMrp(f.getMrp());
        p.setBarcode(f.getBarcode());
        return p;
    }

    public static void isProductValid(ProductMasterForm form) throws ApiException{
        if(isNull(form.getProductName()) || form.getProductName().isEmpty())
        {
            throw new ApiException("Product Name cannot be Empty!");
        }
        if(!(form.getMrp()>=0)){
            throw new ApiException("Enter a valid MRP");
        }
        if(isNull(form.getMrp()) || form.getMrp()<0)
        {
            throw new ApiException("MRP cannot be Empty! or Less than 0");
        }
        if(isNull(form.getBarcode()) || form.getBarcode().isEmpty())
        {
            throw new ApiException("Barcode cannot be Empty!");
        }
        if(isNull(form.getBrand_category()))
        {
            throw new ApiException("Brand Category cannot be Empty!");
        }


    }


}
