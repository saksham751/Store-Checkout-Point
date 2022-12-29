package com.increff.groceryPoint.api;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

public class SampleTest {

	@Test
	public void testFiles() {
		InputStream is = null;
		is = SampleTest.class.getResourceAsStream("/com/increff/groceryPoint/employee.tsv");
		assertNotNull(is);
	}

}
