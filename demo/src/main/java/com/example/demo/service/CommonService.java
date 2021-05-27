package com.example.demo.service;

import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.CommonDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {

	private final CommonDao commonDao;

	/**
	 * 파일을 등록한다
	 * @param PERSON_ID
	 * @return
	 */
	public void saveFile(MultipartFile[] files, String PERSON_ID) throws Exception {
		
		if(files.length>0) {
			for(MultipartFile file : files) {
				
				HashMap<String, Object> reqMap = new HashMap<String, Object>();
				
				String OGN_FILE_NM 		= FilenameUtils.getName(file.getOriginalFilename());	// 원본 파일명
				String FILE_BULK_VAL	= file.getSize() + "";			// 파일 크기값
				
				reqMap.put("PERSON_ID"		, PERSON_ID);
				reqMap.put("OGN_FILE_NM"	, OGN_FILE_NM);
				reqMap.put("FILE_BULK_VAL"	, FILE_BULK_VAL);
				reqMap.put("FILE"			, file.getBytes());
				reqMap.put("MIME_TP"		, getMimeType(file));
				
				commonDao.insertAtth(reqMap);
			}
		}
	}
	
	/**
	 * MIME TYPE 리턴
	 * @param PERSON_ID
	 * @return
	 */
	public String getMimeType(Object o) throws Exception {
		
		String cn = o.getClass().getSimpleName().toLowerCase();
		return new Tika().detect(((MultipartFile) o).getInputStream());
		
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
