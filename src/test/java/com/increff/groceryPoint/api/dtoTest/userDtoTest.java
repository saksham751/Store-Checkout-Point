package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.Userdto;
import com.increff.groceryPoint.model.AddUserForm;
import com.increff.groceryPoint.model.UserData;
import com.increff.groceryPoint.pojo.UserPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class userDtoTest extends AbstractUnitTest {
    @Autowired
    private Userdto userDto;

    @Test
    public void testAdd() throws ApiException {
        AddUserForm userForm = new AddUserForm();
        userForm.setEmail("test@increff.com");
        userForm.setPassword("testpassword");
        userForm.setConfirmPassword("testpassword");
        UserData userData = userDto.add(userForm);
        assertEquals("test@increff.com",userData.getEmail());
    }
    @Test
    public void testGet() throws ApiException {
        AddUserForm userForm = new AddUserForm();
        userForm.setEmail("test@increff.com");
        userForm.setPassword("testpassword");
        userForm.setConfirmPassword("testpassword");
        userDto.add(userForm);
        UserPojo userPojo=userDto.get("test@increff.com");
        assertEquals("test@increff.com",userPojo.getEmail());
        assertEquals("testpassword",userPojo.getPassword());
        assertEquals("operator",userPojo.getRole());
    }
}
