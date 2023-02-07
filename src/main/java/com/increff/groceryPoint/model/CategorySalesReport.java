package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CategorySalesReport {
    private String category;
    private Integer quantity;
    private double total;
}
