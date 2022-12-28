package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints =
        { //other constraints
                @UniqueConstraint(name = "UniqueBrandAndCategory", columnNames = { "brand", "category" })})
public class BrandPojo extends AbstractPojo{
 //TODO Use getter setter
    //TODO @UNIQUE(BRAND,CATEOGRY)
    //TODO abstract pojo should have createdat, updated at,version.
    //TODO @TABLEgenerator or @id
    //TODO index on approriate field

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter
    private String brand;
    @Getter @Setter
    private String category;


//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
}
