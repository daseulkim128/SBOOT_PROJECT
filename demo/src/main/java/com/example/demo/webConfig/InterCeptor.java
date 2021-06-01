package com.example.demo.webConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterCeptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String MEM_TYPE = (String) request.getSession().getAttribute("MEM_TYPE");

		if("0100".equals(MEM_TYPE)) {
			return true;
		}else {
			response.sendRedirect("/login");
			return false;
		}
	}
	
}
