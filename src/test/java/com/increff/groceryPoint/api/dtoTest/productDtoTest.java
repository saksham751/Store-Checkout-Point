package com.increff.groceryPoint.api.dtoTest;

import com.increff.groceryPoint.api.AbstractUnitTest;
import com.increff.groceryPoint.api.BrandMasterApi;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.ProductMasterdto;
import com.increff.groceryPoint.model.ProductMasterData;
import com.increff.groceryPoint.model.ProductMasterForm;
import com.increff.groceryPoint.pojo.BrandMasterPojo;
import com.increff.groceryPoint.pojo.ProductMasterPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.increff.groceryPoint.dto.HelperProduct.convert;
import static com.increff.groceryPoint.dto.HelperProduct.normalize;
import static org.junit.Assert.assertEquals;

public class productDtoTest extends AbstractUnitTest {
    @Autowired
    private ProductMasterdto productDto;
    @Autowired
    private BrandMasterApi brandApi;
    private Integer brandCategory;
    @Before
    public void addBrand() throws ApiException {
        BrandMasterPojo brandCategoryPojo = new BrandMasterPojo();
        brandCategoryPojo.setBrand("testbrand");
        brandCategoryPojo.setCategory("testcategory");
        brandCategory=brandApi.add(brandCategoryPojo);
    }
    @Test
    public void test_add() throws ApiException {
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("testproduct");
        productForm.setBarcode("testb@rc0de");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        int id =productDto.add(productForm);
        ProductMasterData productData=productDto.get(id);
        assertEquals("testproduct",productData.getProductName());
        assertEquals("testb@rc0de",productData.getBarcode());
        assertEquals(brandCategory,productData.getBrand_category());
        assertEquals(Double.valueOf(20.0),productData.getMrp());
    }
    @Test(expected = ApiException.class)
    public void test_add_empty() throws ApiException {
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("");
        productForm.setBarcode("");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        productDto.add(productForm);
    }
    @Test(expected = ApiException.class)
    public void test_add_mrp() throws ApiException {
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("");
        productForm.setBarcode("");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(-20.0);
        productDto.add(productForm);
    }
    @Test(expected = ApiException.class)
    public void test_add_brandCategory() throws ApiException {
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("");
        productForm.setBarcode("");
        productForm.setBrand_category(0);
        productForm.setMrp(20.0);
        productDto.add(productForm);
    }
    @Test
    public void test_update() throws ApiException{
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("testproduct");
        productForm.setBarcode("testb@rc0de");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        int id=productDto.add(productForm);
        ProductMasterForm productForm2 = new ProductMasterForm();
        productForm.setProductName("producttest");
        productForm.setBarcode("testb@rc0de");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        productDto.update(id,productForm);
        ProductMasterData productData =productDto.get(id);
        assertEquals("producttest",productData.getProductName());
        assertEquals("testb@rc0de",productData.getBarcode());
        assertEquals(brandCategory,productData.getBrand_category());
        assertEquals(Double.valueOf(20.0),productData.getMrp());
    }
    @Test(expected = ApiException.class)
    public void test_delete() throws ApiException{
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("testproduct");
        productForm.setBarcode("testb@rc0de");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        int id =productDto.add(productForm);
        productDto.delete(id);
        productDto.get(id);
    }
    @Test
    public void test_normalize() throws ApiException{
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("TeSTPRoduct         ");
        productPojo.setBarcode("     TESTb@rc0de   ");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(  20.0);
        ProductMasterPojo normalized_pojo=normalize(productPojo);
        assertEquals("testproduct",normalized_pojo.getName());
        assertEquals("testb@rc0de",normalized_pojo.getBarcode());
        assertEquals(brandCategory,normalized_pojo.getBrand_category());
        assertEquals(Double.valueOf(20.0),normalized_pojo.getMrp());
    }


    @Test
    public void test_convertFormtoPojo(){
        ProductMasterForm productForm = new ProductMasterForm();
        productForm.setProductName("testproduct");
        productForm.setBarcode("testb@rc0de");
        productForm.setBrand_category(brandCategory);
        productForm.setMrp(20.0);
        ProductMasterPojo productPojo=convert(productForm);
        assertEquals("testproduct",productPojo.getName());
        assertEquals("testb@rc0de",productPojo.getBarcode());
        assertEquals(brandCategory,productPojo.getBrand_category());
        assertEquals(Double.valueOf(20.0),productPojo.getMrp());
    }
    @Test
    public void test_convertPojotoData(){
        ProductMasterPojo productPojo = new ProductMasterPojo();
        productPojo.setName("testproduct");
        productPojo.setBarcode("testb@rc0de");
        productPojo.setBrand_category(brandCategory);
        productPojo.setMrp(  20.0);
        ProductMasterData productData=convert(productPojo);
        assertEquals("testproduct",productData.getProductName());
        assertEquals("testb@rc0de",productData.getBarcode());
        assertEquals(brandCategory,productData.getBrand_category());
        assertEquals(Double.valueOf(20.0),productData.getMrp());
    }
}
