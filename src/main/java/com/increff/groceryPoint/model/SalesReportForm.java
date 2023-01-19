package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter@Getter
public class SalesReportForm {
    private Date start;
    private Date end;
    private String brand;
    private String category;
}
