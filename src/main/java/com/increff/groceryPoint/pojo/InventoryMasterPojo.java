package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Getter
@Setter
public class InventoryMasterPojo extends AbstractPojo{
    @Id
    private Integer id;
    @Column(nullable = false)
    private Integer quantity;
}
