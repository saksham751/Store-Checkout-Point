package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.model.BrandData;
import com.increff.groceryPoint.model.BrandForm;
import com.increff.groceryPoint.pojo.BrandPojo;

import static java.util.Objects.isNull;

public class HelperBrand {
    public static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }

    public static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
    public static void validateBrandForm(BrandForm brandForm)throws ApiException
    {
        if(isNull(brandForm.getBrand()) || brandForm.getBrand().isEmpty())
        {
            throw new ApiException("Brand cannot be Empty!");
        }
        if(isNull(brandForm.getCategory()) || brandForm.getCategory().isEmpty())
        {
            throw new ApiException("Category cannot be Empty!");
        }
    }
    public static BrandForm normalize(BrandForm brandForm){
        brandForm.setBrand(brandForm.getBrand().trim().toLowerCase());
        brandForm.setCategory(brandForm.getCategory().trim().toLowerCase());
        return brandForm;
    }
}
