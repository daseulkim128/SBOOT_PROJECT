package com.example.demo.webConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterCeptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
		
		return true;
//		if("/regist".equals(uri)) {
//			
//			response.sendRedirect("/main");
//			return false;
//		}else {
//			return true;
//		}
	}
	
}
