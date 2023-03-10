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
    public int add(InventoryMasterPojo p) throws ApiException {
        InventoryMasterPojo ex = invDao.get(p.getId());
        if(ex!=null){
            throw new ApiException("Inventory ID already Exists");
        }

        return invDao.add(p);
    }

    public Integer delete(Integer id) throws ApiException{
        return invDao.delete(id);
    }
    public InventoryMasterPojo get(int id) throws ApiException {
        return checkInvExists(id);
    }

    public List<InventoryMasterPojo> getAll() {
        return invDao.getAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(int id, InventoryMasterPojo p) throws ApiException {
        InventoryMasterPojo ex = checkInvExists(id);
        ex.setQuantity(p.getQuantity());
    }

    public InventoryMasterPojo checkInvExists(int id) throws ApiException{
        InventoryMasterPojo ex = invDao.get(id);
        if(ex==null){
            InventoryMasterPojo invPojo = new InventoryMasterPojo();
            invPojo.setQuantity(0);
            invPojo.setId(id);
            add(invPojo);
            return invPojo;
        }
        return ex;
    }
}
