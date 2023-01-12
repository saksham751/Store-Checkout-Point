package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.OrderItemMasterdto;
import com.increff.groceryPoint.dto.OrderMasterdto;
import com.increff.groceryPoint.model.OrderItemMasterData;
import com.increff.groceryPoint.model.OrderMasterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@Autowired
	private OrderMasterdto orderDto;
	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product(){return mav("product.html");}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory(){return mav("inventory.html");}
	@RequestMapping(value = "/ui/orders")
	public ModelAndView order(){return mav("orders.html");}
	@RequestMapping(value = "/ui/orderItem/{id}")
	public ModelAndView orderItem(@PathVariable Integer id) throws ApiException {
		OrderMasterData data= orderDto.getOrderDto(id);
		return mav("orderItem.html",data);}
}

