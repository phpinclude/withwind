package kr.pe.withwind.nims.xls;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.BsshSetting;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.utils.APIConstants;
import kr.pe.withwind.nims.xls.adapter.NimsAdpInf;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.nims.xml.Lines;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.nims.xml.ReportSet;
import kr.pe.withwind.nims.xml.SlmRegist;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExlRead {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	public ExlRead(){
		
	}
	
	public <T> T getExl2Obj(InputStream is, Class<T> cls) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, NimsException {
		return getExl2Obj(is,cls,"TSTNIMSW1", "53d60c5e9233508f59a371c05f9098636c2a5c3f5d69e190c81ce483cb4bd51d");
	}
	
	public <T> T getExl2Obj(InputStream is, Class<T> cls, String bsshCd, String UID) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalAccessException, InvocationTargetException, InstantiationException, NimsException{
		BsshSetting param = new BsshSetting();
		param.setAuthKey(UID);
		param.setBsshCd(bsshCd);
		param.setRptrNm("홍길동");
		param.setRptrEntrpsNm("테스트업체");
		return getExl2Obj(is,cls,param);
	}

	public <T> T getExl2Obj(InputStream is, Class<T> cls, BsshSetting bsshInfo) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalAccessException, InvocationTargetException, InstantiationException, NimsException{
		
		
		XlsVoMappingManager xlsMapManager = XlsVoMappingManager.getInstance();		
		
		XSSFWorkbook workBook = new XSSFWorkbook(is);
		XSSFSheet sheet = workBook.getSheetAt(0);
		
		XSSFRow row = sheet.getRow(1);
		
		// 클래스 이름으로 해당 보고 유형을 알아낸다.
		String rptSeCd = cls.getSimpleName().substring(0, 3).toUpperCase();
		
		Regist regist = (Regist) cls.newInstance();
		
		List<XlsMapping> hMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_HEADER);
		
		ReportSet rset = new ReportSet();
		Header header = new Header();
		
		// 헤더 기초값 셋팅
		header.setBSSHCD(bsshInfo.getBsshCd()); // 취급자 식별번호
		header.setRPTSECD(rptSeCd); // 보고구분코드
		header.setRPTRNM(bsshInfo.getRptrNm()); // 보고자명
		header.setRPTRENTRPSNM(bsshInfo.getRptrEntrpsNm()); // 보고자업체명
		
		//헤더 처리
		//////////////////////////////////////////////////////////////////////////////////
		logger.debug("헤더처리 시작");
		for (XlsMapping info : hMappingList){
			
			int cellIdx = info.getColIdx();
			
			try {
				if (info.getRefType().equalsIgnoreCase("N")) {
					BeanUtils.setProperty(header, info.getFieldNm(), row.getCell(cellIdx) == null ? "" : row.getCell(cellIdx).getStringCellValue());
				}else if (info.getRefType().equalsIgnoreCase("D")) {
					
					if (info.getViewYn().equalsIgnoreCase("Y")) 
						BeanUtils.setProperty(header, info.getFieldNm(), row.getCell(cellIdx) == null ? "" : row.getCell(cellIdx).getStringCellValue());
					else 
						BeanUtils.setProperty(header, info.getFieldNm(),info.getRefDefault());
					
				}else if (info.getRefType().equalsIgnoreCase("A")) {
					Class adtCls = null;

					adtCls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + info.getRefDefault());
					NimsAdpInf nimsAdp = (NimsAdpInf) adtCls.newInstance();
					nimsAdp.getData(header,null, row.getCell(cellIdx) == null ? "" : row.getCell(cellIdx).getStringCellValue());

				}
			} catch (ClassNotFoundException e) {
				logger.debug("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"] cellIdx : " + cellIdx, e);
				throw new NimsException("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"] cellIdx : " + cellIdx, e);
			} catch (NimsException e) {
				throw e;
			} catch (IllegalStateException e) {
				logger.debug("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"] cellIdx : " + cellIdx, e);
				throw new NimsException("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"] cellIdx : " + cellIdx, e);
			}
			
		}
		/////////////////////////////////////////////////////////////////////////////////

		/// 라인셋팅
		List<XlsMapping> lMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_LINE);
		
		XSSFSheet sheet2 = workBook.getSheetAt(1);
		int rowCnt2 = sheet2.getPhysicalNumberOfRows();
		
		
		Lines lines = new Lines();
		
		logger.debug("라인처리 시작");
		for (int i=1; i < rowCnt2; i++){
			Line line = new Line();
			XSSFRow row2 = sheet2.getRow(i);
			
			logger.debug(i + "번째 라인처리");
			
			if (StringUtils.isEmpty(row2.getCell(0).getStringCellValue())) break;
			
			for (XlsMapping info : lMappingList){

				int cellIdx = info.getColIdx();
				
				if (info.getRefType().equalsIgnoreCase("N")) {
					BeanUtils.setProperty(line, info.getFieldNm(), row2.getCell(cellIdx) == null ? "" : row2.getCell(cellIdx).getStringCellValue());
				}else if (info.getRefType().equalsIgnoreCase("D")) {
					
					if (info.getViewYn().equalsIgnoreCase("Y"))
						BeanUtils.setProperty(line, info.getFieldNm(), row2.getCell(cellIdx) == null ? "" : row2.getCell(cellIdx).getStringCellValue());
					else
						BeanUtils.setProperty(line, info.getFieldNm(),info.getRefDefault());
					
				}else if (info.getRefType().equalsIgnoreCase("A")) {
					Class adtCls = null;
					try {
						adtCls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + info.getRefDefault());
						NimsAdpInf nimsAdp = (NimsAdpInf) adtCls.newInstance();
						nimsAdp.getData(header, line, row.getCell(cellIdx) == null ? "" : row.getCell(cellIdx).getStringCellValue());
					} catch (NimsException e) {
						throw e;
					} catch (Exception e) {
						throw new NimsException("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"]", e);
					}
				}
			}
	
			lines.getLINE().add(line);
		}
		
		header.setLINES(lines);
		// 기초정보 셋팅
		header.setRNDDTLRPTCNT(String.valueOf(lines.getLINE().size())); // 수불상세보고수
		
		// header 마지막 참조처리
		logger.debug("헤더 마지막 참조처리 시작");
		for (XlsMapping info : hMappingList){
			if (info.getRefType().equalsIgnoreCase("L")) {
				Class adtCls = null;
				try {
					adtCls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + info.getRefDefault());
					NimsAdpInf nimsAdp = (NimsAdpInf) adtCls.newInstance();
					nimsAdp.getData(header, null, null);
				} catch (ClassNotFoundException e) {
					throw new NimsException("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"]", e);
				} catch (NimsException e) {
					throw e;
				}
			}
		}
		
		// line 마지막 참조처리
		logger.debug("라인 마지막 참조처리 시작");
		List<Line> lineList = lines.getLINE();
		int lineCnt = 0;
		for (Line line : lineList) {
			lineCnt++;
			for (XlsMapping info : lMappingList){
				if (info.getRefType().equalsIgnoreCase("L")) {
					Class adtCls = null;
					try {
						adtCls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + info.getRefDefault());
						NimsAdpInf nimsAdp = (NimsAdpInf) adtCls.newInstance();
						nimsAdp.getData(header, line, String.valueOf(lineCnt));
					} catch (ClassNotFoundException e) {
						throw new NimsException("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"]", e);
					} catch (NimsException e) {
						throw e;
					}
				}
			}
		}
		
		rset.setUID(bsshInfo.getAuthKey()); // 보고자식별ID		
		rset.getHEADER().add(header);
		regist.setREPORTSET(rset);
		
		
		logger.debug("최종결과 리턴");
		return (T) regist;
	}
}
