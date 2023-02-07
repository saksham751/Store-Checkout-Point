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
import static com.increff.groceryPoint.dto.Helper.ProductHelper.validateProductForm;

import static com.increff.groceryPoint.dto.Helper.ProductHelper.*;

@Service
public class ProductMasterDto {
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
    @Autowired
    private InventoryMasterApi invApi;

    public int add(ProductMasterForm form) throws ApiException {
        validateProductForm(form);
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
        List<ProductMasterPojo> productList = productApi.getAll();
        List<ProductMasterData> productData = new ArrayList<ProductMasterData>();
        for (ProductMasterPojo p : productList) {
            productData.add(convert(p));
        }
        return productData;
    }

    public void update(int id, ProductMasterForm form) throws ApiException {
        validateProductForm(form);
        ProductMasterPojo p=convert(form);
        normalize(p);
        checkBrandExistApi(p.getBrand_category());
        productApi.update(id,p);
    }
    public void checkBrandExistApi(int id) throws ApiException{
        brandApi.getCheck(id);
    }

}
