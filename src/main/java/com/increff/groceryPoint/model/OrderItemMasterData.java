package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter@Setter
public class OrderItemMasterData extends OrderItemMasterForm{
    private int id;
}
