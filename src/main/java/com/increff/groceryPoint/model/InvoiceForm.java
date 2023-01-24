package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter@Setter
public class InvoiceForm {
    private Integer orderId;
    private Date time;
    private List<OrderItemMasterData> orderItemList;
}
