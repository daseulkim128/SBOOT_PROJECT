package com.example.demo.service;

import java.io.File;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.CommonDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {

	private final CommonDao commonDao;

	/**
	 * 서버에 파일을 등록한다
	 * @param PERSON_ID, files
	 * @return
	 */
	public void saveFile(MultipartFile[] files, String PERSON_ID) throws Exception {
		
		if(files.length>0) {
			
			String FOLDER_PATH  = "C:\\Users\\USER\\excelTest\\"+PERSON_ID+"\\";
			File PATH 			= new File(FOLDER_PATH);
			
			if(!PATH.exists()) {
				try {
					PATH.mkdirs();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			for(MultipartFile file : files) {
				
				HashMap<String, Object> insertMap = new HashMap<String, Object>();	
				String OGN_FILE_NM	= file.getOriginalFilename();

				try {
					
					//DB등록
					insertMap.put("PERSON_ID"	, PERSON_ID);
					insertMap.put("OGN_FILE_NM"	, OGN_FILE_NM);
					insertMap.put("FILE"	   	, file.getBytes());
					commonDao.insertAtth(insertMap);
					
					//서버등록 (경로, 파일명)
					file.transferTo(new File(PATH+"\\"+OGN_FILE_NM));
				
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 파일정보 조회 (다중 파일 혹은 단일 파일을 리턴한다)
	 * @param reqMap
	 * @return
	 */
	public HashMap<String, Object> selectFiles(HashMap<String, Object> reqMap) {

		return commonDao.selectFiles(reqMap);
	}

	/**
	 * 다운로드할 해당 파일 조회하여 리턴한다
	 * @param reqMap
	 * @return
	 */
	public HashMap<String, Object> selectFileForDownload(String fileId) {
		
		return commonDao.selectFileForDownload(fileId);
	}
}
