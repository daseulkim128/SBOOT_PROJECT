package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CommonService;
import com.example.demo.service.TestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController2 {
	
	private final TestService testService;
	private final CommonService commonService;
	
	@PostMapping("/registUser")
	public @ResponseBody HashMap<String,Object> registUser(@RequestParam(value="files") MultipartFile[] files, @RequestParam HashMap<String, Object> reqMap) throws Exception {
		
		HashMap<String,Object> retMap =  testService.registUser(reqMap);
		
		String PERSON_ID = retMap.get("PERSON_ID").toString();
		
		if(files!=null) {
			commonService.saveFile(files, PERSON_ID);
		}
		
		return retMap;
	}
}





