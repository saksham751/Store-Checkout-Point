package com.increff.groceryPoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

	// WEBSITE PAGES
	@RequestMapping(value = "")
	public ModelAndView index() {
		return mav("index.html");
	}

	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}

	@RequestMapping(value = "/site/pricing")
	public ModelAndView pricing() {
		return mav("pricing.html");
	}

	@RequestMapping(value = "/site/features")
	public ModelAndView features() {
		return mav("features.html");
	}

	@RequestMapping(value = "/site/brand")
	public ModelAndView brand(){return mav("brand.html");}

	@RequestMapping(value = "/site/product")
	public ModelAndView product(){return mav("product.html");}

	@RequestMapping(value = "/site/inventory")
	public ModelAndView inventory(){return mav("inventory.html");}
	@RequestMapping(value = "/site/orders")
	public ModelAndView order(){return mav("orderItem.html");}
	@RequestMapping(value = "/site/orderItem")
	public ModelAndView orderItem(){return mav("orderItem.html");}
}
