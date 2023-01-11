package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter@Setter
public class OrderItemMasterForm {
    private Integer orderId;
    private String barcode;
    private Integer quantity;

}
