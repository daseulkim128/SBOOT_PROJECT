package com.example.demo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	public MemberService memberService;
	
	
	@GetMapping("login")
	public String login() throws Exception {
		return "login";
	}
	
	@GetMapping("memberRegist")
	public String memberRegist() throws Exception {
		return "memberRegist";
	}
	
	@PostMapping("loginMember")
	public @ResponseBody HashMap<String,Object> loginMember(HttpServletRequest request, @RequestParam HashMap<String, Object> reqMap){
		
		HashMap<String, Object> retMap = memberService.loginMember(reqMap);
		HashMap<String, Object> retMap2 = memberService.selectMember(reqMap);
		
		int result =  Integer.parseInt(retMap.get("RESULT").toString());
		
		if(result == 1) {
			String MEM_TYPE	= retMap2.get("MEM_TYPE").toString();
			String MEM_ID	= retMap2.get("MEM_ID").toString();
			
			HttpSession session = request.getSession();
			
			session.setAttribute("MEM_TYPE", MEM_TYPE);
			session.setAttribute("MEM_ID", MEM_ID);
		}

		return retMap;
	}
	
	@PostMapping("checkId")
	public @ResponseBody HashMap<String,Object> checkId(@RequestParam HashMap<String, Object> reqMap){
		
		HashMap<String, Object> retMap = memberService.checkId(reqMap);
		return retMap;
	}
	
	@PostMapping("insertMember")
	public @ResponseBody HashMap<String,Object> insertMember(@RequestParam HashMap<String, Object> reqMap){
		
		HashMap<String, Object> retMap = memberService.insertMember(reqMap);
		return retMap;
	}
}
