package com.increff.groceryPoint.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.UniqueConstraint;

import com.increff.groceryPoint.dto.ApiException;
import org.springframework.stereotype.Repository;

import com.increff.groceryPoint.pojo.UserPojo;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao extends AbstractDao<UserPojo> {

	private static String delete_id = "delete from UserPojo p where id=:id";
	private static String select_id = "select p from UserPojo p where id=:id";
	private static String select_email = "select p from UserPojo p where email=:email";
	private static String select_all = "select p from UserPojo p";

	@Transactional(rollbackFor = ApiException.class)
	public UserPojo insert(UserPojo userPojo) {
		em().persist(userPojo);
		return userPojo;
	}

	@Transactional(readOnly = true)
	public UserPojo select(String email) {
		TypedQuery<UserPojo> query = getQuery(select_email, UserPojo.class);
		query.setParameter("email", email);
		return getSingle(query);
	}
}
