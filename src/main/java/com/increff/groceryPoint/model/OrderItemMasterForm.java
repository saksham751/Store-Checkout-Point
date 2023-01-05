package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OrderItemMasterForm {
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;

}
