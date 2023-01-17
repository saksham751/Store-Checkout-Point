package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Setter@Getter
public class ReportForm {
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String brand;
    private String category;
}
