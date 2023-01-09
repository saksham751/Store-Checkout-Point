package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;

import static com.increff.groceryPoint.dto.HelperBrand.convert;
import static com.increff.groceryPoint.dto.HelperBrand.validateBrandForm;
import static com.increff.groceryPoint.dto.HelperBrand.normalize;

@Service
public class BrandMasterdto {
    @Autowired
    private BrandMasterApi brandApi;
    //todo brandapi.add
    //todo rename everyone with master
    public void addBrandDto(BrandMasterForm form) throws ApiException {
        validateBrandForm(form);
        BrandMasterPojo p = convert(normalize(form));
        brandApi.addBrandApi(p);
    }


    public void deleteBrandDto(@PathVariable int id) throws ApiException{
        brandApi.deleteBrandApi(id);
    }

    public BrandMasterData getBrandDto(int id) throws ApiException {
        BrandMasterPojo p = brandApi.getBrandApi(id);
        return convert(p);
    }


    public List<BrandMasterData> getAllBrandDto() throws ApiException {

        List<BrandMasterPojo> list = brandApi.getAllBrandApi();
        List<BrandMasterData> list2 = new ArrayList<BrandMasterData>();
        for (BrandMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void updateBrandDto(int id, BrandMasterForm f) throws ApiException {

        validateBrandForm(f);
        f=normalize(f);
        BrandMasterPojo p = convert(f);
        brandApi.updateBrandApi(id, p);
    }
}
