//package com.increff.groceryPoint.controller;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.increff.groceryPoint.dto.ApiException;
//import com.increff.groceryPoint.dto.OrderMasterdto;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.io.IOException;
//
//@Api
//@RestController
//public class InvoiceMasterController {
//    @Autowired
//    private OrderMasterdto invoiceDto;
//    @RequestMapping(path = "/invoice/{orderId}", method = RequestMethod.GET)
//    public Resource download(@PathVariable Integer orderId) throws ApiException {
//        return invoiceDto.getInvoice(orderId);
//
//    }
//
//}
