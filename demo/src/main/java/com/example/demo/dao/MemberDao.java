package com.example.demo.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	
	@Autowired
	public SqlSession sessionFactory;


	public HashMap<String, Object> loginMember(HashMap<String, Object> reqMap) {
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = sessionFactory.selectOne("memberSql.loginMember", reqMap);
		
		return retMap;
	}
	

	public HashMap<String, Object> selectMember(HashMap<String, Object> reqMap) {
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		retMap = sessionFactory.selectOne("memberSql.selectMember", reqMap);
		
		return retMap;
	}
	
	public HashMap<String, Object> checkId(HashMap<String, Object> reqMap) {
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		retMap.put("result" , sessionFactory.selectOne("memberSql.checkId", reqMap));
		
		return retMap;
	}

	public HashMap<String, Object> insertMember(HashMap<String, Object> reqMap) {
		
		int result = sessionFactory.insert("memberSql.insertMember", reqMap);
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		retMap.put("result", result);
		
		return retMap;
	}
}
