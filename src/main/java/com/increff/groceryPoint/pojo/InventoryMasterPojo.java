package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
public class InventoryMasterPojo extends AbstractPojo{
    @Id
    private Integer id;
    @Column(nullable = false)
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Enter a valid Number")
    private Integer quantity;

    public Integer getId() {
        return id;
    }
}
