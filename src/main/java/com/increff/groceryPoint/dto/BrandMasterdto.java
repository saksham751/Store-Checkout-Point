package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.model.BrandData;
import com.increff.groceryPoint.model.BrandForm;
import com.increff.groceryPoint.pojo.BrandPojo;
import com.increff.groceryPoint.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.increff.groceryPoint.dto.HelperBrand.convert;
import static com.increff.groceryPoint.dto.HelperBrand.validateBrandForm;
import static com.increff.groceryPoint.dto.HelperBrand.normalize;

@Service
public class BrandMasterdto {
    @Autowired
    private BrandMasterApi brandApi;

    public void add(BrandForm form) throws ApiException {
        validateBrandForm(form);
        BrandPojo p = convert(normalize(form));
        brandApi.add(p);
    }


    public void delete(@PathVariable int id) {
        brandApi.delete(id);
//        service.delete(id);
    }

    public BrandData get(int id) throws ApiException {

        BrandPojo p = brandApi.get(id);
        return convert(p);
    }


    public List<BrandData> getAll() throws ApiException {

        List<BrandPojo> list = brandApi.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update( int id,  BrandForm f) throws ApiException {

        validateBrandForm(f);
        f=normalize(f);
        BrandPojo p = convert(f);
        brandApi.update(id, p);
    }
}
