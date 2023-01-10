package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.InventoryMasterDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.InventoryMasterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryMasterApi {
    @Autowired
    private InventoryMasterDao invDao;
    @Autowired
    private ProductMasterApi productApi;
    public void addInventoryApi(InventoryMasterPojo p) throws ApiException {
        doesProductExists(p.getId());
        InventoryMasterPojo ex = invDao.selectInventoryDao(p.getId());
        if(ex!=null){
            throw new ApiException("Inventory ID already Exists");
        }

        invDao.insertInventorytDao(p);
    }

    public InventoryMasterPojo getInventoryApi(int id) throws ApiException {
        doesProductExists(id);
        return checkInvExists(id);
    }

    public List<InventoryMasterPojo> getAllInventoryApi() {
        return invDao.selectAllInventoryDao();
    }

    @Transactional
    public void updateInventoryApi(int id, InventoryMasterPojo p) throws ApiException {
        InventoryMasterPojo ex = checkInvExists(id);
        ex.setQuantity(p.getQuantity());
        invDao.updateInventoryDao(p,ex);
    }

    public void doesProductExists(int id) throws ApiException{
        productApi.getProductApi(id);
    }

    public InventoryMasterPojo checkInvExists(int id) throws ApiException{
        InventoryMasterPojo ex = invDao.selectInventoryDao(id);
        if (ex == null) {
            throw new ApiException("Given product does not have any Inventory, id: " + id);
        }
        return ex;
    }
}
