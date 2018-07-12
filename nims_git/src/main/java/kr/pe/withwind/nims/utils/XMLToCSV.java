package kr.pe.withwind.nims.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.nims.xml.Lines;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.nims.xml.ReportSet;

public class XMLToCSV {
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	// 파일을 열어 조사하고 해당 파일의 분류대로 디렉토리 변경해서 넣는다.
	
	public static final String resultDir = "C:\\csv_result1\\";
	public static final String outputDir = "C:\\csv_final\\";
	
//	public static final String resultDir = "C:\\csv_xml\\";
//	public static final String outputDir = "C:\\csv_final2\\";

	
	public static final String uid = "인증키";
	public static final String bsshCd = "마약류취급자식별번호(9자리)";
	public static final String rptrNm = "홍길동";
	public static final String rptrEntrpsNm = "KIDS";
	public static final String chrgNm = "이길동";
	public static final String chrgTelNo = "02-0000-0000";
	public static final String chrgMpNo = "6667df28b2e4496f41822e8915f1f2a10eb97a0acce865b7a0f8dfc8de15233f06ef0211655fa1bc5f8c497cf4d5d4b55341613151a99362b2a79d1d9619a4f9b9ac1185c507bbff8f33fe069155ed0766bb833516aba897cac4ce91d3abf76cf697d99a361365576e5bf52a3f23c08fdc294ff26aa7d8d39d386db0e1d4bbe39f5669c05f11e0bc24f56bda3d1a428285e9d25faeacf84e89f129f6e58ee5dec78bc79249329c3a6cca24be09c96bf45892f59ee9c87b24a0c62cd16f727ede29424b6bf44732926dda3a74b4c84b324fde100527c46e4b7530aec605cf52c7cff8a7e6c819ac29d78176071cdd1ab29e9e567c1e6437c6ca918c5f2f211ac7";
	
	public static final String oppBsshCd = "TSTNIMSW1";
	public static final String oppBsshNm = "NIMS테스트도매";
	public static final String oppStorgeNo = "S0001";
	public static final String registerId = "담당자ID(웹 로그인ID)";
	
	public static final String dsUseLoc = "관리원 조제실";
	
	
	public void doExeAll() {
		// 변환파일정보
		String[] fileList = FileList.fileList;
		
		// 변환 코멘트 정보
		HashMap<String, String> commentMap = CommentMap.getCommMap();
		Object[] keys = commentMap.keySet().toArray();
		
		//csv변환정보
		VoMapByRptType csvInfo = new VoMapByRptType();
		Map<String,String[]> headerInfo = csvInfo.getHeaderInfo();
		Map<String,String[]> lineInfo = csvInfo.getLineInfo();
		
		int cnt = 0;
		String xmlFileNm = "";
		for (String  csvFileNm : fileList) {
//		String csvFileNm = "";
//		for (String  xmlFileNm : fileList) {
			
			try {
				String rptType = csvFileNm.substring(9, 12);
				xmlFileNm = csvFileNm.replaceAll("csv", "xml").replaceAll("CSV", "xml");
				logger.debug("fileNmae : " + xmlFileNm);
				File xmlFile = new File(resultDir + xmlFileNm);
				
//				String rptType = xmlFileNm.substring(9, 12);
//				csvFileNm = xmlFileNm.replaceAll("xml", "csv");
//				logger.debug("fileNmae : " + xmlFileNm);
//				File xmlFile = new File(resultDir + xmlFileNm);
				
				if (!xmlFile.exists()) continue;
				
				String className = rptType.charAt(0) + rptType.toLowerCase().substring(1) + "Regist";
				Class cls = Class.forName("kr.pe.withwind.nims.xml." + className);
				Regist reObj = (Regist) MakeXml.getInstance().getXml2Obj(new FileInputStream(resultDir + xmlFileNm), cls);
				
				String rptSeCd = reObj.getREPORTSET().getHEADER().get(0).getRPTSECD();
				logger.debug("보고구분 : " + rptSeCd);
				String rptTyCd = reObj.getREPORTSET().getHEADER().get(0).getRPTTYCD();
				logger.debug("보고유형코드 : " + rptTyCd);
				
				ReportSet reportSet = reObj.getREPORTSET();
				reportSet.setUID(uid);
				
				List<Header> headerList = reportSet.getHEADER();
				
				StringBuffer csvSb = new StringBuffer();
				csvSb.append("[CSV,1.0,UTF-8," + rptSeCd + "," + reportSet.getUID() + "]").append(System.lineSeparator());
				
				// 치환작업
				/////////////////////////////////////////////////////////////////////////
				for (Header header : headerList) {

					header.setBSSHCD(bsshCd);
					header.setRPTRNM(rptrNm);
					header.setRPTRENTRPSNM(rptrEntrpsNm);
					header.setCHRGNM(chrgNm);
					header.setCHRGTELNO(chrgTelNo);
					header.setCHRGMPNO(chrgMpNo);
					
					if (!StringUtils.isEmpty(header.getOPPBSSHCD())) header.setOPPBSSHCD(oppBsshCd);
					if (!StringUtils.isEmpty(header.getOPPBSSHNM())) header.setOPPBSSHNM(oppBsshNm);
					if (!StringUtils.isEmpty(header.getOPPSTORGENO())) header.setOPPSTORGENO(oppStorgeNo);
					if (!StringUtils.isEmpty(header.getREGISTERID())) header.setREGISTERID(registerId);
					if (!StringUtils.isEmpty(header.getDSUSELOC())) header.setDSUSELOC(dsUseLoc);
					
					String[] headerCsvInfo = headerInfo.get(rptSeCd);
					String[] lineCsvInfo = lineInfo.get(rptSeCd);
					
					csvSb.append("H");
					for (String fieldNm : headerCsvInfo) {
						csvSb.append(",");
						String tmp = BeanUtils.getProperty(header, fieldNm);
						csvSb.append(tmp);
					}
					csvSb.append(System.lineSeparator());
					
					Lines lines = header.getLINES();
					
					if (lines != null) {
						List<Line> lineList = lines.getLINE();
						
						if (lineList != null) {
							for (Line line : lineList) {
								csvSb.append("L");
								for (String fieldNm : lineCsvInfo) {
									csvSb.append(",");
									String tmp = BeanUtils.getProperty(line, fieldNm);
									csvSb.append(tmp);
								}
								csvSb.append(System.lineSeparator());
							}
						}
					}
					
				}
				////////////////////////////////////////////////////////////////////////
				// 치환작업 끝
				
				String xmlStr1 = MakeXml.getInstance().getObj2Xml(reObj);
				
				// 코멘트 작업
				for (Object key : keys) {
					String source = (String)key;
					String target = commentMap.get(key);
					xmlStr1 = xmlStr1.replaceAll(source, source + target);
				}
				
				// 파일분류 작업
				
				File rptSeDir = new File(outputDir + rptSeCd + "\\" + rptTyCd);
				if (!rptSeDir.exists()) rptSeDir.mkdirs();
				
				FileOutputStream fosXML = new FileOutputStream(outputDir + rptSeCd + "\\" + rptTyCd + "\\" + xmlFileNm);
				fosXML.write(xmlStr1.getBytes());
				fosXML.close();
				
				FileOutputStream fosCSV = new FileOutputStream(outputDir + rptSeCd + "\\" + rptTyCd + "\\" + csvFileNm);
				fosCSV.write(csvSb.toString().getBytes());
				fosCSV.close();
				
//						logger.debug(xmlStr1);
//						logger.debug(csvSb.toString());
				
			}catch(Exception e) {
				logger.debug("오류파일명 : " + xmlFileNm);
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}
	}
	
	public void doOneXml2Csv(String dirName, String xmlFileNm) {
		
		// 변환 코멘트 정보
		HashMap<String, String> commentMap = CommentMap.getCommMap();
		Object[] keys = commentMap.keySet().toArray();
		
		//csv변환정보
		VoMapByRptType csvInfo = new VoMapByRptType();
		Map<String,String[]> headerInfo = csvInfo.getHeaderInfo();
		Map<String,String[]> lineInfo = csvInfo.getLineInfo();
		
		String csvFileNm="";
		try {
			String rptType = xmlFileNm.substring(9, 12);
			csvFileNm = xmlFileNm.replaceAll("xml", "csv");
			logger.debug("fileNmae : " + xmlFileNm);
			
			String className = rptType.charAt(0) + rptType.toLowerCase().substring(1) + "Regist";
			Class cls = Class.forName("kr.pe.withwind.nims.xml." + className);
			Regist reObj = (Regist) MakeXml.getInstance().getXml2Obj(new FileInputStream(dirName + "\\" + xmlFileNm), cls);
			
			String rptSeCd = reObj.getREPORTSET().getHEADER().get(0).getRPTSECD();
			logger.debug("보고구분 : " + rptSeCd);
			String rptTyCd = reObj.getREPORTSET().getHEADER().get(0).getRPTTYCD();
			logger.debug("보고유형코드 : " + rptTyCd);
			
			ReportSet reportSet = reObj.getREPORTSET();
			reportSet.setUID(uid);
			
			List<Header> headerList = reportSet.getHEADER();
			
			StringBuffer csvSb = new StringBuffer();
			csvSb.append("[CSV,1.0,UTF-8," + rptSeCd + "," + reportSet.getUID() + "]").append(System.lineSeparator());
			
			// 치환작업
			/////////////////////////////////////////////////////////////////////////
			for (Header header : headerList) {

				header.setBSSHCD(bsshCd);
				header.setRPTRNM(rptrNm);
				header.setRPTRENTRPSNM(rptrEntrpsNm);
				header.setCHRGNM(chrgNm);
				header.setCHRGTELNO(chrgTelNo);
				header.setCHRGMPNO(chrgMpNo);
				
				if (!StringUtils.isEmpty(header.getOPPBSSHCD())) header.setOPPBSSHCD(oppBsshCd);
				if (!StringUtils.isEmpty(header.getOPPBSSHNM())) header.setOPPBSSHNM(oppBsshNm);
				if (!StringUtils.isEmpty(header.getOPPSTORGENO())) header.setOPPSTORGENO(oppStorgeNo);
				if (!StringUtils.isEmpty(header.getREGISTERID())) header.setREGISTERID(registerId);
				if (!StringUtils.isEmpty(header.getDSUSELOC())) header.setDSUSELOC(dsUseLoc);
				
				String[] headerCsvInfo = headerInfo.get(rptSeCd);
				String[] lineCsvInfo = lineInfo.get(rptSeCd);
				
				csvSb.append("H");
				for (String fieldNm : headerCsvInfo) {
					csvSb.append(",");
					String tmp = BeanUtils.getProperty(header, fieldNm);
					csvSb.append(tmp);
				}
				csvSb.append(System.lineSeparator());
				
				Lines lines = header.getLINES();
				
				if (lines != null) {
					List<Line> lineList = lines.getLINE();
					
					if (lineList != null) {
						for (Line line : lineList) {
							csvSb.append("L");
							for (String fieldNm : lineCsvInfo) {
								csvSb.append(",");
								String tmp = BeanUtils.getProperty(line, fieldNm);
								csvSb.append(tmp);
							}
							csvSb.append(System.lineSeparator());
						}
					}
				}
				
			}
			////////////////////////////////////////////////////////////////////////
			// 치환작업 끝
			
			String xmlStr1 = MakeXml.getInstance().getObj2Xml(reObj);
			
			// 코멘트 작업
			for (Object key : keys) {
				String source = (String)key;
				String target = commentMap.get(key);
				xmlStr1 = xmlStr1.replaceAll(source, source + target);
			}
			
			// 파일분류 작업
			File rptSeDir = new File(outputDir + rptSeCd + "\\" + rptTyCd);
			if (!rptSeDir.exists()) rptSeDir.mkdirs();
			
//			FileOutputStream fosXML = new FileOutputStream(outputDir + rptSeCd + "\\" + rptTyCd + "\\" + xmlFileNm);
//			fosXML.write(xmlStr1.getBytes());
//			fosXML.close();
			
			FileOutputStream fosCSV = new FileOutputStream(outputDir + rptSeCd + "\\" + rptTyCd + "\\" + csvFileNm);
			fosCSV.write(csvSb.toString().getBytes());
			fosCSV.close();
			
		}catch(Exception e) {
			logger.debug("오류파일명 : " + xmlFileNm);
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	public static void main(String[] arg) {
		
		XMLToCSV mainClass = new XMLToCSV();
//		mainClass.doExeAll();
		mainClass.doOneXml2Csv("C:\\csv_final\\UEM\\1\\","TST000027UEM20171210121201_0003.xml");
	}
}