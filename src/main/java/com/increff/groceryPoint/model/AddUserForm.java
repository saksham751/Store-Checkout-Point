package com.increff.groceryPoint.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AddUserForm {
    private String email;
    private String password;
    private String confirmPassword;
}
