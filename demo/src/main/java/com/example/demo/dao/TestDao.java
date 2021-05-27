package com.example.demo.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	

	public HashMap<String, Object> selectUserList() {	
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		
		retMap.put("list", sqlSession.selectList("testSql.selectUserList"));
		
		return retMap;
	}

	public HashMap<String, Object> selectUser(HashMap<String, Object> reqMap) {
		
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		
		retMap.put("detail", sqlSession.selectOne("testSql.selectUser", reqMap));
		
		return retMap;
	}

	public HashMap<String, Object> insertUser(HashMap<String, Object> reqMap) {
		
		int result = sqlSession.insert("testSql.insertUser", reqMap);
		
		reqMap.put("result", result);
		
		return reqMap;
	}

	public HashMap<String, Object> updateUser(HashMap<String, Object> reqMap) {
		
		
		int result = sqlSession.update("testSql.updateUser", reqMap);
		
		reqMap.put("result", result);
		
		return reqMap;
	}

	public HashMap<String, Object> deleteUser(HashMap<String, Object> reqMap) {
		
		
		int result = sqlSession.delete("testSql.deleteUser", reqMap);
		
		reqMap.put("result", result);
		
		return reqMap;
	}

}



