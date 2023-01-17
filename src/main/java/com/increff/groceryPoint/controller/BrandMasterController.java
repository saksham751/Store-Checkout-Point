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
    @Autowired
    private BrandMasterdto brandDto;

    @ApiOperation(value = "Adds a Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandMasterForm form) throws ApiException {
        brandDto.add(form);

    }
    @ApiOperation(value = "Deletes a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException{
        brandDto.delete(id);

    }
    @ApiOperation(value = "Gets a brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandMasterData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);

    }
    @ApiOperation(value = "Gets list of all brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandMasterData> getAll() throws ApiException {
        return brandDto.getAll();

    }
    @ApiOperation(value = "Updates a brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandMasterForm f) throws ApiException {
        brandDto.update(id, f);

    }
}
