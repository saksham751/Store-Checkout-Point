package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductMasterApi {
    @Autowired
    private ProductMasterDao productDao;

    public int add(ProductMasterPojo p) throws ApiException {
        int id = p.getBrand_category();
        checkBarcodeExists(p.getBarcode());
        return productDao.add(p);
    }

    public void delete(int id) throws ApiException {
        getCheckApi(id);
        productDao.delete(id);
    }

    public ProductMasterPojo get(int id) throws ApiException {
        ProductMasterPojo p = getCheckApi(id);
        return p;
    }

    public List<ProductMasterPojo> getAll() {
        return productDao.getAll();
    }


    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, ProductMasterPojo p) throws ApiException {
        ProductMasterPojo ex = getCheckApi(id);
        ProductMasterPojo pro = getfromBarcode(p.getBarcode());
        if(pro!=null && pro.getId()!=ex.getId()) {
            throw new ApiException("Barcode already exists");
        }
        ex.setName(p.getName());
        ex.setMrp(p.getMrp());
        ex.setBarcode(p.getBarcode());
        ex.setBrand_category(p.getBrand_category());
    }

    public ProductMasterPojo getCheckApi(int id) throws ApiException {
        ProductMasterPojo p = productDao.get(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exist, id: " + id);
        }
        return p;
    }
    public ProductMasterPojo getfromBarcode(String barcode) throws ApiException{
        ProductMasterPojo ex= productDao.getfromBarcode(barcode);
        if (ex == null) {
            throw new ApiException("Product with given barcode does not exist");
        }
        return ex;
    }

    public void checkBarcodeExists(String barcode) throws ApiException{
        ProductMasterPojo ex=  productDao.getfromBarcode(barcode);
        if(ex!=null){
            throw new ApiException("Barcode already exists");
        }
    }
}