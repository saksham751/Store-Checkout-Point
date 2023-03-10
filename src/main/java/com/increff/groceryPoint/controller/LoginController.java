package com.increff.groceryPoint.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.increff.groceryPoint.model.AddUserForm;
import com.increff.groceryPoint.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.increff.groceryPoint.model.InfoData;
import com.increff.groceryPoint.model.LoginForm;
import com.increff.groceryPoint.pojo.UserPojo;
import com.increff.groceryPoint.dto.ApiException;
import com.increff.groceryPoint.dto.Userdto;
import com.increff.groceryPoint.util.SecurityUtil;
import com.increff.groceryPoint.util.UserPrincipal;

import io.swagger.annotations.ApiOperation;

@Controller
public class LoginController {
	@Autowired
	private Userdto userDto;
	@Autowired
	private InfoData info;
	@Value("#{'${supervisor.email}'.split(',')}")
	private List<String> supervisorList;
	private String supervisorEmail;
	@ApiOperation(value = "Logs in a user")
	@RequestMapping(path = "/session/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView login(HttpServletRequest req, LoginForm f) throws ApiException {
		UserPojo p = userDto.get(f.getEmail());
		boolean authenticated = (p != null && Objects.equals(p.getPassword(), f.getPassword()));
		if (!authenticated) {
			info.setMessage("Invalid username or password");
			return new ModelAndView("redirect:/site/login");
		}

		// Create authentication object
		Authentication authentication = convert(p);
		// Create new session
		HttpSession session = req.getSession(true);
		// Attach Spring SecurityContext to this new session
		SecurityUtil.createContext(session);
		// Attach Authentication object to the Security Context
		SecurityUtil.setAuthentication(authentication);

		return new ModelAndView("redirect:/ui/home");

	}
	@ApiOperation(value = "Signs up a user")
	@RequestMapping(path = "/site/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView signUp(HttpServletRequest req, AddUserForm userForm) throws ApiException {
		try {
			UserData userData = userDto.add(userForm);
			if(userData==null){
				ModelAndView mav = new ModelAndView("signup.html");
				mav.addObject("info", info);
				return mav;
			}
		}catch(Exception e){
			info.setMessage(e.getMessage());
			return new ModelAndView("redirect:/site/signup	");
		}

		return new ModelAndView("redirect:/site/login");
	}

	@RequestMapping(path = "/session/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/site/logout");
	}

	private Authentication convert(UserPojo p) {
		// Create principal
		UserPrincipal principal = new UserPrincipal();
		principal.setEmail(p.getEmail());
		principal.setId(p.getId());

		// Create Authorities
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		String role = "operator";
		for(String s:supervisorList) {
			if (Objects.equals(s, p.getEmail()))
				role = "supervisor";
		}
		principal.setRole(role);

		authorities.add(new SimpleGrantedAuthority(role));
		//System.out.println(role);
		//authorities.add(new SimpleGrantedAuthority(p.getRole()));
		// you can add more roles if required

		// Create Authentication
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null,
				authorities);
		return token;
	}

}
