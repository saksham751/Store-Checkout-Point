package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.model.InvoiceForm;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



@Service
public class OrderMasterdto {
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private HelperOrder helpOrder;
    public void add() throws ApiException{
        OrderMasterPojo omp= new OrderMasterPojo();
        orderApi.add(omp);
    }
    public OrderMasterData get(int id) throws ApiException {
        return helpOrder.convert(orderApi.get(id));
    }

    public List<OrderMasterData> getAll() throws ApiException{
        List<OrderMasterPojo> list = orderApi.getAll();
        List<OrderMasterData> list2 = new ArrayList<OrderMasterData>();
        for (OrderMasterPojo p : list) {
            list2.add(helpOrder.convert(p));
        }
        return list2;
    }

    public InvoiceForm getInvoiceData(Integer id) throws ApiException{
        OrderMasterPojo order= orderApi.get(id);
        List<OrderItemMasterPojo> orderItemList = orderItemApi.getAllfromOrderId(id);
        List<OrderItemMasterData> orderItemData = new ArrayList<OrderItemMasterData>();
        for(OrderItemMasterPojo p: orderItemList){
            orderItemData.add(helpOrder.convert(p));
        }
        InvoiceForm invoice = new InvoiceForm();
        invoice.setOrderItemList(orderItemData);
        invoice.setTime(order.getUpdated_At());
        invoice.setOrderId(order.getId());
        return invoice;
    }
    @Transactional(rollbackFor = ApiException.class)
    public void placeOrder(int id) throws ApiException, IOException{
        List<OrderItemMasterPojo> orderItemList = orderItemApi.getAllfromOrderId(id);
        for (OrderItemMasterPojo orderItem : orderItemList) {
            orderApi.placeOrder(id,orderItem);
        }
        OrderMasterPojo orderPojo = orderApi.get(id);
        orderPojo.setStatus("Placed");
        orderApi.update(id, orderPojo);
        InvoiceForm invoiceData=getInvoiceData(id);
        String path= generatePDF(invoiceData);

//        String invoicePath = generatePDF(getInvoiceData(id));
//        InvoiceForm invoiceForm = invoiceGenerator.generateInvoiceForOrder(orderId);
//        RestTemplate restTemplate = new RestTemplate();
//
//        String url = "http://localhost:8080/invoice/api/get-invoice";
//
//        byte[] contents = restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody();
//
////        saving pdf;
//        Path pdfPath = Paths.get("./src/main/resources/pdf/" + id + "_invoice.pdf");
//
//        Files.write(pdfPath, contents);
    }

    public ResponseEntity<byte[]>  generateInvoice(int id) throws ApiException, IOException {
        OrderMasterPojo orderData = orderApi.get(id);

        Path pdfPath = Paths.get("./src/main/resources/pdf/" + orderData.getId() + "_invoice.pdf");

        byte[] contents = Files.readAllBytes(pdfPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = orderData.getId() + ".pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
    public Resource getInvoice(Integer orderId) throws ApiException, IOException {
        OrderMasterPojo orderPojo = orderApi.get(orderId);
        String billDirPath =  "/home/sakshamyadav/repos/pos/grocery-point/src/main/resources/pdf/"+orderId+"_invoice.pdf";   //"D:\\IncreffProjectPOS BILLS\\bills\\" + orderId + ".pdf";
        File file = new File(billDirPath);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }
    public static String generatePDF(InvoiceForm invoiceData) throws IOException {
        String encodedPdf = getEncodedPdf(invoiceData);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(encodedPdf);
        String filePath = "/home/sakshamyadav/repos/pos/grocery-point/src/main/resources/pdf/"  + invoiceData.getOrderId().toString() + ".pdf";
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
