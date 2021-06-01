package com.example.demo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDao;

@Service
public class MemberService {

	@Autowired
	public MemberDao memberDao;

	public HashMap<String, Object> loginMember(HashMap<String, Object> reqMap) {
		return memberDao.loginMember(reqMap);
	}
	
	public HashMap<String, Object> selectMember(HashMap<String, Object> reqMap) {
		
		return memberDao.selectMember(reqMap);
	}

	public HashMap<String, Object> checkId(HashMap<String, Object> reqMap) {
	
		return memberDao.checkId(reqMap);
	}

	public HashMap<String, Object> insertMember(HashMap<String, Object> reqMap) {
		
		return memberDao.insertMember(reqMap);
	}
}
