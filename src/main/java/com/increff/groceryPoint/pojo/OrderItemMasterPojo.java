package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//todo change int to Integer
@Entity
@Getter@Setter
public class OrderItemMasterPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;
    @Column(nullable = false)

    private Integer OrderId;
    @Column(nullable = false)

    private Integer productId;
    @Column(nullable = false)

    private Integer quantity;
    @Column(nullable = false)

    private Double sellingPrice;

}
