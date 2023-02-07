package com.increff.groceryPoint.dto;

import com.increff.groceryPoint.api.InventoryMasterApi;
import com.increff.groceryPoint.api.ProductMasterApi;
import com.increff.groceryPoint.model.InventoryMasterData;
import com.increff.groceryPoint.model.InventoryMasterForm;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.increff.groceryPoint.dto.Helper.InventoryHelper.*;


@Service
public class InventoryMasterDto {
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private ProductMasterApi productApi;
//todo validateInventory
    public int add(InventoryMasterForm form)throws ApiException{
        validateInventoryForm(form);
        checkProductExists(form.getId());
        return invApi.add(convertFormtoPojo(form));
    }
    public InventoryMasterData get(int id) throws ApiException {
        checkProductExists(id);
        return convertPojotoData(invApi.get(id));
    }
//todo change name of lists
    public List<InventoryMasterData> getAll() throws ApiException{
        List<InventoryMasterPojo> inventoryData = invApi.getAll();
        List<InventoryMasterData> inventoryList = new ArrayList<InventoryMasterData>();
        for (InventoryMasterPojo inventoryPojo : inventoryData) {
            inventoryList.add(convertPojotoData(inventoryPojo));
        }
        return inventoryList;
    }

    public void update(int id, InventoryMasterForm form) throws ApiException {
        validateInventoryForm(form);
        InventoryMasterPojo p=convertFormtoPojo(form);
        invApi.update(id,p);
    }
    public void checkProductExists(int id) throws ApiException{
        productApi.get(id);
    }


}
