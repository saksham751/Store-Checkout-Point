package com.increff.groceryPoint.dto.Helper;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;


import static java.util.Objects.isNull;

public class InventoryHelper {
    public static void validateInventoryForm(InventoryMasterForm form) throws ApiException {
        if(isNull(form.getQuantity()))
        {
            throw new ApiException("Quantity cannot be Empty!");
        }
        if(!(form.getQuantity()>=0))
        {
            throw new ApiException("Quantity cannot be less than 0 or Null!");
        }
        if(isNull(form.getId()) || form.getId()==null){
            throw new ApiException("Enter a Valid Product ID");
        }
    }


    public static InventoryMasterPojo convertFormtoPojo(InventoryMasterForm form){
        InventoryMasterPojo inv=new InventoryMasterPojo();
        inv.setQuantity(form.getQuantity());
        inv.setId(form.getId());
        return inv;
    }
    public static InventoryMasterData convertPojotoData(InventoryMasterPojo p){
        InventoryMasterData inv=new InventoryMasterData();
        inv.setQuantity(p.getQuantity());
        inv.setId(p.getId());
        return inv;
    }

}
