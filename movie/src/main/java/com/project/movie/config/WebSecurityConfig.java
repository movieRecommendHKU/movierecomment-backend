//package com.project.movie.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests(authorizeRequests ->
////                // 所有请求均放过, spring security 就没有什么用了
////                // anyRequest() 限定任意的请求
////                // permitAll() 无条件允许访问
////                authorizeRequests.anyRequest().permitAll()
////        );
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/hello")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//    }
//}
