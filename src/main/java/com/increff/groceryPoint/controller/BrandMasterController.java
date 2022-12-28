package com.increff.groceryPoint.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.groceryPoint.model.BrandData;
import com.increff.groceryPoint.model.BrandForm;
import com.increff.groceryPoint.dto.BrandMasterdto;
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
public class BrandMasterController {
//TODO SHOULD BE CALLING DTO LAYER
    //TODO PACKAGE FOR DTO AND ADD HELPER CLASS
    //todo remove api from name and add master
    //todo brnadmastercontroller should call brandmasterdto should call brandmasterapi this should call brandmasterdao.
    @Autowired
    private BrandMasterdto brandDto;
//    private HelperBrand helperBrand;
    @ApiOperation(value = "Adds an Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.add(form);
//        BrandPojo p = helperBrand.convert(form);
//        service.add(p);
    }


    @ApiOperation(value = "Deletes an brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) {
        brandDto.delete(id);
//        service.delete(id);
    }

    @ApiOperation(value = "Gets an brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
//        BrandPojo p = service.get(id);
//        return helperBrand.convert(p);
    }

    @ApiOperation(value = "Gets list of all brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() throws ApiException {
        return brandDto.getAll();
//        List<BrandPojo> list = service.getAll();
//        List<BrandData> list2 = new ArrayList<BrandData>();
//        for (BrandPojo p : list) {
//            list2.add(helperBrand.convert(p));
//        }
//        return list2;
    }

    @ApiOperation(value = "Updates an brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        brandDto.update(id, f);
//        BrandPojo p = helperBrand.convert(f);
//        service.update(id, p);
    }




}
