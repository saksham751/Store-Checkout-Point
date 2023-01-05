package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;


import static java.util.Objects.isNull;

public class HelperInventory {
    private static ProductMasterApi pApi;
    public static void doesProductExists(InventoryMasterForm form) throws ApiException{
        pApi.getProductApi(form.getId());
    }
    public static void isInventoryValid(InventoryMasterForm form) throws ApiException{
        if(isNull(form.getQuantity()))
        {
            throw new ApiException("Product Name cannot be Empty!");
        }
        if(form.getQuantity()<0)
        {
            throw new ApiException("Quantity cannot be less than 0!");
        }

    }

    public static InventoryMasterPojo convertFormtoPojo(InventoryMasterForm form) throws ApiException{
        InventoryMasterPojo inv=new InventoryMasterPojo();
        inv.setQuantity(form.getQuantity());
        inv.setId(form.getId());
        return inv;
    }
    public static InventoryMasterData convertPojotoData(InventoryMasterPojo p) throws ApiException{
        InventoryMasterData inv=new InventoryMasterData();
        inv.setQuantity(p.getQuantity());
        inv.setId(p.getId());
        return inv;
    }
}
