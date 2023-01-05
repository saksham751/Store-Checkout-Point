package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dao.ProductMasterDao;

import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import com.increff.groceryPoint.api.ProductMasterApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//import static com.increff.groceryPoint.dto.HelperBrand.convert;
import static com.increff.groceryPoint.dto.HelperProduct.isProductValid;

import static com.increff.groceryPoint.dto.HelperProduct.*;

@Service
public class ProductMasterdto {
    @Autowired
    private ProductMasterDao dao;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private BrandMasterApi brandApi;
//removed Transactional
    public void addProductDto(ProductMasterForm form) throws ApiException {
        isProductValid(form);
        ProductMasterPojo p=convert(form);
        productApi.addProductApi(p);
    }

    public void deleteProductDto(int id) {
        productApi.deleteProductApi(id);
    }

    public ProductMasterData getProductDto(int id) throws ApiException {
        return convert(productApi.getProductApi(id));
    }

    public List<ProductMasterData> getAllProductDto() throws ApiException{
        List<ProductMasterPojo> list = productApi.getAllProductApi();
        List<ProductMasterData> list2 = new ArrayList<ProductMasterData>();
        for (ProductMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public void updateProductDto(int id, ProductMasterForm form) throws ApiException {
        isProductValid(form);
        ProductMasterPojo p=convert(form);
        normalize(p);
        productApi.updateProductApi(id,p);
    }




}
