package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class posDaySalesData {
    private String date;
    private Integer invoicedOrderCount;
    private Integer invoicedItemsCount;
    private Double totalRevenue;
}
