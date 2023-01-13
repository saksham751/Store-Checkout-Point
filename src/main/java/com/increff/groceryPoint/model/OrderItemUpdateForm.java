package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OrderItemUpdateForm {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
}
