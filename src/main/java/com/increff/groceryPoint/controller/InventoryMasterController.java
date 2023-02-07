package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.InventoryMasterDto;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//todo change master to main
@Api
@RestController
@RequestMapping("/api/inventory")
public class InventoryMasterController {
    @Autowired
    private InventoryMasterDto invDto;

    @ApiOperation(value = "Add a product Inventory")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody InventoryMasterForm form) throws ApiException{
        invDto.add(form);
    }
    @ApiOperation(value="Select particular Inventory")
    @RequestMapping(path="/{id}",method=RequestMethod.GET)
    public InventoryMasterData get(@PathVariable int id)throws ApiException{
        return invDto.get(id);
    }
    @ApiOperation(value="Gets all Inventory")
    @RequestMapping(path="", method=RequestMethod.GET)
    public List<InventoryMasterData> getAll() throws ApiException{
        return invDto.getAll();
    }
    @ApiOperation(value="Updates Inventory")
    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public void update(@RequestBody InventoryMasterForm form) throws ApiException {
        invDto.update(form.getId(),form);
    }

}
