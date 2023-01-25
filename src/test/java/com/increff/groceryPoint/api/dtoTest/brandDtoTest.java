//package com.increff.groceryPoint.api.dtoTest;
//
//import com.increff.groceryPoint.api.AbstractUnitTest;
//import com.increff.groceryPoint.dto.ApiException;
//import com.increff.groceryPoint.dto.BrandMasterdto;
//import com.increff.groceryPoint.dto.HelperBrand;
//import com.increff.groceryPoint.model.BrandMasterData;
//import com.increff.groceryPoint.model.BrandMasterForm;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.junit.Assert.assertEquals;
//
//public class brandDtoTest extends AbstractUnitTest {
//    @Autowired
//    private BrandMasterdto brandDto;
//    private HelperBrand helperBrand;
//    @Test
//    public void test_add() throws ApiException {
//        BrandMasterForm brandForm = new BrandMasterForm();
//        brandForm.setBrand("Puma");
//        brandForm.setCategory("Sports");
//        int id =brandDto.add(brandForm);
//        BrandMasterData brandData=brandDto.get(id);
//        assertEquals("puma",brandData.getBrand());
//        assertEquals("sports",brandData.getCategory());
//    }
//    @Test
//    public void test_normalize() throws ApiException{
//        BrandMasterForm brandForm = new BrandMasterForm();
//        brandForm.setBrand("Adidas");
//        brandForm.setCategory("Sports");
//        BrandMasterForm normalizedForm=helperBrand.normalize(brandForm);
//        assertEquals("adidas",normalizedForm.getBrand());
//        assertEquals("sports",normalizedForm.getBrand());
//    }
//
//}
