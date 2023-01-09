package com.increff.groceryPoint.model;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMasterForm {
    private String barcode;
    private int brand_category;

    private String productName;
    private Double mrp;
}
