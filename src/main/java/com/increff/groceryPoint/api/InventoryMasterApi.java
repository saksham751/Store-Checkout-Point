package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.InventoryMasterDao;
import com.increff.groceryPoint.dao.ProductMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.increff.groceryPoint.dto.HelperProduct.checkProductExists;
import static java.util.Objects.isNull;

@Service
public class InventoryMasterApi {
    @Autowired
    private InventoryMasterDao idao;
    public void addInventoryApi(int id) throws ApiException {
        InventoryMasterPojo inv = null;
        inv.setId(id);
        inv.setQuantity(0);
        idao.insertInventorytDao(inv);
    }

    public InventoryMasterPojo getInventoryApi(int id) throws ApiException {
        InventoryMasterPojo inv=idao.selectInventoryDao(id);
        if (inv == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + id);
        }
        return inv;
    }

    public List<InventoryMasterPojo> getAllInventoryApi() {
        return idao.selectAllInventoryDao();
    }


    public void updateInventoryApi(int id, InventoryMasterPojo p) throws ApiException {
        InventoryMasterPojo ex = idao.selectInventoryDao(id);
        if (ex == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + id);
        }
        idao.updateInventoryDao(p,ex);
    }
}
