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

import javax.validation.Valid;

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
    public void deleteProductController(@PathVariable int id) throws ApiException{
        productDto.deleteProductDto(id);
    }

    @ApiOperation(value = "Gets an product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductMasterData getProductController(@PathVariable int id) throws ApiException {
        return productDto.getProductDto(id);
    }

    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductMasterData> getAllProductController() throws ApiException{
        return productDto.getAllProductDto();

    }

    @ApiOperation(value = "Updates an product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void updateProductController(@PathVariable int id, @RequestBody ProductMasterForm f) throws ApiException {
        //ProductMasterPojo p = convert(f);
        productDto.updateProductDto(id, f);
    }

}
