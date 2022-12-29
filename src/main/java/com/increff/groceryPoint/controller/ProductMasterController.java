package com.increff.groceryPoint.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.ProductMasterPojo;

import com.increff.groceryPoint.dto.ProductMasterdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.increff.groceryPoint.dto.ApiException;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductMasterController {

    @Autowired
    private ProductMasterdto productDto;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void addProductController(@RequestBody ProductMasterForm form) throws ApiException {
        productDto.addProductDto(form);
    }


    @ApiOperation(value = "Deletes a product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
    public void deleteProductController(@PathVariable int id) {
        productDto.deleteProductDto(id);
    }

    @ApiOperation(value = "Gets an product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductMasterData getProductController(@PathVariable int id) throws ApiException {
        ProductMasterPojo p = productDto.getProductDto(id);
        return convert(p);
    }

    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductMasterData> getAllProductController() {
        List<ProductMasterPojo> list = productDto.getAllProductDto();
        List<ProductMasterData> list2 = new ArrayList<ProductMasterData>();
        for (ProductMasterPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Updates an product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void updateProductController(@PathVariable int id, @RequestBody ProductMasterForm f) throws ApiException {
        ProductMasterPojo p = convert(f);
        productDto.updateProductDto(id, p);
    }


    private static ProductMasterData convert(ProductMasterPojo p) {
        ProductMasterData d = new ProductMasterData();
        d.setBrand_category(p.getBrand_category());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setBarcode(p.getBarcode());
        d.setId(p.getId());
        return d;
    }

    private static ProductMasterPojo convert(ProductMasterForm f) {
        ProductMasterPojo p = new ProductMasterPojo();
        p.setBrand_category(f.getBrand_category());
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        p.setBarcode(f.getBarcode());
        return p;
    }

}
