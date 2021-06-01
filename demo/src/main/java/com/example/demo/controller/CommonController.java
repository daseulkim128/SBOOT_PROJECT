package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@SuppressWarnings("unchecked")
	@GetMapping("excelDownloadUserList")
	public void excelDownload(HttpServletResponse response) throws Exception {
		
		HashMap<String,Object> retMap =  testService.searchUserList();
		
		XSSFWorkbook  workbook = new XSSFWorkbook ();
	        
		XSSFSheet sheet = workbook.createSheet("UserList");
	        
		XSSFRow row = null;
	        
		XSSFCell cell = null;
		
		int rowNo 	= 0;
    
		List<HashMap<String, Object>> list 	= (List<HashMap<String, Object>>)retMap.get("list");
	        
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
        response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=userList.xlsx");

        OutputStream os = response.getOutputStream();
        
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();
	}

	/**
	 * 엑셀파일 업로드시 DB에 데이터를 등록한다
	 * @param file
	 * @return
	 */
	@PostMapping("excelUpload")
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

	/**
	 * 서버에 등록된 파일을 읽어 브라우저 다운로드를 한다
	 * @param p_id, fileNm, response
	 * @return
	 */
	@GetMapping("fileDownload/{p_id}/{fileNm}")
	public void fileDownload(@PathVariable("p_id") String P_ID, @PathVariable("fileNm") String FILE_NM, HttpServletResponse response) throws Exception {

		String PATH		= "C:\\Users\\USER\\fileTest\\"+P_ID+"\\";		
		
		//목적에 따라 컨텐츠 타입이 다름
		response.setHeader("Content-Type", "application/octet-stream");  // 물리적 파일 다운로드 - application/octet-stream
		response.setHeader("Content-Disposition", "attachment; filename=\"" +  FILE_NM.split("______")[1] + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();

		FileInputStream fis = null;	
		
		try {
			File file = new File(PATH + FILE_NM);
			fis = new FileInputStream(file);

			FileCopyUtils.copy(fis, out);
		
		}catch(Exception e) {
			e.printStackTrace();

		}finally {
			if(fis !=null) {
				fis.close();
			}
			out.flush();
			out.close();
		}
	}
}