package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dao.UserDao;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMasterApi {
    @Autowired
    private UserDao userDao;

    public UserPojo add(UserPojo p) throws ApiException {
        UserPojo existing = userDao.select(p.getEmail());
        if (existing != null) {
            throw new ApiException("User with given email already exists");
        }

        return userDao.insert(p);
    }
    public UserPojo get(String email) throws ApiException {
        return userDao.select(email);
    }

}
