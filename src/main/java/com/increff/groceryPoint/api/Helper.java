package com.increff.groceryPoint.api;

import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.util.StringUtil;

public class Helper {
    public static void normalize(BrandMasterPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }

}
