package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class OrderMasterData{
    private Integer id;
    private Date time;
    private String status;
}
