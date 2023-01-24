package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.OrderMasterdto;
import com.increff.groceryPoint.model.OrderMasterData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Api
@RestController
public class OrderMasterController {

    @Autowired
    private OrderMasterdto orderDto;

    @ApiOperation(value = "Adds and Order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void add() throws ApiException {
        orderDto.add();
    }

    @ApiOperation(value = "Gets an Order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderMasterData get(@PathVariable int id) throws ApiException{
        return orderDto.get(id);
    }

    @ApiOperation(value = "Gets all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderMasterData> getAll() throws ApiException{
        return orderDto.getAll();
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "api/order/place/{id}", method = RequestMethod.PUT)
    public void markOrderPlaced(@PathVariable int id) throws ApiException, IOException
    {
        orderDto.placeOrder(id);
    }
    @ApiOperation(value = "Download Invoice")
    @ResponseBody
    @RequestMapping(path = "/api/invoice/{orderId}", method = RequestMethod.GET)
    public Resource getInvoice(@PathVariable Integer orderId) throws Exception {
       return orderDto.getInvoice(orderId);
    }

}
