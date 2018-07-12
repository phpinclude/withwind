package kr.pe.withwind.nims.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.pe.withwind.nims.PrevInfoManager;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.utils.APIConstants;
import kr.pe.withwind.nims.xml.Header;


/**
 * 
 * 기초데이터를 읽어서 엑셀 파일을 파일을 만드는 클래스 
 * @author phpinclude@naver.com
 *
 */
public class ExlMake {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	public ExlMake(){
		
	}

	public String doMake(String rptType, String filePath) throws IOException{
		List<XlsMapping> headerVo = XlsVoMappingManager.getInstance().getMappingList(rptType, APIConstants.TYPE_HEADER);
		List<XlsMapping> lineVo = XlsVoMappingManager.getInstance().getMappingList(rptType, APIConstants.TYPE_LINE);
		
		XSSFWorkbook workBook = new XSSFWorkbook();
		
		XSSFSheet headerSheet = workBook.createSheet("header");
		XSSFRow hRow = headerSheet.createRow(0);
		XSSFRow hRow2 = headerSheet.createRow(1);
		
		// 데이터 포멧관련
		XSSFCellStyle textStyle = workBook.createCellStyle();
		textStyle.setDataFormat((short)0x31);
		
		for (XlsMapping hInfo : headerVo){
			
			if (!"Y".equals(hInfo.getViewYn())) continue;
			XSSFCell cell = hRow.createCell(hInfo.getColIdx());
			cell.setCellValue(hInfo.getXlsLabel());
			
			try {
				XSSFCell cell2 = hRow2.createCell(hInfo.getColIdx());
				cell2.setCellValue(PrevInfoManager.getInstance().getValue(hInfo.getFieldNm()));
				
				logger.debug("hInfo.getFieldNm() : " + hInfo.getFieldNm());
				
			}catch(Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
			
			headerSheet.setDefaultColumnStyle(hInfo.getColIdx(), textStyle);
		}
		
		XSSFSheet lineSheet = workBook.createSheet("line");
		XSSFRow lRow = lineSheet.createRow(0);
		
		for (XlsMapping lInfo : lineVo){
			if (!"Y".equals(lInfo.getViewYn())) continue;
			XSSFCell cell = lRow.createCell(lInfo.getColIdx());
			cell.setCellValue(lInfo.getXlsLabel());
			
			// 데이터 포멧관련
			lineSheet.setDefaultColumnStyle(lInfo.getColIdx(), textStyle);
		}
		
		File xlsFile = new File(filePath + "/" + rptType.toUpperCase() + "_Report.xlsx");
		FileOutputStream fos = new FileOutputStream(xlsFile);
		workBook.write(fos);
		fos.close();
		
		return xlsFile.getName();
	}
}
