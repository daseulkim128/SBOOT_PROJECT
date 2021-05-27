package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class CommonController {
	
	private final TestService testService;
	private final CommonService commonService;

	@GetMapping("/excelDownloadUserList")
	public void excelDownload() throws Exception {
		
		HashMap<String,Object> reqMap =  testService.searchUserList();
		FileOutputStream fos = null;
		    
	    try {
	    		XSSFWorkbook  workbook = new XSSFWorkbook ();
	 	        
	    		XSSFSheet sheet = workbook.createSheet("UserList");
	 	        
	    		XSSFRow row = null;
	 	        
	    		XSSFCell cell = null;
	    		
	    		int rowNo 	= 0;
	        
	 	        List<HashMap<String, Object>> list 	= (List<HashMap<String, Object>>)reqMap.get("list");
	 	        
	 	        row = sheet.createRow(rowNo);
		        String[] headerKey = {"NO", "LAST_NAME", "FIRST_NAME", "ADDRESS", "HEIGHT", "AGE", "CITY"};
		        
		        for(int i=0; i < headerKey.length; i++) {
		            cell = row.createCell(i);
		            cell.setCellValue(headerKey[i]);
		        }
		        
		        rowNo 	= 1;
		        
		        for(int j = 0; j < list.size(); j ++) {
		        	HashMap<String, Object> map = list.get(j);
		        	
		        	row = sheet.createRow(rowNo++);
		        	
		        	for(int i=0; i<headerKey.length; i++) {
		        		
		        		String key = headerKey[i];
		        		String value = map.get(key).toString();
		        		
		        		cell = row.createCell(i);
		        		cell.setCellValue(value);
		        	}
		        }		        
		        
		        String pattern = "yyyy-MM-dd";
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		        String date = simpleDateFormat.format(new Date());
		        
	        	String localFile = "/Users/USER/excelTest/" + "excelDownTest_"+ date + ".xlsx";
	        	
	        	File file = new File(localFile);
	        	fos = new FileOutputStream(file);

	        	workbook.write(fos);
	        	fos.flush();
	    
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	} finally {
	    		if(fos != null) fos.close();
	    }   
	}


	@PostMapping(value="/excelUpload")
	public @ResponseBody HashMap<String, Object> excelUpload(@RequestParam("file") MultipartFile file) throws Exception{
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try(Workbook workbook = new XSSFWorkbook(file.getInputStream())){
			
			Sheet worksheet = workbook.getSheetAt(0);
			
			for(int i=1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				
				Row row = worksheet.getRow(i);

				HashMap<String, Object> insertMap 	= new HashMap<String, Object>();
				
				insertMap.put("LAST_NAME", row.getCell(0).getStringCellValue());
				
				insertMap.put("FIRST_NAME", row.getCell(1).getStringCellValue());
				
				insertMap.put("ADDRESS", row.getCell(2).getStringCellValue());
				
				insertMap.put("HEIGHT", row.getCell(3).getStringCellValue());
				
				insertMap.put("AGE", row.getCell(4).getStringCellValue());
				
				insertMap.put("CITY", row.getCell(5).getStringCellValue());
				
				testService.registUser(insertMap);
				map.put("message","success");
			}
			
		} catch (Exception e){
			e.printStackTrace();
			map.put("message","fail");
		}
		
		return map;
	}
	
//	@GetMapping("fileDownload/{id}")
//	public void fileDownload(@PathVariable("id") String fileId, HttpServletResponse response) {
//		
//		HashMap<String, Object> retMap = commonService.selectFileForDownload(fileId);
//		
//		String FILE_NM = retMap.get("OGN_FILE_NM").toString();
//
//		String MIME_TYPE = retMap.get("MIME_TP").toString();
//		
//		response.setHeader("Content-Disposition", "attachment; filename=\"" + FILE_NM + "\";");
//        response.setHeader("Content-Transfer-Encoding", "binary"); 
//        response.setHeader("Content-Type", MIME_TYPE);
//        response.setHeader("Pragma", "no-cache;");
//        response.setHeader("Expires", "-1;");
//        
//        try (FileInputStream fis = new FileInputStream(FILE_NM); OutputStream out = response.getOutputStream();) {
//            int readCount = 0;
//            byte[] buffer = new byte[1024];
//            // 파일 읽을 만큼 크기의 buffer를 생성한 후 
//            while ((readCount = fis.read(buffer)) != -1) {
//                out.write(buffer, 0, readCount);
//                // outputStream에 씌워준다
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException("file Load Error");
//        }
//	}
	
	@GetMapping("fileDownload/{id}")
	public void fileDownload(@PathVariable("id") String fileId, HttpServletResponse response) {
		
		HashMap<String, Object> retMap = commonService.selectFileForDownload(fileId);
		
		String FILE_NM = retMap.get("OGN_FILE_NM").toString();
		String MIME_TYPE = retMap.get("MIME_TP").toString();
		
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + FILE_NM + "\";");
        response.setHeader("Content-Type", MIME_TYPE);
        
        try (FileInputStream fis = new FileInputStream(FILE_NM); 
        		OutputStream out = response.getOutputStream();) {
        	
            int readCount = 0;
            byte[] buffer = new byte[1024];
            // 파일 읽을 만큼 크기의 buffer를 생성한 후 
            while ((readCount = fis.read(buffer)) != -1) {
                out.write(buffer, 0, readCount);
                // outputStream에 씌워준다
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Load Error");
        }
	}
	
}