package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Date;
@Getter@Setter
public class OrderMasterData{
    private Integer id;
    private String time;
    private String status;
}
