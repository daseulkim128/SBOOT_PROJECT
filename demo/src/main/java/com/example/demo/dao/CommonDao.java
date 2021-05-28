package com.example.demo.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CommonDao {

	private final SqlSessionTemplate sqlSession;

	public HashMap<String, Object> insertAtth(HashMap<String, Object> reqMap) {
		
		int result = sqlSession.insert("commonSql.insertAtth", reqMap);
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		retMap.put("result", result);
		
		return retMap;
	}

	public HashMap<String, Object> selectFiles(HashMap<String, Object> reqMap) {
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		retMap.put("list", sqlSession.selectList("commonSql.selectFiles", reqMap));
		
		return retMap;
	}

	public HashMap<String, Object> selectFileForDownload(String fileId) {
		
		return sqlSession.selectOne("commonSql.selectFileForDownload", fileId);
	}
}
