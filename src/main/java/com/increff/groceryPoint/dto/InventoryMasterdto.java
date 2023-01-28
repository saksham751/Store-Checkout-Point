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

import static com.increff.groceryPoint.dto.HelperInventory.*;


@Service
public class InventoryMasterdto {
    @Autowired
    private InventoryMasterApi invApi;
    @Autowired
    private ProductMasterApi productApi;

    public int add(InventoryMasterForm form)throws ApiException{
        isInventoryValid(form);
        doesProductExists(form.getId());
        return invApi.add(convertFormtoPojo(form));
    }
    public InventoryMasterData get(int id) throws ApiException {
        doesProductExists(id);
        return convertPojotoData(invApi.get(id));
    }

    public List<InventoryMasterData> getAll() throws ApiException{
        List<InventoryMasterPojo> list = invApi.getAll();
        List<InventoryMasterData> list2 = new ArrayList<InventoryMasterData>();
        for (InventoryMasterPojo p : list) {
            list2.add(convertPojotoData(p));
        }
        return list2;
    }

    public void update(int id, InventoryMasterForm form) throws ApiException {
        isInventoryValid(form);
        InventoryMasterPojo p=convertFormtoPojo(form);
        invApi.update(id,p);
    }
    public void doesProductExists(int id) throws ApiException{
        productApi.get(id);
    }


}
