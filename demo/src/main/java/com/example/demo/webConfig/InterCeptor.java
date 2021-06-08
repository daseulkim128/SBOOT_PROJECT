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
		
		HttpSession session = request.getSession();
		String URI = request.getRequestURI();
		
		boolean login = session.getAttribute("login") == null ? false : (boolean) session.getAttribute("login");

		//api 접근인지 페이지 접근인지 request.header로 판별
		if(isAjax(request)) {
			//api 요청
			//제외할 api - session 체크 안함
			if("/loginMember".equals(URI)) {
				return true;
			}
			if("/insertMember".equals(URI)) {
				return true;
			}
			//현재는 전부 패스
			return true;
		}
		
		//페이지 요청
		if(login) {
			String MEM_TYPE = (String) request.getSession().getAttribute("MEM_TYPE");
			
			if("/home".equals(URI)) {
				if("0100".equals(MEM_TYPE)) {
					response.sendRedirect("/main");
				}
			} 
			
			return true;
		}else {
			session.invalidate();
			response.sendRedirect("/login");
			return false;
		}
	}
	
	public boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	
}
