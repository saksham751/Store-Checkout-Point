package com.increff.groceryPoint.spring;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
				.antMatchers(HttpMethod.GET, "/api/brand/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.GET, "/api/product/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.GET, "/api/inventory/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.GET, "/api/order/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.POST, "/api/sales-report/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.GET, "/api/inventory-report/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.GET, "/api/brand-report/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers(HttpMethod.POST, "/api/pos_day_sales_report/**").hasAnyAuthority("supervisor", "operator")//
				.antMatchers("/api/**").hasAuthority("supervisor")//
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
