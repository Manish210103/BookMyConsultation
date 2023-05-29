package com.upgrad.bookmyconsultation.config;

import com.upgrad.bookmyconsultation.servlet.AuthFilter;
import com.upgrad.bookmyconsultation.servlet.CorsFilter;
import com.upgrad.bookmyconsultation.servlet.RequestContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.upgrad.bookmyconsultation.controller")
//@ServletComponentScan("com.upgrad.bookmyconsultation.servlet")
public class WebConfiguration {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authFilter);
        registration.addUrlPatterns("/*");
        registration.setName("Auth Filter");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("Cors Filter");
        registration.setOrder(0);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<RequestContextFilter> reqContextFilterRegistration() {
        FilterRegistrationBean<RequestContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestContextFilter());
        registration.addUrlPatterns("/*");
        registration.setName("reqContext Filter");
        registration.setOrder(1);
        return registration;
    }
}
