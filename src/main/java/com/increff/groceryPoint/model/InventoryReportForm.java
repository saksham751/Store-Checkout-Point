package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class InventoryReportForm {
    private String brand;
    private String category;
    private Integer quantity;
}