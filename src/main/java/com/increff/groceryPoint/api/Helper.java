package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandPojo;
import com.increff.groceryPoint.util.StringUtil;

import java.util.Objects;

public class Helper {
    public static void normalize(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }

}
