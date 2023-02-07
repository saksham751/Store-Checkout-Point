package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.OrderMasterDto;
import com.increff.groceryPoint.model.OrderMasterData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/order")
public class OrderMasterController {

    @Autowired
    private OrderMasterDto orderDto;

    @ApiOperation(value = "Adds and Order")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public int add() throws ApiException {
        return orderDto.add();
    }

    @ApiOperation(value = "Gets an Order")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public OrderMasterData get(@PathVariable int id) throws ApiException{
        return orderDto.get(id);
    }

    @ApiOperation(value = "Gets all orders")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<OrderMasterData> getAll() throws ApiException{
        return orderDto.getAll();
    }

    @ApiOperation(value = "Mark order placed")
    @RequestMapping(path = "/place/{id}", method = RequestMethod.PUT)
    public void markOrderPlaced(@PathVariable int id) throws ApiException, IOException {
        orderDto.placeOrder(id);
    }
    //todo learn about bytestream.
    @ApiOperation(value = "Download Invoice")
    @ResponseBody
    @RequestMapping(path = "/invoice/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]>  getInvoice(@PathVariable Integer orderId) throws Exception {
       return orderDto.getInvoice(orderId);
    }

}
