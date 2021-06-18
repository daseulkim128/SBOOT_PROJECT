package com.example.demo.webConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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
				.excludePathPatterns("/static/**")			//html, js, img 예외처리
				.excludePathPatterns("/login")
				.excludePathPatterns("/error")
				.excludePathPatterns("/memberRegist");
	}
	
	@Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CrossScriptingFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
