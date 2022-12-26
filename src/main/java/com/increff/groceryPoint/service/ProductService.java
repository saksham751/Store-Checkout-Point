package com.increff.groceryPoint.service;

import com.increff.groceryPoint.dao.BrandDao;
import com.increff.groceryPoint.dao.ProductDao;

import com.increff.groceryPoint.pojo.ProductPojo;
import com.increff.groceryPoint.pojo.BrandPojo;
import com.increff.groceryPoint.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao dao;
    @Autowired
    private BrandService bserv;
    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {

        if(StringUtil.isEmpty(p.getName())) {
            throw new ApiException("name cannot be empty");
        }
        try {
            int id = p.getBrand_category();
            BrandPojo brandPojo = bserv.get(id);
            //System.out.println(brandPojo.getBrand());
            if (brandPojo != null) {
                normalize(p);
            }
        }catch(Exception e) {
            e.printStackTrace();
            throw new ApiException("Brand id does not exist");
        }
        try {
            dao.insert(p);
        }catch(Exception e){
            throw new ApiException("Barcode must be unique");
        }



    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        ex.setBrand_category(p.getBrand_category());
        ex.setName(p.getName());
        ex.setBarcode(p.getBarcode());
        ex.setMrp(p.getMrp());
        dao.update(ex);
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}
