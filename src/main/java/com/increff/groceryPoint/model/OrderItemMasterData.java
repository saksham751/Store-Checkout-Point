package com.increff.groceryPoint.model;

import com.increff.groceryPoint.pojo.AbstractPojo;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OrderItemMasterData{
    private Integer id;
    private Double sellingPrice;
    private Integer productId;
    private Integer quantity;
    private Integer orderId;

}
