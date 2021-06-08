package com.example.demo.webConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

	@Autowired
	InterCeptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/*")
				.excludePathPatterns("/static/**")
				.excludePathPatterns("/login")
				.excludePathPatterns("/error")
				.excludePathPatterns("/memberRegist");
	}
	
}
