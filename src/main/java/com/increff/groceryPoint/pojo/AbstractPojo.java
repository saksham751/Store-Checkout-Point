package com.increff.groceryPoint.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractPojo<ID> {
    @CreationTimestamp
    private Timestamp Created_At;
    @UpdateTimestamp
    private Timestamp Updated_At;
    @Version
    private Integer Version;

    public abstract ID getId();

}
