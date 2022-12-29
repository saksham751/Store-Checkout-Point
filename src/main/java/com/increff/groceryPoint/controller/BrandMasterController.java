package com.increff.groceryPoint.controller;

import java.util.List;

import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
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

    @ApiOperation(value = "Adds an Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void addBrandController(@RequestBody BrandMasterForm form) throws ApiException {
        brandDto.addBrandDto(form);

    }


    @ApiOperation(value = "Deletes an brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    public void deleteBrandController(@PathVariable int id) {
        brandDto.deleteBrandDto(id);

    }

    @ApiOperation(value = "Gets an brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandMasterData getBrandController(@PathVariable int id) throws ApiException {
        return brandDto.getBrandDto(id);

    }

    @ApiOperation(value = "Gets list of all brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandMasterData> getAllBrandController() throws ApiException {
        return brandDto.getAllBrandDto();

    }

    @ApiOperation(value = "Updates an brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void updateBrandController(@PathVariable int id, @RequestBody BrandMasterForm f) throws ApiException {
        brandDto.updateBrandDto(id, f);

    }




}
