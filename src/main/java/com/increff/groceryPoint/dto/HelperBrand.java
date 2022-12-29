package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import static java.util.Objects.isNull;

public class HelperBrand {
    public static BrandMasterData convert(BrandMasterPojo p) {
        BrandMasterData d = new BrandMasterData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }

    public static BrandMasterPojo convert(BrandMasterForm f) {
        BrandMasterPojo p = new BrandMasterPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
    public static void validateBrandForm(BrandMasterForm brandMasterForm)throws ApiException
    {
        if(isNull(brandMasterForm.getBrand()) || brandMasterForm.getBrand().isEmpty())
        {
            throw new ApiException("Brand cannot be Empty!");
        }
        if(isNull(brandMasterForm.getCategory()) || brandMasterForm.getCategory().isEmpty())
        {
            throw new ApiException("Category cannot be Empty!");
        }
    }
    public static BrandMasterForm normalize(BrandMasterForm brandMasterForm){
        brandMasterForm.setBrand(brandMasterForm.getBrand().trim().toLowerCase());
        brandMasterForm.setCategory(brandMasterForm.getCategory().trim().toLowerCase());
        return brandMasterForm;
    }
}
