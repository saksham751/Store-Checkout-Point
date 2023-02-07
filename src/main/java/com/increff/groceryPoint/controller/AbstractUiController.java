package com.increff.groceryPoint.controller;
import com.increff.groceryPoint.model.OrderMasterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.increff.groceryPoint.model.InfoData;
import com.increff.groceryPoint.util.SecurityUtil;
import com.increff.groceryPoint.util.UserPrincipal;

@Controller
public abstract class AbstractUiController {

	@Autowired
	private InfoData info;

	@Value("${app.baseUrl}")
	private String baseUrl;

	protected ModelAndView mav(String page) {
		// Get current user
		UserPrincipal principal = SecurityUtil.getPrincipal();

		info.setEmail(principal == null ? "" : principal.getEmail());
		info.setRole(principal == null ? "" :principal.getRole());
		//System.out.println(principal.getEmail() + " "+principal.getRole());
		// Set info
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		//mav.addObject("role",principal.getRole());
		return mav;
	}
	protected ModelAndView mav (String page, OrderMasterData orderData)
	{
		// Get current user
//        UserPrincipal principal = SecurityUtil.getPrincipal();

//        info.setEmail(principal == null ? "" : principal.getEmail());
		UserPrincipal principal = SecurityUtil.getPrincipal();
		info.setEmail(principal == null ? "" : principal.getEmail());
		info.setRole(principal == null ? "" :principal.getRole());
		ModelAndView mav = new ModelAndView(page);

		mav.addObject("info", info);
		mav.addObject("orderId", orderData.getId());
//		mav.addObject("Time", orderData.getTime());
//		mav.addObject("Status", orderData.getStatus());
		//mav.addObject("CustomerName",);
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

}
