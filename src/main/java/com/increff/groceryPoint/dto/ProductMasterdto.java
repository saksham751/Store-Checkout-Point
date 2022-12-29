package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dao.ProductMasterDao;

import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.increff.groceryPoint.dto.HelperProduct.convert;
//import static com.increff.groceryPoint.dto.HelperProduct.validateProductForm;
//import static com.increff.groceryPoint.dto.HelperProduct.normalize;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductMasterdto {
    @Autowired
    private ProductMasterDao dao;
    @Autowired
    private BrandMasterApi bserv;
    @Transactional(rollbackOn = ApiException.class)
    public void addProductDto(ProductMasterForm form) throws ApiException {
        ProductMasterPojo p=convert(form);
        if(StringUtil.isEmpty(p.getName())) {
            throw new ApiException("name cannot be empty");
        }
        try {
            int id = p.getBrand_category();
            BrandMasterPojo brandPojo = bserv.getBrandApi(id);
            //System.out.println(brandPojo.getBrand());
            if (brandPojo != null) {
                normalize(p);
            }
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApiException("Brand id does not exist");
        }
        try {
            dao.insertProductDao(p);
        }catch(Exception e){
            throw new ApiException("Barcode must be unique");
        }



    }

    @Transactional
    public void deleteProductDto(int id) {
        dao.deleteProductDao(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductMasterPojo getProductDto(int id) throws ApiException {
        return checkProductExists(id);
    }

    @Transactional
    public List<ProductMasterPojo> getAllProductDto() {
        return dao.selectAllProductDao();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void updateProductDto(int id, ProductMasterPojo p) throws ApiException {
        normalize(p);
        ProductMasterPojo ex = checkProductExists(id);
        ex.setBrand_category(p.getBrand_category());
        ex.setName(p.getName());
        ex.setBarcode(p.getBarcode());
        ex.setMrp(p.getMrp());
        dao.updateProductDao(ex);
    }

    @Transactional
    public ProductMasterPojo checkProductExists(int id) throws ApiException {
        ProductMasterPojo p = dao.selectProductDao(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(ProductMasterPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}
