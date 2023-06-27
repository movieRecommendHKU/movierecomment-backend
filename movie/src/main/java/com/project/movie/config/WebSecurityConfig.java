//package com.project.movie.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//				.csrf().disable()
//				.authorizeHttpRequests(requests ->
//						requests
//								.requestMatchers("/").permitAll()
//								.requestMatchers("/account/register").permitAll()
//								.requestMatchers("/account/**")
//				)
//				.authorizeHttpRequests()
//				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//				.loginProcessingUrl("/account/login")
//				.and()
//				.logout()
//				.logoutUrl("/account/logout");
//
//		return http.build();
//	}
//}
