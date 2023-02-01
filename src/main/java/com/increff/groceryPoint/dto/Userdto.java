package com.increff.groceryPoint.dto;

import java.util.List;

import com.increff.groceryPoint.api.UserMasterApi;
import com.increff.groceryPoint.model.AddUserForm;
import com.increff.groceryPoint.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.groceryPoint.pojo.UserPojo;

@Service
public class Userdto {

	@Autowired
	private UserMasterApi userApi;

	public UserData add(AddUserForm userForm) throws ApiException {
		if(!userForm.getPassword().equals(userForm.getConfirmPassword())){
			throw new ApiException("Passwords are not same.");
		}
		normalize(userForm);
		UserPojo userPojo = convertFormtoPojo(userForm);
		return convertPojotoData(userApi.add(userPojo));
	}
	public UserPojo get(String email) throws ApiException {
		return userApi.get(email);
	}

//	public List<UserPojo> getAll() {
//		return userApi.getAll();
//	}
//
//	public void delete(int id) {
//		userApi.delete(id);
//	}

	protected static void normalize(AddUserForm form) {
		form.setEmail(form.getEmail().toLowerCase().trim());
		form.setPassword(form.getPassword());
	}
//	protected static void normalize2(UserPojo form) {
//		form.setEmail(form.getEmail().toLowerCase().trim());
//		form.setRole(form.getRole());
//	}
	protected static UserPojo convertFormtoPojo(AddUserForm form) {
		UserPojo userPojo = new UserPojo();
		userPojo.setEmail(form.getEmail());
		userPojo.setPassword(form.getPassword());
		userPojo.setRole("operator");
		return userPojo;
	}

	protected static UserData convertPojotoData(UserPojo userPojo){
		UserData userData = new UserData();
		userData.setEmail(userPojo.getEmail());
		userData.setId(userPojo.getId());
		userData.setRole(userPojo.getRole());
		return userData;
	}
}
