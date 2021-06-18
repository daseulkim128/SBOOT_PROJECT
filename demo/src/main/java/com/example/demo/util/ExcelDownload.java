package com.example.demo.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestParam;

public class ExcelDownload {
	
	
	  public HSSFWorkbook listExcelDownload(@RequestParam HashMap<String,Object> reqMap) throws Exception {
	        
	        
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        
	        HSSFSheet sheet = workbook.createSheet("엑셀시트명");
	        
	        HSSFRow row = null;
	        
	        HSSFCell cell = null;
	        
	    	List<HashMap<String, Object>> list 	= (List<HashMap<String, Object>>)reqMap.get("list");

	    	row = sheet.createRow(0);
	        String[] headerKey = {"NO", "LAST NAME", "FIRST NAME", "ADDRESS", "CITY"};
	        
	        for(int i=0; i<headerKey.length; i++) {
	            cell = row.createCell(i);
	            cell.setCellValue(headerKey[i]);
	        }
	        
	        for(HashMap<String, Object> map : list) {
				
				Iterator<String> itrs = map.keySet().iterator();

				while(itrs.hasNext()) {
					String key = itrs.next();
					String value = map.get(key).toString();	
					
					cell = row.createCell(0);
		            cell.setCellValue(value);
		            
		            cell = row.createCell(1);
		            cell.setCellValue(value);
		            
		            cell = row.createCell(2);
		            cell.setCellValue(value);
		            
		            cell = row.createCell(3);
		            cell.setCellValue(value);
				}	
			}
	        
	        return workbook;
	    }
	
}
