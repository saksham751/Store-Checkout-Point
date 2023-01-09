package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.increff.groceryPoint.dto.HelperProduct.checkProductExists;
import static java.util.Objects.isNull;

@Service
public class ProductMasterApi {
    @Autowired
    private ProductMasterDao pdao;
    @Autowired
    private BrandMasterApi brandApi;

    @Autowired
    private InventoryMasterApi inventoryApi;

    public void addProductApi(ProductMasterPojo p) throws ApiException {
        int id = p.getBrand_category();
        BrandMasterPojo brandPojo = brandApi.getBrandApi(id);
        if (isNull(brandPojo)) {
            throw new ApiException("Brand id does not exist");
        }

        pdao.insertProductDao(p);
    }

    public void deleteProductApi(int id) throws ApiException {
        getCheck(id);
        pdao.deleteProductDao(id);
    }


    public ProductMasterPojo getProductApi(int id) throws ApiException {
        checkProductExists(id,pdao);
        return getCheck(id);
    }

    public List<ProductMasterPojo> getAllProductApi() {
        return pdao.selectAllProductDao();
    }


    @Transactional
    public void updateProductApi(int id, ProductMasterPojo p) throws ApiException {
        ProductMasterPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setMrp(p.getMrp());
        ex.setBarcode(p.getBarcode());
        ex.setBrand_category(p.getBrand_category());
        pdao.updateProductDao(ex);

    }

    public ProductMasterPojo getCheck(int id) throws ApiException {
        ProductMasterPojo p = pdao.selectProductDao(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }
}