package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ProductMasterApi {
    @Autowired
    private ProductMasterDao pdao;
    @Autowired
    private BrandMasterApi brandApi;

    @Autowired
    private InventoryMasterApi inventoryApi;

    public void add(ProductMasterPojo p) throws ApiException {
        int id = p.getBrand_category();
        checkBrandExistApi(id);
        ProductMasterPojo ex = getfromBarcode(p.getBarcode());
        if(ex!=null){
            throw new ApiException("Barcode already exists");
        }
        pdao.add(p);
    }

    public void delete(int id) throws ApiException {
        getCheckApi(id);
        pdao.delete(id);
    }


    public ProductMasterPojo get(int id) throws ApiException {
        ProductMasterPojo p = getCheckApi(id);
        return p;
    }

    public List<ProductMasterPojo> getAll() {
        return pdao.getAll();
    }


    @Transactional
    public void update(int id, ProductMasterPojo p) throws ApiException {
        ProductMasterPojo ex = getCheckApi(id);
        checkBrandExistApi(p.getBrand_category());
        ProductMasterPojo pro = getfromBarcode(p.getBarcode());
        if(pro!=null){
            throw new ApiException("Barcode already exists");
        }
        ex.setName(p.getName());
        ex.setMrp(p.getMrp());
        ex.setBarcode(p.getBarcode());
        ex.setBrand_category(p.getBrand_category());
        pdao.update(ex);

    }

    public ProductMasterPojo getCheckApi(int id) throws ApiException {
        ProductMasterPojo p = pdao.get(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }
    public void checkBrandExistApi(int id) throws ApiException{
        BrandMasterPojo brandPojo = brandApi.get(id);
        if (isNull(brandPojo)) {
            throw new ApiException("Brand id does not exist");
        }
    }
    public ProductMasterPojo getfromBarcode(String barcode) throws ApiException{
        ProductMasterPojo ex= pdao.getfromBarcode(barcode);
        if (ex == null) {
            throw new ApiException("Product with given barcode does not exist");
        }
        return ex;
    }
}