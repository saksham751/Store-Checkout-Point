package com.increff.groceryPoint.spring;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//todo remove admin
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger logger = Logger.getLogger(SecurityConfig.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http//
			// Match only these URLs
				.requestMatchers()//
				.antMatchers("/api/**")//
				.antMatchers("/ui/**")//
				.and().authorizeRequests()//
//				.antMatchers("/api/admin/**").hasAuthority("operator")//
//				.antMatchers("/api/order/**").hasAuthority("supervisor")//
				.antMatchers("/api/inventory/").hasAuthority("supervisor")//
				.antMatchers("/api/brand").hasAuthority("operator")//
				.antMatchers("/api/product").hasAuthority("operator")//
				.antMatchers("/api/inventory").hasAuthority("operator")//
				.antMatchers("/api/order").hasAuthority("operator")//
				.antMatchers("/api/orderItem").hasAuthority("operator")//
				.antMatchers("/api/**").hasAnyAuthority("supervisor")//
//				.antMatchers("/ui/admin/**").hasAuthority("operator")//
//				.antMatchers("/ui/orders").hasAnyAuthority("supervisor")//
//				.antMatchers("/ui/inventory").hasAnyAuthority("supervisor")//
//				.antMatchers("/ui/**").hasAnyAuthority("supervisor", "operator")//

				// Ignore CSRF and CORS
				.and().csrf().disable().cors().disable();
		logger.info("Configuration complete");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**");
	}

}
