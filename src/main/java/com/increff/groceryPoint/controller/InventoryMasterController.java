package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.InventoryMasterdto;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventoryMasterController {
    @Autowired
    private InventoryMasterdto invDto;

    @ApiOperation(value = "Add a product Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void addInventoryController(@RequestBody InventoryMasterForm form) throws ApiException{
        invDto.addInventoryDto(form);
    }
    @ApiOperation(value="Select particular Inventory")
    @RequestMapping(path="/api/inventory/{id}",method=RequestMethod.GET)
    public InventoryMasterData getInventoryController(@PathVariable int id)throws ApiException{
        return invDto.getInventoryDto(id);
    }
    @ApiOperation(value="Gets all Inventory")
    @RequestMapping(path="api/inventory", method=RequestMethod.GET)
    public List<InventoryMasterData> getAllInventoryController() throws ApiException{
        return invDto.getAllInventoryDto();
    }
    @ApiOperation(value="Updates Inventory")
    @RequestMapping(path="/api/inventory/{id}", method = RequestMethod.PUT)
    public void updateInventoryController(@PathVariable int id, @RequestBody InventoryMasterForm form) throws ApiException {
        invDto.updateInventoryDto(id,form);
    }

}
