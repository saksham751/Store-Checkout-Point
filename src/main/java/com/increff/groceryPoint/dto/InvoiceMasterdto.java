//package com.increff.groceryPoint.dto;
//
//import com.increff.groceryPoint.api.OrderMasterApi;
//import com.increff.groceryPoint.pojo.OrderMasterPojo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class InvoiceMasterdto {
//
//    @Autowired
//    OrderMasterApi orderApi;
//    public Resource getInvoice(Integer orderId) throws ApiException, IOException {
//        OrderMasterPojo orderPojo = orderApi.get(orderId);
//        String billDirPath =  orderPojo.getInvoicePath();   //"D:\\IncreffProjectPOS BILLS\\bills\\" + orderId + ".pdf";
//        File file = new File(billDirPath);
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//        return resource;
//    }
//}
