package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.api.InventoryMasterApi;

import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import com.increff.groceryPoint.api.ProductMasterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import static com.increff.groceryPoint.dto.Helper.HelperBrand.convert;
import static com.increff.groceryPoint.dto.Helper.HelperProduct.isProductValid;

import static com.increff.groceryPoint.dto.Helper.HelperProduct.*;

@Service
public class ProductMasterdto {
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterApi invApi;

    public int add(ProductMasterForm form) throws ApiException {
        isProductValid(form);
        ProductMasterPojo p=convert(form);
        checkBrandExistApi(form.getBrand_category());
        p=normalize(p);
        return productApi.add(p);
    }

    public void delete(int id) throws ApiException {
        if(invApi.get(id)!=null) {
            invApi.delete(id);
        }
        productApi.delete(id);
    }

    public ProductMasterData get(int id) throws ApiException {
        return convert(productApi.get(id));
    }

    public List<ProductMasterData> getAll() throws ApiException{
        List<ProductMasterPojo> list = productApi.getAll();
        List<ProductMasterData> list2 = new ArrayList<ProductMasterData>();
        for (ProductMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void update(int id, ProductMasterForm form) throws ApiException {
        isProductValid(form);
        ProductMasterPojo p=convert(form);
        normalize(p);
        checkBrandExistApi(p.getBrand_category());
        productApi.update(id,p);
    }
    public void checkBrandExistApi(int id) throws ApiException{
        brandApi.getCheck(id);
    }

}
