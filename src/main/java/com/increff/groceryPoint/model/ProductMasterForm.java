package com.increff.groceryPoint.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMasterForm {
    private String barcode;
    private Integer brand_category;
    private String productName;
    private Double mrp;
}
