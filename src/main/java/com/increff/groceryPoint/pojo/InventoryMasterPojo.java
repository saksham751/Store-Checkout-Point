package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Getter
@Setter
public class InventoryMasterPojo extends AbstractPojo{
    @Id
    private int id;

    private int quantity;
}
