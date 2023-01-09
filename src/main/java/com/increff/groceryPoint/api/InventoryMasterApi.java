package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.InventoryMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.model.InventoryMasterForm;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryMasterApi {
    @Autowired
    private InventoryMasterDao invApi;
    @Autowired
    private static ProductMasterApi productApi;
    public void addInventoryApi(InventoryMasterPojo p) throws ApiException {
        doesProductExists(p.getId());
        invApi.insertInventorytDao(p);
    }

    public InventoryMasterPojo getInventoryApi(int id) throws ApiException {
        InventoryMasterPojo inv= invApi.selectInventoryDao(id);
        if (inv == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + id);
        }
        return inv;
    }

    public List<InventoryMasterPojo> getAllInventoryApi() {
        return invApi.selectAllInventoryDao();
    }


    public void updateInventoryApi(int id, InventoryMasterPojo p) throws ApiException {
        InventoryMasterPojo ex = invApi.selectInventoryDao(id);
        if (ex == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + id);
        }
        invApi.updateInventoryDao(p,ex);
    }

    public static void doesProductExists(int id) throws ApiException{
        productApi.getProductApi(id);
    }
}
