package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryReportModel {
    private Integer brandCategory;
    private String brand;
    private String category;
    private Integer quantity;
}