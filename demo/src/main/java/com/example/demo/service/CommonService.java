package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonService {

	/**
	 * 서버에 파일을 등록한다
	 * @param PERSON_ID, files
	 * @return
	 */
	public void saveFile(MultipartFile[] files, String PERSON_ID) throws Exception {
		
		if(files.length>0) {
			
			String FOLDER_PATH  = "C:\\Users\\USER\\fileTest\\"+PERSON_ID+"\\";
			File PATH 			= new File(FOLDER_PATH);
			
			if(!PATH.exists()) {
				try {
					PATH.mkdirs();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			for(MultipartFile file : files) {
				
				String uuid = UUID.randomUUID().toString();
				String OGN_FILE_NM	= file.getOriginalFilename();
				
				try {
					//서버등록 (경로, 파일명)
					file.transferTo(new File(PATH+"\\"+uuid+"______"+OGN_FILE_NM));
				
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
		
		HashMap<String, Object> retMap = new HashMap<String, Object>();

		String PERSON_ID = reqMap.get("PERSON_ID").toString();
		
		File files = new File("C:\\Users\\USER\\fileTest\\"+PERSON_ID+"\\");
		
		File []fileList = files.listFiles();
		
		String fileName = "";
		
		List<HashMap<String, Object>> list = new ArrayList<>();
		
		if(fileList != null) {
			
			for(File file : fileList) {
				
				HashMap<String, Object> fileMap = new HashMap<String,Object>();
				
				if(file.isFile()) {
					fileName = file.getName();
					
					fileMap.put("FILE_ID", fileName.split("______")[0]);
					fileMap.put("OGN_FILE_NM", fileName.split("______")[1]);
				}
				list.add(fileMap);
			}
		}
		retMap.put("list",list);

		return retMap;
	}
}
