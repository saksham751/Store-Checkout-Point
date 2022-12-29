package com.increff.groceryPoint.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.groceryPoint.dao.EmployeeDao;
import com.increff.groceryPoint.pojo.EmployeePojo;
import com.increff.groceryPoint.util.StringUtil;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(EmployeePojo p) throws ApiException {
		normalize(p);
		if(StringUtil.isEmpty(p.getName())) {
			throw new ApiException("name cannot be empty");
		}
		dao.insert(p);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public EmployeePojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<EmployeePojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, EmployeePojo p) throws ApiException {
		normalize(p);
		EmployeePojo ex = getCheck(id);
		ex.setAge(p.getAge());
		ex.setName(p.getName());
		dao.update(ex);
	}

	@Transactional
	public EmployeePojo getCheck(int id) throws ApiException {
		EmployeePojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Employee with given ID does not exit, id: " + id);
		}
		return p;
	}

	public static void normalize(EmployeePojo p) {
		p.setName(StringUtil.toLowerCase(p.getName()));
	}
}
