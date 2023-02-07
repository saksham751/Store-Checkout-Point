package com.increff.groceryPoint.dto.Helper;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import static java.util.Objects.isNull;

public class BrandHelper {
    public static BrandMasterData convertFormtoPojo(BrandMasterPojo p) {
        BrandMasterData d = new BrandMasterData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }

    public static BrandMasterPojo convertFormtoPojo(BrandMasterForm brandForm) {
        BrandMasterPojo p = new BrandMasterPojo();
        p.setCategory(brandForm.getCategory());
        p.setBrand(brandForm.getBrand());
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
