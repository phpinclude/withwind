package kr.pe.withwind.nims.view.layout.rpt;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.Constants;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.barcode.ReadForComPort;
import kr.pe.withwind.nims.barcode.ReadForComPort.BarcodeListener;
import kr.pe.withwind.nims.customnode.NodeBindMap;
import kr.pe.withwind.nims.domain.BarcodeInfo;
import kr.pe.withwind.nims.domain.BsshSetting;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.IncomByPurchase;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.domain.ResultErrInfo;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.domain.openapi.PlaceList;
import kr.pe.withwind.nims.utils.APIConstants;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.MakeXml;
import kr.pe.withwind.nims.utils.MultiPartUpload;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.BindNodeEventHandler;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.NimsBindNodeFactory;
import kr.pe.withwind.nims.view.NimsPopupFactory;
import kr.pe.withwind.nims.view.PopupController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.nims.xls.XlsVoMappingManager;
import kr.pe.withwind.nims.xls.adapter.NimsAdpInf;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.nims.xml.Lines;
import kr.pe.withwind.nims.xml.PcmRegist;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.nims.xml.ReportSet;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DateUtils;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class RptModFormController extends CommonController implements BindNodeEventHandler, BarcodeListener{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
	private GridPane gpHeaderInput;
	
	@FXML
	private GridPane gpLineInput;
	
	@FXML
	private Button btClose;
	
	@FXML
    private Button btLineAdd;

    @FXML
    private Button btLineDel;
    
    @FXML
    private Button btLineChange;
    
    @FXML
    private Button btRegist;
    
    @FXML
    private Button btGetData;
	
	@FXML
	private TableView<Line> mainTable;
	
	private NodeBindMap headerMap;
	private NodeBindMap lineMap;
	private NodeBindMap defaultLineMap;
	
	private List<TreeCode> myStorgeInfoList;
	
	private BsshSetting bsshInfo;
	
	private String rptSeCd;
	private String rptTyCd = "2";
	
	private boolean isPmmReturn;
    private boolean isPdmIn;
    private boolean isAarNot;
    private boolean isMtmIn;
    
    private List<XlsMapping> tableMappingList;
    
    private ReadForComPort codeReader;
    
    private String oppUsrRptIdNo; // 상대업체판매보고
    private String oppHdrDe;// 상대업체 보고 기준일자
	
	@FXML
	public void initialize() {
		headerMap = new NodeBindMap();
		lineMap = new NodeBindMap();
		
		// 환경셋팅 기초정보를 가져온다.
		try {
			CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
			bsshInfo = BsshInfoManager.getInstance().getBsshInfo(isTestCode.getCodeNm().equals("Y") ? TYPE.TEST : TYPE.REAL);
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e,"오류", null, "기초정보 불러오기 오류");
		}
		
		try {
			myStorgeInfoList = ApiCall.storgeSearch(bsshInfo.getBsshCd());
		} catch (NimsException e) {
			DialogManager.showExceptionDialog(e,"오류", null, "업체 저장소 정보 가져오기 실패");
		}
		
		btClose.setOnKeyPressed(closeHandler);
		btClose.setOnMouseClicked(closeHandlerM);
		
		btLineAdd.setOnKeyPressed(registHandler);
		btLineAdd.setOnMouseClicked(registHandlerM);
		btLineDel.setOnKeyPressed(registHandler);
		btLineDel.setOnMouseClicked(registHandlerM);
		btLineChange.setOnKeyPressed(registHandler);
		btLineChange.setOnMouseClicked(registHandlerM);
		btRegist.setOnKeyPressed(registHandler);
		btRegist.setOnMouseClicked(registHandlerM);
		
		btGetData.setOnKeyPressed(searchHandler);
		btGetData.setOnMouseClicked(searchHandlerM);
		
		EventHandler<MouseEvent> eh = event -> {
			if (event.getClickCount() != 1) return;
			Line line = mainTable.getSelectionModel().getSelectedItem();
			
			Object[] keys = lineMap.keySet().toArray();
			
			for(Object key : keys) {
				try {
					lineMap.setValue((String) key, BeanUtils.getProperty(line, (String) key));
				}catch(Exception e) {}
			}
		};
		
		mainTable.setOnMouseClicked(eh);
		
		try {
			codeReader = ReadForComPort.getInstance();
			codeReader.removeAllListener();
			codeReader.addBarcodeListener(this);
		}catch(Exception e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setData(String rptSeCd, String fileName) {

		this.rptSeCd = rptSeCd;
		
		if (fileName == null) return;

		// 데이터 가져오기 버튼 셋팅
		if ("PCM".equals(rptSeCd)) {
			btGetData.setText("판매처 데이터 가져오기");
			btGetData.setVisible(true);
		}
		
		// 화면을 그린다.
		// 보고 해더 정보를 이용하여 입력 박스를 만든다.
		XlsVoMappingManager xlsMapManager = XlsVoMappingManager.getInstance();		
		List<XlsMapping> hMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_HEADER);
		
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(18);
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(32);
	    ColumnConstraints column3 = new ColumnConstraints();
	    column3.setPercentWidth(18);
	    ColumnConstraints column4 = new ColumnConstraints();
	    column4.setPercentWidth(32);
	    gpHeaderInput.getColumnConstraints().addAll(column1,column2,column3,column4);
    
	    NimsBindNodeFactory nsnf = new NimsBindNodeFactory(this);
	    
	    int i=0;
		for (XlsMapping mapInfo : hMappingList) {
			
			if (mapInfo.getViewYn().equalsIgnoreCase("N")) continue;
			
			String fieldNm = mapInfo.getFieldNm();
			
			// 신규보고에 대한 불필요한 항목 제거
			if ("REFUSRRPTIDNO".equals(fieldNm)) {
				headerMap.put("REFUSRRPTIDNO", "");
				continue; // 참조사용자보고식별번호
			}
			if ("RPTTYCD".equals(fieldNm)) {
				headerMap.put("RPTTYCD", rptTyCd);
				continue; // 보고유형코드 (무조건변경 '2')
			}
			
			String label = mapInfo.getXlsLabel();
			gpHeaderInput.add(new Label(label), (i % 2) * 2, i / 2);
			
			Node headerNode = null;
			
			// 초기값 셋팅
			if ("CHRGNM".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(headerMap,fieldNm,bsshInfo.getChrgNm());
			}else if ("MDCPATSECD".equals(fieldNm) || "MDCPATIDNOTYCD".equals(fieldNm)
					|| "MDCLCNSASORTCD".equals(fieldNm) || "MDCANIKINDSECD".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(headerMap, fieldNm, getCodeForRpt(fieldNm));
			}else if ("CHRGTELNO".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(headerMap,fieldNm,bsshInfo.getChrgTelNo());
			}else if ("CHRGMPNO".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(headerMap,fieldNm,bsshInfo.getChrgMpNo());
			}else if ("REGISTERID".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(headerMap,fieldNm,bsshInfo.getRegisterId());
			}else if ("MDCANICNT".equals(fieldNm)){
				headerNode = nsnf.getBindNode(headerMap,fieldNm,"0");
			}else {
				headerNode = nsnf.getBindNode(headerMap,fieldNm);
			}
			gpHeaderInput.add(headerNode, (i % 2) * 2 + 1, i / 2);

			logger.debug(label+"["+mapInfo.getFieldNm()+"] --> " + mapInfo.getRefDefault());
			i++;
		}
		
		gpHeaderInput.setHgap(10);
		gpHeaderInput.setVgap(5);
		
		// 보고 라인 정보를 이용하여 입력 박스를 만든다.
		List<XlsMapping> lMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_LINE);
		
		ColumnConstraints columnLine1 = new ColumnConstraints();
		columnLine1.setPercentWidth(12.01);
	    ColumnConstraints columnLine2 = new ColumnConstraints();
	    columnLine2.setPercentWidth(21.33);
	    ColumnConstraints columnLine3 = new ColumnConstraints();
	    columnLine3.setPercentWidth(12.00);
	    ColumnConstraints columnLine4 = new ColumnConstraints();
	    columnLine4.setPercentWidth(21.33);
	    ColumnConstraints columnLine5 = new ColumnConstraints();
		columnLine5.setPercentWidth(12.00);
	    ColumnConstraints columnLine6 = new ColumnConstraints();
	    columnLine6.setPercentWidth(21.33);
	    gpLineInput.getColumnConstraints().addAll(columnLine1,columnLine2,columnLine3,columnLine4,columnLine5,columnLine6);
	    
	    
	    // 상세내용 테이블 셋팅
	 	tableMappingList = new ArrayList<XlsMapping>();

	    i=0;
		for (XlsMapping mapInfo : lMappingList) {
			
			if (mapInfo.getViewYn().equalsIgnoreCase("N")) continue;
			
			String fieldNm = mapInfo.getFieldNm();
			
			if ("MVMNTYCD".equals(fieldNm)) {
				lineMap.setValue(fieldNm, getMVMNTYCD());
				continue;
			}
			
			String label = mapInfo.getXlsLabel();
			gpLineInput.add(new Label(label), (i % 3) * 2, i / 3);
			
			Node headerNode = null;
			
			// 초기값 셋팅
			if ("STORGENO".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(lineMap, fieldNm, myStorgeInfoList);
			}else if ("MVMNTYCD".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(lineMap, fieldNm, getMVMNTYCD());
			}else if ("CHRGMPNO".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(lineMap, fieldNm, bsshInfo.getChrgMpNo());
			}else if ("REGISTERID".equals(fieldNm)) {
				headerNode = nsnf.getBindNode(lineMap, fieldNm, bsshInfo.getRegisterId());
			}else if ("MINDISTBQY".equals(fieldNm)){
				headerNode = nsnf.getBindNode(lineMap, fieldNm, "1");
			}else{
				headerNode = nsnf.getBindNode(lineMap, fieldNm);
			}
			gpLineInput.add(headerNode, (i % 3) * 2 + 1, i / 3);

			logger.debug(label+"["+mapInfo.getFieldNm()+"] --> " + mapInfo.getRefDefault());
			i++;
			
			tableMappingList.add(mapInfo);
		}
		
		gpLineInput.setHgap(10);
		gpLineInput.setVgap(5);
		
		defaultLineMap = (NodeBindMap)lineMap.clone();
		
		// 파일을 열고 해당 내용으로 오브젝트를 만든다.
		try {
			String className = "kr.pe.withwind.nims.xml." + CamelUtil.convert2CamelCase2(rptSeCd + "_REGIST");
			Class cls = getClass().getClassLoader().loadClass(className);
			FileReader fr = new FileReader(Constants.RPT_DIR + fileName);
			Regist regist = (Regist) MakeXml.getInstance().getXml2Obj(fr, cls);
			
			// 오브첵트로 부터 값을 셋팅한다.
			Header prevHeader = regist.getREPORTSET().getHEADER().get(0);

			// 해더 맵에 데이터 넣기
			Object[] keys = headerMap.keySet().toArray();
			for (Object key : keys) {
				String temp = BeanUtils.getProperty(prevHeader, (String)key);
				headerMap.setValue((String) key, temp);
			}
			// 참조사용자보고 식별번호 셋팅
			headerMap.setValue("REFUSRRPTIDNO", prevHeader.getUSRRPTIDNO());
			// 보고 유형 셋팅
			headerMap.setValue("RPTTYCD", rptTyCd);
			
			// 기초데이터 셋팅
			headerMap.setValue("CHRGNM", bsshInfo.getChrgNm());
			headerMap.setValue("CHRGTELNO", bsshInfo.getChrgTelNo());
			headerMap.setValue("CHRGMPNO", bsshInfo.getChrgMpNo());
			headerMap.setValue("REGISTERID", bsshInfo.getRegisterId());
			
			List<Line> lineList = prevHeader.getLINES().getLINE();
			mainTable.setItems(FXCollections.observableArrayList(lineList));
			if (mainTable.getColumns().isEmpty()) setTableColumn(tableMappingList);
			
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
			DialogManager.showExceptionDialog(e, "에러", "보고파일 로드중 에러가 발생했습니다.", "내용을 확인 하세요");
			Platform.runLater(() -> {dialogStage.close();});
		}
	}

	private List<TreeCode> getCodeForRpt(String fieldNm) {
		logger.debug("getCodeForRpt --> " + fieldNm);
		if ("MDCPATSECD".equals(fieldNm)) {
			return TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","PAT_SE_CD");
		}else if ("MDCPATIDNOTYCD".equals(fieldNm)) {
			return TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","ND_97");
		}else if ("MDCLCNSASORTCD".equals(fieldNm)) {
			return TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","M14");
		}else if ("MDCANIKINDSECD".equals(fieldNm)) {
			return TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","ND_107");
		}
		return new ArrayList<TreeCode>();
	}

	@Override
	public void handleSearch(Object source) {
		
		if (source.equals(btGetData)) {
			if ("PCM".equals(rptSeCd)) {
				PopupController controller = NimsPopupFactory.showPurchaseRegist(dialogStage, null);

				PcmRegist pcmRegist = controller.getData();
				
				if (pcmRegist == null || pcmRegist.getREPORTSET().getHEADER() == null || pcmRegist.getREPORTSET().getHEADER().size() < 1){
					return;
				}
				
				// 화면에 정보를 셋팅한다.
				Header header = pcmRegist.getREPORTSET().getHEADER().get(0);
				
				oppUsrRptIdNo = header.getUSRRPTIDNO();
				oppHdrDe = header.getHDRDE();
				
				headerMap.setValue("OPPBSSHNM", header.getOPPBSSHNM());
				headerMap.setValue("OPPBSSHCD", header.getOPPBSSHCD());
				headerMap.setValue("OPPSTORGENO", header.getOPPSTORGENO());
				
				List<Line> lineList = pcmRegist.getREPORTSET().getHEADER().get(0).getLINES().getLINE();
				mainTable.setItems(FXCollections.observableArrayList(lineList));
				if (mainTable.getColumns().isEmpty()) setTableColumn(tableMappingList);
				
				// 팝업띄워서 바로 보고 할것인지 물어보기
				Optional<ButtonType> result = DialogManager.showConfirm("확인", null, "연계보고 진행하시겠습니까?");
				
				if (result.get() == ButtonType.OK){
					handleRegist(btRegist);
				}
			}
		}
	}

	@Override
	public void handleRegist(Object source) {
		if (source.equals(btLineAdd)) {
			Object[] keys = lineMap.keySet().toArray();
			
			Line line = new Line();
			for (Object key : keys) {
				String fieldNm = (String)key;
				try {
					BeanUtils.setProperty(line, fieldNm, lineMap.get(key) == null ? "" : lineMap.get(key));
				}catch(Exception e) {
					logger.error(this.getClass().getSimpleName() + "오류!!", e);
				}
			}
			
			mainTable.getItems().add(line);
			if (mainTable.getColumns().isEmpty()) setTableColumn(tableMappingList);
			
			keys = defaultLineMap.keySet().toArray();
			
			for (Object key : keys) {
				lineMap.setValue((String) key, defaultLineMap.get(key));
			}
			
		}else if (source.equals(btLineDel)) {
			Line line = mainTable.getSelectionModel().getSelectedItem();
			if (line == null) {
				DialogManager.showDialog("알림", null, "삭제하실 라인을 목록에서 선택하세요");
				return;
			}
			
			mainTable.getItems().remove(line);
			
			Object[] keys = defaultLineMap.keySet().toArray();
			
			for (Object key : keys) {
				lineMap.setValue((String) key, defaultLineMap.get(key));
			}
			
		}else if (source.equals(btLineChange)) {
			
			if (mainTable.getSelectionModel().getSelectedIndex() < 0) {
				DialogManager.showDialog("알림", null, "변경하실 라인을 목록에서 선택하세요");
				return;
			}
			
			Object[] keys = lineMap.keySet().toArray();
			
			Line line = new Line();
			for (Object key : keys) {
				String fieldNm = (String)key;
				try {
					BeanUtils.setProperty(line, fieldNm, lineMap.get(key));
				}catch(Exception e) {
					logger.error(this.getClass().getSimpleName() + "오류!!", e);
				}
			}
			
			mainTable.getItems().set(mainTable.getSelectionModel().getSelectedIndex(), line);
		}else if (source.equals(btRegist)) {
			
			try {
				String className = "kr.pe.withwind.nims.xml." + CamelUtil.convert2CamelCase2(rptSeCd + "_REGIST");
				Class cls = getClass().getClassLoader().loadClass(className);
				
				Regist regist = (Regist) getBindMap2Obj(headerMap, mainTable.getItems(), cls);
				
				// UID와 마약류 취급자 식별번호를 셋팅한다.
				String xmlStr1 = MakeXml.getInstance().getObj2Xml(regist);
				
				// 검증시작
				try {
					MakeXml.getInstance().getXml2Obj(new StringReader(xmlStr1), cls);
				}catch(NimsException e) {
					DialogManager.showExceptionDialog(e, "오류", "XML 데이터 검증 에러", "오류내용을 확인하세요.");
					return;
				}
				
				String newXMLFileName = "마약류식별자ID + 보고별약어 + 생성일시 + _ + 업체생성번호 + XML";
				newXMLFileName = bsshInfo.getBsshCd() + rptSeCd + DateUtils.getFormatted(DateUtils.YYYYMMDDHHMISS) + "_" + "0001" + ".xml";
				
				File newXMLFile = new File("rpt_xml/" + newXMLFileName);
				
				FileOutputStream fos = new FileOutputStream(newXMLFile);
				fos.write(xmlStr1.getBytes());
				fos.close();
				
				MultiPartUpload uploader = new MultiPartUpload(bsshInfo.getAuthKey());
				String reString = uploader.sendFile(newXMLFile);
				
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
				    
				    // 상대업체 판매보고 데이터가 있고 성공이면 INCOM_BY_PURCHASE에 INSERT 한다.
				    if (!StringUtils.isEmpty(oppUsrRptIdNo)
				    		&& !StringUtils.isEmpty(oppHdrDe)
				    		&& "0000".equals(uResult.getResultCd())) {
				    	IncomByPurchase incomByPurchase = new IncomByPurchase();
				    	incomByPurchase.setBsshCd(headerMap.get("OPPBSSHCD"));
				    	incomByPurchase.setUsrRptIdNo(oppUsrRptIdNo);
				    	incomByPurchase.setStdDe(oppHdrDe);
				    	
				    	try {
					    	if (DerbyManager.getInstance().update("IncomByPurchase.updateByPk", incomByPurchase) != 1) {
					    		DerbyManager.getInstance().update("IncomByPurchase.insert", incomByPurchase);
					    	}
				    	}catch(Exception e) {
				    		DialogManager.showExceptionDialog(e, "에러", "상대업체 보고정보 업데이트 중 오류", "내용을 확인하세요");
				    	}
				    }
				    
				    if ("0000".equals(uResult.getResultCd())){
				    	DialogManager.showDialog("알림", null, "정상보고 되었습니다.");

				    	try {
				    		// 이전보고의 상태값을 바꾼다.
					    	String prevUsrRptIdNo = regist.getREPORTSET().getHEADER().get(0).getREFUSRRPTIDNO();
					    	String prevBsshCd = regist.getREPORTSET().getHEADER().get(0).getBSSHCD();
					    	
					    	UploadResult param = new UploadResult();
					    	param.setBsshCd(prevBsshCd);
					    	param.setUsrRptIdNo(prevUsrRptIdNo);
					    	param.setStatus(Constants.RPT_STATUS_CHANGE);
					    	logger.debug(param);
				    		DerbyManager.getInstance().update("RptInfo.updateByPk", param);
				    	}catch(DerbyException e) {
				    		DialogManager.showDialog("알림", null, "이전보고 상태변경 실패");
				    	}
				    	
				    }else {
				    	DialogManager.showDialog("알림", null, "보고가 실패되었습니다.");
				    }
				    
				    handleClose(btClose);
				}catch(Exception ex){
					ex.printStackTrace();
					uResult = null;
				}
				
			} catch (Exception e) {
				DialogManager.showExceptionDialog(e, "에러", null, "보고 중 오류가 발생했습니다.");
			}
		}
	}

	@Override
	public void handleClose(Object source) {
		
		Object[] keys = headerMap.keySet().toArray();
		
		logger.debug("Header map data!!");
		
		for (Object key : keys) {
			logger.debug(key + " --> " + headerMap.get(key));
		}
		
		keys = lineMap.keySet().toArray();
		
		logger.debug("Line map data!!");
		
		for (Object key : keys) {
			logger.debug(key + " --> " + lineMap.get(key));
		}
		super.handleClose(source);
	}

	@Override
	public void bindNodeHandle(NodeBindMap bindMap, String fieldNm) {
		if (fieldNm == null) return;
		
		if (fieldNm.equals("OPPBSSHNM")) {
			PopupController controller = NimsPopupFactory.showBsshSearch(dialogStage, bindMap.get(fieldNm));
			
			HashMap<String,String> info = controller.getData();
			
			if (info != null) {
				Object[] keys = info.keySet().toArray();
				
				for (Object key : keys) {
					bindMap.setValue((String)key, info.get(key));
				}
			}
			
		}else if (fieldNm.equals("PRDUCTNM")) {
			PopupController controller = NimsPopupFactory.showProductSearch(dialogStage, bindMap.get(fieldNm));
			
			HashMap<String,String> info = controller.getData();
			
			if (info != null) {
				Object[] keys = info.keySet().toArray();
				
				for (Object key : keys) {
					bindMap.setValue((String)key, info.get(key));
				}
			}
		}else if (fieldNm.equals("MDCDISSCODE")) {
			PopupController controller = NimsPopupFactory.showDissSearch(dialogStage, bindMap.get(fieldNm));
			
			HashMap<String,String> info = controller.getData();
			
			if (info != null) {
				Object[] keys = info.keySet().toArray();
				
				for (Object key : keys) {
					bindMap.setValue((String)key, info.get(key));
				}
			}
		}else if (fieldNm.equals("MDCPATNM")) {
			PopupController controller = NimsPopupFactory.showPatntSearch(dialogStage, bindMap.get(fieldNm));
			
			HashMap<String,String> info = controller.getData();
			
			if (info != null) {
				Object[] keys = info.keySet().toArray();
				
				for (Object key : keys) {
					bindMap.setValue((String)key, info.get(key));
				}
			}
		}
	}
	
	
	public <T> T getBindMap2Obj(NodeBindMap headerMap, List<Line> lineList, Class<T> cls) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalAccessException, InvocationTargetException, InstantiationException, NimsException{
		
		XlsVoMappingManager xlsMapManager = XlsVoMappingManager.getInstance();		
		
		Regist regist = (Regist) cls.newInstance();
		
		List<XlsMapping> hMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_HEADER);
		
		ReportSet rset = new ReportSet();
		Header header = new Header();
		
		// 헤더 기초값 셋팅
		header.setBSSHCD(bsshInfo.getBsshCd()); // 취급자 식별번호
		header.setRPTSECD(rptSeCd); // 보고구분코드
		header.setRPTRNM(bsshInfo.getRptrNm()); // 보고자명
		header.setRPTRENTRPSNM(bsshInfo.getRptrEntrpsNm()); // 보고자업체명
		
		// headerMap에서 해더 정보 셋팅
		Object[] keys = headerMap.keySet().toArray();
		for (Object key : keys) {
			BeanUtils.setProperty(header, (String)key, headerMap.get(key));
		}
		
		//헤더 처리
		//////////////////////////////////////////////////////////////////////////////////
		logger.debug("헤더처리 시작");
		for (XlsMapping info : hMappingList){
			
			if (info.getRefType().equalsIgnoreCase("D") && info.getViewYn().equalsIgnoreCase("N")) {
				BeanUtils.setProperty(header, info.getFieldNm(),info.getRefDefault());
			}else if (info.getRefType().equalsIgnoreCase("A")) {
				Class adtCls = null;
				try {
					adtCls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + info.getRefDefault());
					NimsAdpInf nimsAdp = (NimsAdpInf) adtCls.newInstance();
					nimsAdp.getData(header, null, headerMap.get(info.getFieldNm()) == null ? "" : headerMap.get(info.getFieldNm()));
				} catch (ClassNotFoundException e) {
					throw new NimsException("헤더 어뎁터 클래스 실행 실패 ["+info.getRefDefault()+"]", e);
				} catch (NimsException e) {
					throw e;
				}
			}
		}
		/////////////////////////////////////////////////////////////////////////////////

		/// 라인셋팅
		List<XlsMapping> lMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_LINE);
		
		Lines lines = new Lines();
		
		logger.debug("라인처리 시작");
		for (Line line : lineList){
			
			for (XlsMapping info : lMappingList){
				
				if (info.getRefType().equalsIgnoreCase("D") && info.getViewYn().equalsIgnoreCase("N")) {
					BeanUtils.setProperty(line, info.getFieldNm(),info.getRefDefault());
				}else if (info.getRefType().equalsIgnoreCase("A")) {
					Class adtCls = null;
					try {
						
						String fieldValue = null;
						try {
							BeanUtils.getProperty(line, info.getFieldNm());
						}catch(Exception e) {}
						
						adtCls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + info.getRefDefault());
						NimsAdpInf nimsAdp = (NimsAdpInf) adtCls.newInstance();
						nimsAdp.getData(header, line, fieldValue == null ? "" : fieldValue);
					} catch (NimsException e) {
						logger.error(this.getClass().getSimpleName() + "오류!!", e);
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
		List<Line> finalLineList = lines.getLINE();
		int lineCnt = 0;
		for (Line line : finalLineList) {
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
	
	/**
	 * 보고 구분별 신규유형에 해당하는 이동유형코드를 반환한다. 
	 * @return
	 */
	private String getMVMNTYCD() {
		if ("SLM".equals(rptSeCd)) {
			return "0102"; // 판매보고
		}else if ("PCM".equals(rptSeCd)) {
			return "0201"; // 구매보고
		}else if ("TNT".equals(rptSeCd)) {
			return "0302"; // 양도보고
		}else if ("TNI".equals(rptSeCd)) {
			return "0401"; // 양수보고
		}else if ("EPM".equals(rptSeCd)) {
			return "0502"; // 수출보고
		}else if ("IPM".equals(rptSeCd)) {
			return "0601"; // 수입보고
		}else if ("PMM".equals(rptSeCd)) {
			if (isPmmReturn) return "0705"; // 조제반납입고
			else return "0702"; // 조제보고
		}else if ("MCM".equals(rptSeCd)) {
			return "0802"; // 투약보고
		}else if ("PDM".equals(rptSeCd)) {
			if (isPdmIn) return "0901"; // 제조입고
			else return "0904"; // 제조출고
		}else if ("UEM".equals(rptSeCd)) {
			return "1002"; // 사용보고
		}else if ("AAR".equals(rptSeCd)) {
			if (isAarNot) return "1170"; // 폐기 재고 미차감
			else return "1102"; // 폐기 재고 차감
		}else if ("CNT".equals(rptSeCd)) {
			return "1202"; // 위수탁출고보고
		}else if ("CNI".equals(rptSeCd)) {
			return "1301"; // 위수탁입고보고
		}else if ("ETT".equals(rptSeCd)) {
			return "1402"; // 기타출고보고
		}else if ("ETI".equals(rptSeCd)) {
			return "1501"; // 기타입고보고
		}else if ("MTM".equals(rptSeCd)) {
			if (isMtmIn) return "1705"; // 원료사용입고
			else return "1702"; // 원료사용출고
		}
		
		return null;
	}
	
	/**
	 * 조제반납 셋팅
	 * @param isPmmReturn
	 */
	public void setPmmReturn(boolean isPmmReturn) {
		this.isPmmReturn = isPmmReturn;
	}
	/**
	 * 제조입고 셋팅
	 * @param isPdmIn
	 */
	public void setPdmIn(boolean isPdmIn) {
		this.isPdmIn = isPdmIn;
	}
	/**
	 * 폐기 재고미차감 셋팅
	 * @param isAarNot
	 */
	public void setAarNot(boolean isAarNot) {
		this.isAarNot = isAarNot;
	}
	/**
	 * 원료사용 입고 셋팅
	 * @param isMtmIn
	 */
	public void setMtmIn(boolean isMtmIn) {
		this.isMtmIn = isMtmIn;
	}

	@Override
	public void listenBarcode(BarcodeInfo barcodeInfo) {
		
		ProductInfo productInfo = new ProductInfo();
		
		try {
			productInfo.setProductCd(barcodeInfo.getPrdCode());
			productInfo = DerbyManager.getInstance().listOne("ProductInfo.selectByPk", productInfo);
		} catch (DerbyException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
		barcodeInfo.setPrdNm(productInfo.getProductNm());
		
		setProdInfo(barcodeInfo);
		
		lineMap.setValue("PRDUCTCD", barcodeInfo.getPrdCode());
		lineMap.setValue("PRDUCTNM", productInfo.getProductNm());
		lineMap.setValue("MNFNO", barcodeInfo.getMnfNo());
		lineMap.setValue("MNFSEQ", barcodeInfo.getMnfSeq());
		lineMap.setValue("PRDVALIDDE", barcodeInfo.getPrdValidDe());
		lineMap.setValue("PRDSGTIN", barcodeInfo.getBarcodeStr());

		// linemap에 데이터가 모두 셋팅되어 있으면 라인을 추가해준다.
		boolean isDone = true;
		Object[] keys = lineMap.keySet().toArray();
		
		for (Object key : keys) {
			if (StringUtils.isEmpty(lineMap.get(key))) {
				isDone = false;
				break;
			}
		}
		
		if (isDone) {
			Platform.runLater(()->{
				handleRegist(btLineAdd);}
			);
		}
	}
	
	private void setProdInfo(BarcodeInfo barcodeInfo) {
		try {
			
			CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
			boolean isTest = isTestCode.getCodeNm().equals("Y") ? true : false;
			

			String urlParam = "pg=1&fg=2&p=" + barcodeInfo.getPrdCode();
			urlParam += "&t=" + barcodeInfo.getMnfSeq();

			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall apiCall = new ApiCall(isTest,authKey);
			apiCall.init();
			ArrayList<JSONObject> result = apiCall.doTaskExcute(CALL_TYPE.SEQ_INFO,urlParam);
			
			if (result.size() > 0) {

				String mnfNo = (String)result.get(0).get("MNF_NO");
				String prdValidDe = (String)result.get(0).get("PRD_VALID_DE");
				
				if (!StringUtils.isEmpty(mnfNo)) barcodeInfo.setMnfNo(mnfNo);
				if (!StringUtils.isEmpty(prdValidDe)) barcodeInfo.setPrdValidDe(prdValidDe);
			}
			
		}catch(Exception e) {}
	}
}
