package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.OrderItemMasterApi;
import com.increff.groceryPoint.api.OrderMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.dto.Helper.OrderHelper;
import com.increff.groceryPoint.model.InvoiceForm;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderMasterData;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.OrderItemMasterPojo;
import com.increff.groceryPoint.pojo.OrderMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



@Service
public class OrderMasterDto {
    @Autowired
    private OrderMasterApi orderApi;
    @Autowired
    private OrderItemMasterApi orderItemApi;
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private ProductMasterApi productApi;
    @Autowired
    private OrderHelper helpOrder;
    public int add() throws ApiException{
        OrderMasterPojo omp= new OrderMasterPojo();
        return orderApi.add(omp);
    }
    public OrderMasterData get(int id) throws ApiException {
        return helpOrder.convert(orderApi.get(id));
    }

    public List<OrderMasterData> getAll() throws ApiException{
        List<OrderMasterPojo> orderList = orderApi.getAll();
        List<OrderMasterData> orderDataList = new ArrayList<OrderMasterData>();
        for (OrderMasterPojo p : orderList) {
            orderDataList.add(helpOrder.convert(p));
        }
        return orderDataList;
    }

    public InvoiceForm getInvoiceData(Integer id) throws ApiException{
        OrderMasterPojo order= orderApi.get(id);
        List<OrderItemMasterPojo> orderItemList = orderItemApi.getAllfromOrderId(id);
        List<OrderItemMasterData> orderItemData = new ArrayList<OrderItemMasterData>();
        List<String> productList= new ArrayList<String>();
        InvoiceForm invoice = new InvoiceForm();
        for(OrderItemMasterPojo orderItems: orderItemList){
            orderItemData.add(helpOrder.convert(orderItems));
            productList.add(productApi.get(orderItems.getProductId()).getName());
        }
        invoice.setOrderItemList(orderItemData);
        invoice.setTime(order.getUpdated_At());
        invoice.setOrderId(order.getId());
        invoice.setProductName(productList);
        return invoice;
    }
    public void reduceInventory(int id,OrderItemMasterPojo orderItem) throws ApiException{
        InventoryMasterPojo inv = new InventoryMasterPojo();
        inv.setId(orderItem.getProductId());
        inv.setQuantity(invApi.get(orderItem.getProductId()).getQuantity() - orderItem.getQuantity());
        invApi.update(orderItem.getProductId(), inv);
    }
    @Transactional(rollbackFor = ApiException.class)
    public void placeOrder(int id) throws ApiException, IOException{
        List<OrderItemMasterPojo> orderItemList = orderItemApi.getAllfromOrderId(id);
        for (OrderItemMasterPojo orderItem : orderItemList) {
            reduceInventory(id,orderItem);
        }
        OrderMasterPojo orderPojo = orderApi.get(id);
        orderPojo.setStatus("Placed");
        orderApi.update(id, orderPojo);
        InvoiceForm invoiceData=getInvoiceData(id);
        String path= helpOrder.generatePDF(invoiceData);
    }

    public ResponseEntity<byte[]>  getInvoice(int id) throws ApiException, IOException {
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
}
