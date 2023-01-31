package com.increff.groceryPoint.controller;

import com.increff.groceryPoint.model.InfoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteUiController extends AbstractUiController {

	@Autowired
	private InfoData infoData;
	// WEBSITE PAGES
	@RequestMapping(value = "")
	public ModelAndView index() {
		return mav("index.html");
	}
	@RequestMapping(path = "/site/signup", method = RequestMethod.GET)
	public ModelAndView showSignUpPage() {
		infoData.setMessage("");
		return mav("signup.html");
	}
	@RequestMapping(value = "/site/signup")
	public ModelAndView signup() {
		if(!infoData.getEmail().equals("")){
			return new ModelAndView("redirect:/ui/home");
		}
		return mav("signup.html");
	}
	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}

}
