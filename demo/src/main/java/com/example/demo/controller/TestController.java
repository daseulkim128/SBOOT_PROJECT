package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CommonService;
import com.example.demo.service.TestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TestController {
	
	private final TestService testService;
	private final CommonService commonService;
	
	@GetMapping("/main")
	public String main() throws Exception {
		return "main";
	}
	
	@GetMapping("/detail")
	public String detail() throws Exception {
		return "detail";
	}

	@GetMapping("/regist")
	public String regist() throws Exception {
		return "regist";
	}

	@PostMapping("/searchUserList")
	public @ResponseBody HashMap<String,Object> searchUserList() {
		
		HashMap<String,Object> retMap =  testService.searchUserList();
		
	    return retMap;
	}
	
	@PostMapping("/searchUser")
	public @ResponseBody HashMap<String, Object> searchUser(@RequestParam HashMap<String, Object> reqMap){
		
		HashMap<String,Object> retMap =  testService.searchUser(reqMap);
		
		retMap.put("files",commonService.selectFiles(reqMap));
		
	    return retMap;
	}
	
//	@PostMapping("/registUser")
//	public @ResponseBody HashMap<String,Object> registUser(@RequestParam(value="files",required=false) MultipartFile[] files, @RequestParam HashMap<String, Object> reqMap) throws Exception {
//		
//		HashMap<String,Object> retMap =  testService.registUser(reqMap);
//		
//		String PERSON_ID = retMap.get("PERSON_ID").toString();
//		
//		if(files!=null) {
//			commonService.saveFile(files, PERSON_ID);
//		}
//		
//		return retMap;
//	}
	
	@PostMapping("/updateUser")
	public @ResponseBody HashMap<String,Object> updateUser(@RequestParam HashMap<String,Object> reqMap) {
		
		HashMap<String,Object> retMap =  testService.updateUser(reqMap);
		
		return retMap;
	}
	
	@PostMapping("/deleteUser")
	public @ResponseBody HashMap<String,Object> deleteUser(@RequestParam HashMap<String,Object> reqMap) {
		
		HashMap<String,Object> retMap =  testService.deleteUser(reqMap);
		
		return retMap;
	}
	
}





