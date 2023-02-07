package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import javax.persistence.*;
@Entity
@Getter@Setter
public class OrderMasterPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Date time;
    @Column(nullable = false)
    private String status;

    public Integer getId() {
        return id;
    }
}
