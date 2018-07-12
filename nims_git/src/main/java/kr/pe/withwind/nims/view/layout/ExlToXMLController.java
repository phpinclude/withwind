package kr.pe.withwind.nims.view.layout;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.Constants;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.BsshSetting;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.ResultErrInfo;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.utils.MakeXml;
import kr.pe.withwind.nims.utils.MultiPartUpload;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.ExlRead;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DateUtils;
import kr.pe.withwind.utils.DerbyManager;

public class ExlToXMLController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
	private TextField tfFileName;
	
	@FXML
	private TextField tfBsshCd;
	
	@FXML
	private TextField tfAuthKey;
	
	@FXML
	private TextField tfRptrNm;
	
	@FXML
	private TextField tfRptrEntrpsNm;
	
	@FXML
	private TextArea taXMLStr;
	
	@FXML
	private ComboBox<CommonCode> cbRptType;
	
	private MainApp mainApp;
	
	private BsshSetting bsshInfo;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		cbRptType.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","RPT_TYPE")));
		cbRptType.getSelectionModel().selectFirst();
		
		try {
			TreeCode code = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE", "IS_TEST");
			bsshInfo = BsshInfoManager.getInstance().getBsshInfo(code.getCodeNm().equalsIgnoreCase("Y") ? TYPE.TEST : TYPE.REAL);
		} catch (NimsException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
		tfBsshCd.setText(bsshInfo.getBsshCd());
		tfAuthKey.setText(bsshInfo.getAuthKey());
		tfRptrNm.setText(bsshInfo.getRptrNm());
		tfRptrEntrpsNm.setText(bsshInfo.getRptrEntrpsNm());
	}
	
	@FXML
	public void handleFindBt() {
		FileChooser fc = new FileChooser();
		File selectedFc = fc.showOpenDialog(mainApp.getPrimaryStage());
		tfFileName.setText(selectedFc.getPath());
	}
	
	@FXML
	public void handleChangeBt() {
		
		if (mainApp.checkTaskRun()) return;
		
		logger.debug("엑셀 to xml 변환 시작");
		
		String exlFileNm = tfFileName.getText();
		String bsshCd = tfBsshCd.getText();
		String authKey = tfAuthKey.getText();
		String rptrNm = tfRptrNm.getText();
		String rptrEntrpsNm = tfRptrEntrpsNm.getText();

		String errMsg = "";
		if (StringUtils.isEmpty(exlFileNm)) 
			errMsg += "엑셀파일을 선택하세요." + System.lineSeparator();
		
		if (StringUtils.isEmpty(bsshCd)) 
			errMsg += "마약류 식별자 ID를 입력하세요." + System.lineSeparator();
		
		if (StringUtils.isEmpty(authKey)) 
			errMsg += "인증키를 입력하세요." + System.lineSeparator();
		
		if (StringUtils.isEmpty(rptrNm))
			errMsg += "보고자명을 입력하세요.";
		
		if (StringUtils.isEmpty(rptrEntrpsNm))
			errMsg += "보고업체명을 입력하세요.";
		
		
		if (!"".equals(errMsg)) {
			DialogManager.showDialog("항목을 확인해주세요", null, errMsg);
			return;
		}

		taXMLStr.setText("처리시작");

		Task<Void> task = new Task<Void>() {
		    public Void call() throws Exception {
		    	mainApp.setTaskRun(true);
		    	doExcute();
		    	mainApp.setTaskRun(false);
				return null;
		    }
		};
		
		Thread thread = new Thread(task);
		thread.start();
		
//		String testXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
//				+ "		  <pmm_result"
//				+ "		 xmlns=\"http://www.nims.or.kr/schema/nims\""
//				+ "		 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
//				+ "		 xsi:schemaLocation=\"http://www.nims.or.kr/schema/nims http://www.nims.or.kr/resources/nims.xsd\">"
//				+ "		 <RESULT_SET>"
//				+ "		   <UID><![CDATA[a8d2575592919820f888bbdf466fe652733a03b328b0761e8630d6315da268b1]]></UID>"
//				+ "		    <RESULT>"
//				+ "		            <BSSH_CD><![CDATA[D00015392]]></BSSH_CD><!-- 마약류취급자식별번호 -->"
//				+ "		            <RPT_SE_CD><![CDATA[PMM]]></RPT_SE_CD><!-- 보고구분코드 -->"
//				+ "		            <USR_RPT_ID_NO><![CDATA[PPM20180515023530_0052]]></USR_RPT_ID_NO><!-- 사용자보고식별번호 -->"
//				+ "		            <RESULT_CD><![CDATA[9999]]></RESULT_CD><!-- 보고결과코드 -->"
//				+ "		            <RESULT_MSG><![CDATA[보고 실패 되었습니다.]]></RESULT_MSG><!-- 보고결과메시지 -->"
//				+ "		            <RPT_RCEPT_NO><![CDATA[]]></RPT_RCEPT_NO><!-- 보고접수번호 -->"
//				+ "		               <ERRORS>"
//				+ "		                    <ERROR>"
//				+ "		                         <ERROR_CD><![CDATA[1301]]></ERROR_CD><!-- Error 코드 -->"
//				+ "		                         <ERROR_MSG><![CDATA[ 'LINES' 요소의 콘텐츠가 불완전합니다. '{\"https://www.nims.or.kr/schema/nims\":LINE}' 중 하나가 필요합니다.]]></ERROR_MSG><!-- Error 메시지 -->"
//				+ "		                    </ERROR>"
//				+ "		                    <ERROR>"
//				+ "		                         <ERROR_CD><![CDATA[1305]]></ERROR_CD><!-- Error 코드 -->"
//				+ "		                         <ERROR_MSG><![CDATA[ 'asfetset' 요소의 콘텐츠가 불완전합니다. '{\"https://www.nims.or.kr/schema/nims\":LINE}' 중 하나가 필요합니다.]]></ERROR_MSG><!-- Error 메시지 -->"
//				+ "		                    </ERROR>"
//				+ "		              </ERRORS>"
//				+ "		   </RESULT>"
//				+ "		  </RESULT_SET>"
//				+ "		</pmm_result>";
		
		
		
	}
	
	private void doExcute() {
		try {
			
			String exlFileNm = tfFileName.getText();
			String bsshCd = tfBsshCd.getText();
			String authKey = tfAuthKey.getText();
			String rptrNm = tfRptrNm.getText();
			String rptrEntrpsNm = tfRptrEntrpsNm.getText();
			
			BsshSetting bsshInfo = new BsshSetting();
			bsshInfo.setAuthKey(authKey);
			bsshInfo.setBsshCd(bsshCd);
			bsshInfo.setRptrEntrpsNm(rptrEntrpsNm);
			bsshInfo.setRptrNm(rptrNm);
			
			String rptType = cbRptType.getValue().getCode();
			String xmlStr1 = "";
			String className = "kr.pe.withwind.nims.xml." + CamelUtil.convert2CamelCase2(rptType + "_REGIST");
			
			File exlFile = new File(tfFileName.getText());
			String filePath = exlFile.isDirectory() ? exlFile.getPath() : exlFile.getParent();
			
			FileInputStream fis = new FileInputStream(exlFile);
			ExlRead er = new ExlRead();
			
			Class cls = getClass().getClassLoader().loadClass(className);
			
			Regist regist = (Regist) er.getExl2Obj(fis, cls, bsshInfo);
			String rptCode = regist.getREPORTSET().getHEADER().get(0).getRPTSECD();
			
			if (!rptType.equals(rptCode)) throw new NimsException("파일내의 보고구분과 선택하신 보고구분이 일치하지 않습니다.");
			
			String rptTyCd = regist.getREPORTSET().getHEADER().get(0).getRPTTYCD();
			
			// UID와 마약류 취급자 식별번호를 셋팅한다.
			xmlStr1 = MakeXml.getInstance().getObj2Xml(regist);
			
			logger.debug(xmlStr1);
			
			/// 검증시작
			MakeXml.getInstance().getXml2Obj(new StringReader(xmlStr1), cls);
			
			String newXMLFileName = "마약류식별자ID + 보고별약어 + 생성일시 + _ + 업체생성번호 + XML";
			newXMLFileName = bsshCd + rptType + DateUtils.getFormatted(DateUtils.YYYYMMDDHHMISS) + "_" + "0001" + ".xml";
			
			File newXMLFile = new File(Constants.RPT_DIR + newXMLFileName);
			
			FileOutputStream fos = new FileOutputStream(newXMLFile);
			fos.write(xmlStr1.getBytes());
			fos.close();
		
			Platform.runLater(()->{taXMLStr.setText(taXMLStr.getText() + System.lineSeparator() + "처리완료" + System.lineSeparator());});
			
			
			MultiPartUpload uploader = new MultiPartUpload(authKey);
			String reString = uploader.sendFile(newXMLFile, "https://test.nims.or.kr/api/upload_s.do");
//			String reString = uploader.sendFile(newXMLFile, "http://172.16.2.108:7003/api/upload_s.do");
			
			
			JSONParser jp = new JSONParser();
			JSONObject rootObj = (JSONObject) jp.parse(reString.toString());
			
			String resultXml = (String) rootObj.get("result");
			
			DocumentBuilderFactory objDocumentBuilderFactory = null;
			DocumentBuilder objDocumentBuilder = null;
			Document doc = null;
			
			UploadResult uResult = new UploadResult();
			
			uResult.setRptFileName(newXMLFileName);
			uResult.setRptTyCd(rptTyCd);
			try{
			    objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			    objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			    doc = objDocumentBuilder.parse(new ByteArrayInputStream(resultXml.getBytes()));
			    
			    uResult.setUid(doc.getElementsByTagName("UID").item(0).getTextContent());
			    uResult.setBsshCd(doc.getElementsByTagName("BSSH_CD").item(0).getTextContent());
			    uResult.setRptSeCd(doc.getElementsByTagName("RPT_SE_CD").item(0).getTextContent());
			    uResult.setResultCd(doc.getElementsByTagName("RESULT_CD").item(0).getTextContent());
			    uResult.setResultMsg(doc.getElementsByTagName("RESULT_MSG").item(0).getTextContent());
			    uResult.setRptRceptNo(doc.getElementsByTagName("RPT_RCEPT_NO").item(0).getTextContent());
			    uResult.setUsrRptIdNo(doc.getElementsByTagName("USR_RPT_ID_NO").item(0).getTextContent());
			    
			    if ("0000".equals(uResult.getResultCd())) {
			    	uResult.setStatus("1");
			    }else {
			    	uResult.setStatus("0");
			    }

			    logger.debug(uResult);
			    DerbyManager.getInstance().insert("RptInfo.insertRptInfo", uResult);
			    
			    NodeList nl = doc.getElementsByTagName("ERROR");
			    for (int i=0; i < nl.getLength();i++) {
			    	NodeList enl = nl.item(i).getChildNodes();
			    	ResultErrInfo rei = new ResultErrInfo();
			    	rei.setIdx(i);
			    	rei.setBsshCd(uResult.getBsshCd());
			    	rei.setUsrRptIdNo(uResult.getUsrRptIdNo());
			    	for (int j=0; j < enl.getLength(); j++) {
			    		if (enl.item(j).getNodeName().equals("ERROR_CD")) {
			    			rei.setErrorCd(enl.item(j).getTextContent());
			    		}else if (enl.item(j).getNodeName().equals("ERROR_MSG")) {
			    			rei.setErrorMsg(enl.item(j).getTextContent());
			    		}
			    	}
			    	uResult.addErrInfoList(rei);
		    		logger.debug(rei);
		    		DerbyManager.getInstance().insert("RptInfo.insertRptErrInfo", rei);
			    }
			    
			    StringBuffer sb = new StringBuffer();
			    sb.append("결과코드 : ").append(uResult.getResultCd()).append(System.lineSeparator());
			    sb.append("메시지 : ").append(uResult.getResultMsg()).append(System.lineSeparator());
			    
			    if (!uResult.getErrInfoList().isEmpty()) {
			    	
			    	for (ResultErrInfo rei: uResult.getErrInfoList()) {
			    		sb.append("에러코드 : ").append(rei.getErrorCd()).append(System.lineSeparator());
			    		sb.append("에러메시지 : ").append(rei.getErrorMsg()).append(System.lineSeparator());
			    	}
			    }
			    
			    sb.append(System.lineSeparator());
			    sb.append(resultXml);
			    
			    Platform.runLater(()->{taXMLStr.setText(taXMLStr.getText() + System.lineSeparator() + sb.toString());});
			    
			}catch(Exception ex){
				ex.printStackTrace();
				uResult = null;
			}
			
		}catch(Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "에러내용을 확인 해주세요.", e.getMessage());});
		}
	}
	

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void handleSearch(Object source) {
		
	}

	@Override
	public void handleRegist(Object source) {
		
	}
}