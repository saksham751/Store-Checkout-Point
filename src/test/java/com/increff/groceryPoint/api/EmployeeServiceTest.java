package com.increff.groceryPoint.api;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.EmployeeService;
import com.increff.groceryPoint.pojo.EmployeePojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class EmployeeServiceTest extends AbstractUnitTest {

	@Autowired
	private EmployeeService service;


    @Test
	public void testAdd() throws ApiException {
		EmployeePojo p = new EmployeePojo();
		p.setName(" Romil Jain ");
		service.add(p);
	}

	@Test
	public void testNormalize() {
		EmployeePojo p = new EmployeePojo();
		p.setName(" Romil Jain ");
		EmployeeService.normalize(p);
		assertEquals("romil jain", p.getName());
	}

}
