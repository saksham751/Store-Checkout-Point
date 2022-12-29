package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMasterForm {
    private String barcode;
    private int brand_category;
    private String name;
    private double mrp;
}
