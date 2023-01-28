package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.BrandMasterdto;
import com.increff.groceryPoint.dto.HelperBrand;
import com.increff.groceryPoint.model.BrandMasterData;
import com.increff.groceryPoint.model.BrandMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import static com.increff.groceryPoint.dto.HelperBrand.normalize;
import static com.increff.groceryPoint.dto.HelperBrand.validateBrandForm;
import static org.junit.Assert.assertEquals;

public class brandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandMasterdto brandDto;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void test_add() throws ApiException {
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setBrand("Puma");
        brandForm.setCategory("Sports");
        int id =brandDto.add(brandForm);
        BrandMasterData brandData=brandDto.get(id);
        assertEquals("puma",brandData.getBrand());
        assertEquals("sports",brandData.getCategory());
    }
    @Test(expected = ApiException.class)
    public void test_duplicate() throws ApiException {
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setCategory("string");
        brandForm.setBrand("string");
        brandDto.add(brandForm);
        brandDto.add(brandForm);
    }
    @Test
    public void test_update() throws ApiException{
        BrandMasterForm brandform = new BrandMasterForm();
        brandform.setBrand("testBrand");
        brandform.setCategory("testCategory");
        int id=brandDto.add(brandform);
        BrandMasterForm brandform2 = new BrandMasterForm();
        brandform2.setBrand("brandTest");
        brandform2.setCategory("categoryTest");
        brandDto.update(id,brandform2);
        BrandMasterData data =brandDto.get(id);
        assertEquals("brandtest",data.getBrand());
        assertEquals("categorytest",data.getCategory());
    }
    @Test(expected = ApiException.class)
    public void test_delete() throws ApiException{
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setBrand("Puma");
        brandForm.setCategory("Sports");
        int id =brandDto.add(brandForm);
        brandDto.delete(id);
        brandDto.get(id);
    }
    @Test
    public void test_normalize() throws ApiException{
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setBrand("    testBrand");
        brandForm.setCategory("     testCategory      ");
        int id=brandDto.add(brandForm);
        BrandMasterForm normalized_form=normalize(brandForm);
        assertEquals("testbrand",normalized_form.getBrand());
        assertEquals("testcategory",normalized_form.getCategory());
    }
    @Test(expected = ApiException.class)
    public void test_ValidationBrand() throws ApiException{
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setBrand("");
        brandForm.setCategory("category");
        BrandMasterForm normalizedForm=normalize(brandForm);
        validateBrandForm(normalizedForm);
    }
    @Test
    public void test_ValidationCategory() throws ApiException{
        exceptionRule.expect(ApiException.class);
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setBrand("testBrand");
        brandForm.setCategory("");
        BrandMasterForm normalizedForm=normalize(brandForm);
        validateBrandForm(normalizedForm);
    }

    @Test
    public void test_convertFormtoPojo(){
        BrandMasterForm brandForm = new BrandMasterForm();
        brandForm.setBrand("testbrand");
        brandForm.setCategory("testcategory");
        BrandMasterPojo brandPojo=HelperBrand.convert(brandForm);
        assertEquals("testbrand",brandPojo.getBrand());
        assertEquals("testcategory",brandPojo.getCategory());
    }
    @Test
    public void test_convertPojotoData(){
        BrandMasterPojo brandPojo = new BrandMasterPojo();
        brandPojo.setBrand("testbrand");
        brandPojo.setCategory("testcategory");
        brandPojo.setId(Integer.valueOf(0));
        BrandMasterData brandData=HelperBrand.convert(brandPojo);
        assertEquals("testbrand",brandData.getBrand());
        assertEquals("testcategory",brandData.getCategory());
        assertEquals(Integer.valueOf(0),brandData.getId());
    }
}
