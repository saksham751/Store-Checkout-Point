package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//todo change int to Integer
@Entity
@Getter@Setter
public class OrderItemMasterPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int Id;
    private int OrderId;
    private int productId;
    private int quantity;
    private double sellingPrice;

}
