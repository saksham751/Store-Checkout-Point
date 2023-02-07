package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class DaySalesData {
    private Date date;
    private Integer invoicedOrderCount;
    private Integer invoicedItemsCount;
    private Double totalRevenue;
}
