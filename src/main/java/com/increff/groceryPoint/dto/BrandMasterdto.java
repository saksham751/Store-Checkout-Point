package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.increff.groceryPoint.dto.HelperBrand.convert;
import static com.increff.groceryPoint.dto.HelperBrand.validateBrandForm;
import static com.increff.groceryPoint.dto.HelperBrand.normalize;

@Service
public class BrandMasterdto {
    @Autowired
    private BrandMasterApi brandApi;
    public void add(BrandMasterForm form) throws ApiException {
        validateBrandForm(form);
        BrandMasterPojo brandPojo = convert(normalize(form));
        brandApi.add(brandPojo);
    }


    public void delete(@PathVariable int id) throws ApiException{
        brandApi.delete(id);
    }

    public BrandMasterData get(int id) throws ApiException {
        BrandMasterPojo p = brandApi.get(id);
        return convert(p);
    }


    public List<BrandMasterData> getAll() throws ApiException {

        List<BrandMasterPojo> list = brandApi.getAll();
        List<BrandMasterData> list2 = new ArrayList<BrandMasterData>();
        for (BrandMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, BrandMasterForm f) throws ApiException {

        validateBrandForm(f);
        f=normalize(f);
        BrandMasterPojo p = convert(f);
        brandApi.update(id, p);
    }
}
