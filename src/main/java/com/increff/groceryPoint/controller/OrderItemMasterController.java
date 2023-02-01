package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.OrderItemMasterdto;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.OrderItemUpdateForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api@RestController
public class OrderItemMasterController {
    @Autowired
    private OrderItemMasterdto orderItemdto;
    @ApiOperation(value = "Adds a OrderItem")
    @RequestMapping(path = "/api/orderItem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemMasterForm form) throws ApiException {
        orderItemdto.add(form);
    }

    @ApiOperation(value="Gets specific Order Item")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
    public OrderItemMasterData get(@PathVariable int id) throws ApiException{
        return orderItemdto.get(id);
    }

    @ApiOperation(value="Gets all Order Item")
    @RequestMapping(path = "api/orderItem",method = RequestMethod.GET)
    public List<OrderItemMasterData> getAll() throws ApiException{
        return orderItemdto.getAll();
    }

    @ApiOperation(value = "Updates an OrderItem")
    @RequestMapping(path = "/api/orderItem/{id}",method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemUpdateForm form) throws ApiException{
        orderItemdto.update(id,form);
    }

    @ApiOperation(value = "Deletes an OrderItem")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException{
        orderItemdto.delete(id);
    }

    @ApiOperation(value = "Gets orderItems using orderId")
    @RequestMapping(path = "/api/orderItem/order-code/{orderId}",method = RequestMethod.GET)
    public List<OrderItemMasterData> getAllfromOrderId(@PathVariable Integer orderId) throws ApiException{
        return orderItemdto.getAllfromOrderId(orderId);
    }

}
