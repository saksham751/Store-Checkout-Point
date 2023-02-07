package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.Helper.BrandHelper;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;

import static com.increff.groceryPoint.dto.Helper.BrandHelper.convertFormtoPojo;
import static com.increff.groceryPoint.dto.Helper.BrandHelper.validateBrandForm;
import static com.increff.groceryPoint.dto.Helper.BrandHelper.normalize;

@Service
public class BrandMasterDto {
    @Autowired
    private BrandMasterApi brandApi;
    public int add(BrandMasterForm form) throws ApiException {
        validateBrandForm(normalize(form));
        BrandMasterPojo brandPojo = BrandHelper.convertFormtoPojo(normalize(form));
        return brandApi.add(brandPojo);
    }

    public int delete(@PathVariable int id) throws ApiException{
        return brandApi.delete(id);
    }

    public BrandMasterData get(int id) throws ApiException {
        BrandMasterPojo p = brandApi.get(id);
        return convertFormtoPojo(p);
    }

    public List<BrandMasterData> getAll() throws ApiException {

        List<BrandMasterPojo> brandDataList = brandApi.getAll();
        List<BrandMasterData> brandList = new ArrayList<BrandMasterData>();
        for (BrandMasterPojo p : brandDataList) {
            brandList.add(convertFormtoPojo(p));
        }
        return brandList;
    }

    public void update(int id, BrandMasterForm brandForm) throws ApiException {

        validateBrandForm(normalize(brandForm));
        brandForm=normalize(brandForm);
        BrandMasterPojo p = BrandHelper.convertFormtoPojo(brandForm);
        brandApi.update(id, p);
    }
}
