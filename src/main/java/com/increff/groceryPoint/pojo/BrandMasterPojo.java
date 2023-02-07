package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( uniqueConstraints = {
            @UniqueConstraint(name = "UniqueBrandAndCategory", columnNames = { "brand", "category" })},
        indexes = {
            @Index(name ="brandIndex" ,columnList = "brand"), @Index(name = "categoryIndex",columnList = "category")
})
@Getter
@Setter
public class BrandMasterPojo extends AbstractPojo{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String category;
    public Integer getId() {
        return id;
    }
}
