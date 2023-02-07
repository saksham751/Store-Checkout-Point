package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(indexes = {
        @Index(name ="nameIndex" ,columnList = "name"), @Index(name = "brand_categoryIndex",columnList = "brand_category"),
        @Index(name="barcodeIndex",columnList = "barcode")
})
public class ProductMasterPojo extends AbstractPojo{
    //todo change to INeteger
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true, nullable = false)
    private String barcode;
    private String name;
    @Column(nullable = false)
    private Integer brand_category;
    @Column(nullable = false)
    private Double mrp;

    public Integer getId() {
        return id;
    }

}
