package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dao.OrderMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.OrderItemMasterdto;
import com.increff.groceryPoint.dto.OrderMasterdto;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderItemMasterForm;
import com.increff.groceryPoint.model.ProductMasterForm;
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
    @RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
    public void addOrderItemController(@RequestBody OrderItemMasterForm form) throws ApiException {
        orderItemdto.addOrderItemDto(form);
    }

    @ApiOperation(value="Gets specific Order Item")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
    public OrderItemMasterData getOrderItemController(@PathVariable int id) throws ApiException{
        return orderItemdto.getOrderItemDto(id);
    }

    @ApiOperation(value="Gets all Order Item")
    @RequestMapping(path = "api/orderitem",method = RequestMethod.GET)
    public List<OrderItemMasterData> getAllOrderItemController() throws ApiException{
        return orderItemdto.getAllOrderItemDto();
    }

    @ApiOperation(value = "Updates an OrderItem")
    @RequestMapping(path = "/api/orderitem/{id}",method = RequestMethod.PUT)
    public void updateOrderItemController(@PathVariable int id,@RequestBody OrderItemMasterForm form) throws ApiException{
        orderItemdto.updateOrderItemDto(id,form);
    }

    @ApiOperation(value = "Deletes an OrderItem")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
    public void deleteOrderItemController(@PathVariable int id) throws ApiException{
        orderItemdto.deleteOrderItemDto(id);
    }

    @ApiOperation(value = "Gets orderItems using orderId")
    @RequestMapping(path = "/api/orderitem/order-code/{orderId}",method = RequestMethod.GET)
    public List<OrderItemMasterData> getAllOrderItemOrderIdController(@PathVariable Integer orderId) throws ApiException{
        return orderItemdto.getOrderItemOrderIdDto(orderId);
    }

}
