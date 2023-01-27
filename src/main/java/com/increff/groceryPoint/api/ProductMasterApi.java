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

    public void add(ProductMasterPojo p) throws ApiException {
        int id = p.getBrand_category();
        checkBarcodeExists(p.getBarcode());
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


    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ProductMasterPojo p) throws ApiException {
        ProductMasterPojo ex = getCheckApi(id);
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
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }
    public ProductMasterPojo getfromBarcode(String barcode) throws ApiException{
        ProductMasterPojo ex= pdao.getfromBarcode(barcode);
        if (ex == null) {
            throw new ApiException("Product with given barcode does not exist");
        }
        return ex;
    }

    public void checkBarcodeExists(String barcode) throws ApiException{
        ProductMasterPojo ex=  pdao.getfromBarcode(barcode);
        if(ex!=null){
            throw new ApiException("Barcode already exists");
        }
    }
}