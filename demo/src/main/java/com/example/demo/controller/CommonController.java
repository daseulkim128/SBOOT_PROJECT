package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.TestService;

@RestController
public class CommonController {
	
	@Autowired
	private TestService testService;

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
	
}
