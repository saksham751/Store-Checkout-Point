package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
//todo remove snake case
@Getter@Setter
public class pos_day_sales_Data {
    private String date;
    private Integer invoicedOrderCount;
    private Integer invoicedItemsCount;
    private Double totalRevenue;
}
