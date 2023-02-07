package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "pos_day_sales")
public class DaySalesPojo extends AbstractPojo{
    @Id
    @Column(nullable = false, name = "date")
    private Date date;
    @Column(nullable = false, name = "invoiced_orders_count")
    private Integer invoicedOrderCount;
    @Column(nullable = false, name = "invoiced_items_count")
    private Integer invoicedItemsCount;
    @Column(nullable = false, name = "total_revenue")
    private Double totalRevenue;
}
