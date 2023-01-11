package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
@Entity
@Getter@Setter
public class OrderMasterPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private ZonedDateTime time;
    @Column(nullable = false)
    private String status;
}
