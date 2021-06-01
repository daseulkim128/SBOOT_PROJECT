package com.example.demo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TestDao;

@Service
public class TestService {

	@Autowired
	private TestDao testDao;
	
	public HashMap<String, Object> searchUserList() {
		return testDao.selectUserList();
	}

	public HashMap<String, Object> searchUser(HashMap<String, Object> reqMap) {
		
		
		return testDao.selectUser(reqMap);
	}

	public HashMap<String, Object> registUser(HashMap<String, Object> reqMap) {
		
		return testDao.insertUser(reqMap);
	}

	public HashMap<String, Object> updateUser(HashMap<String, Object> reqMap) {
		
		return testDao.updateUser(reqMap);
	}

	public HashMap<String, Object> deleteUser(HashMap<String, Object> reqMap) {
	
		return testDao.deleteUser(reqMap);
	}
}
