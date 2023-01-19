package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryReportForm {
    private String brand;
    private String category;
    private Integer quantity;
}