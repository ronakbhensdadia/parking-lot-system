package com.parking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.parking.security.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	// For Testing Environment
	http.csrf().disable().authorizeRequests()
		.antMatchers("/", "/error", "/webjars/**", "/h2-console/**", "/login/**", "/oauth2/**").permitAll()
		.antMatchers("/api/v1/admin/**").hasRole("ADMIN").antMatchers("/api/v1/parking/**")
		.hasAnyRole("USER", "ADMIN").anyRequest().authenticated().and().headers().frameOptions().sameOrigin()
		.and().oauth2Login().loginPage("/login").defaultSuccessUrl("/success", true).userInfoEndpoint()
		.userService(customOAuth2UserService);
	
	/*
	 * CSRF Enable for QA, Prod
	 * 
	http.authorizeRequests().antMatchers("/", "/error", "/webjars/**", "/h2-console/**", "/login/**", "/oauth2/**")
		.permitAll()
		.antMatchers(HttpMethod.GET, "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
			"/swagger-resources/**")
		.permitAll().antMatchers("/api/v1/admin/**").hasRole("ADMIN").antMatchers("/api/v1/parking/**")
		.hasAnyRole("USER", "ADMIN").anyRequest().authenticated().and().exceptionHandling()
		.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and()
		.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.ignoringAntMatchers("/h2-console/**"))
		.headers().frameOptions().sameOrigin().and().oauth2Login().loginPage("/login")
		.defaultSuccessUrl("/success", true).userInfoEndpoint().userService(customOAuth2UserService);
	*/
    }
}
