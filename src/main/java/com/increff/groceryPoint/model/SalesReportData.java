package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SalesReportData {
    private int id;
    private String barcode;
    private String name;
    private String brand;
    private String category;
    private double mrp;
    private int quantity;
    private double total;
}
