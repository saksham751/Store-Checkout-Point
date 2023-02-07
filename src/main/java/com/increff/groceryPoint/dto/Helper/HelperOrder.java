package com.increff.groceryPoint.dto.Helper;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.*;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


@Service
public class HelperOrder {
    @Autowired
    private ProductMasterApi productpApi;
    public OrderMasterData convert(OrderMasterPojo p) {
        OrderMasterData d = new OrderMasterData();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        d.setTime(formatter.format(p.getTime()));
        d.setId(p.getId());
        d.setStatus(p.getStatus());
        return d;
    }

    public OrderItemMasterPojo convert(OrderItemMasterForm form) throws ApiException {
        OrderItemMasterPojo p=new OrderItemMasterPojo();
        ProductMasterPojo product = productpApi.getfromBarcode(form.getBarcode());
        p.setProductId(product.getId());
        p.setQuantity(form.getQuantity());
        p.setOrderId(form.getOrderId());
        p.setSellingPrice(product.getMrp()*form.getQuantity());
        return p;
    }
    public OrderItemMasterData convert(OrderItemMasterPojo p){
        OrderItemMasterData data=new OrderItemMasterData();
        data.setProductId(p.getProductId());
        data.setQuantity(p.getQuantity());
        data.setId(p.getId());
        data.setSellingPrice(p.getSellingPrice());
        data.setOrderId(p.getOrderId());
        return data;
    }
    public OrderItemMasterPojo convert(OrderItemUpdateForm form) throws ApiException{
        OrderItemMasterPojo data = new OrderItemMasterPojo();
        ProductMasterPojo product = productpApi.get(form.getProductId());
        data.setOrderId(form.getOrderId());
        data.setQuantity(form.getQuantity());
        data.setProductId(form.getProductId());
        data.setSellingPrice(form.getQuantity()*product.getMrp());
        return data;
    }
    public void isOrderItemUpdateValid(OrderItemUpdateForm form) throws ApiException{
        if(form.getQuantity()==null){
            throw new ApiException("Quantity cannot be empty");
        }
        if(form.getQuantity()<1) {
            throw new ApiException("Quantity cannot be less than 1");
        }
    }
    public static String generatePDF(InvoiceForm invoiceData) throws IOException {
        String encodedPdf = getEncodedPdf(invoiceData);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(encodedPdf);
        String filePath = "/home/sakshamyadav/repos/pos/grocery-point/src/main/resources/pdf/"  + invoiceData.getOrderId().toString() + "_invoice.pdf";
        File file = new File(filePath);
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();
        return filePath;
    }
    private static String getEncodedPdf(InvoiceForm invoiceData){
        RestTemplate restTemplate = new RestTemplate();
        String invoiceAppUrl = "http://localhost:8000/invoice";
        String response = restTemplate.postForObject(invoiceAppUrl + "/api/get-invoice",invoiceData, String.class);
        return response;
    }
}
