package com.example.demo;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		
		HttpSession session = request.getSession();
		
		session.getAttribute("ASDFASD");
	}

}
