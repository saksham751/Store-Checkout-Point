package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//todo change int to Integer
@Entity
@Getter@Setter
@Table(indexes = {
                @Index(name ="orderIdIndex" ,columnList = "OrderId"), @Index(name = "productIdIndex",columnList = "productId")
        })
public class OrderItemMasterPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer orderId;
    @Column(nullable = false)
    private Integer productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double sellingPrice;

}
