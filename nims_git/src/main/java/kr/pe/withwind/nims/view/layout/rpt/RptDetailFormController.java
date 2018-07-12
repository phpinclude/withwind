package kr.pe.withwind.nims.view.layout.rpt;

import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kr.pe.withwind.nims.Constants;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.utils.APIConstants;
import kr.pe.withwind.nims.utils.MakeXml;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.nims.xls.XlsVoMappingManager;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class RptDetailFormController extends CommonController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
	private GridPane gpInput;
	
	@FXML
	private Button btClose;
	
	@FXML
	private TableView<Line> mainTable;
	
	@FXML
	private Button btReRpt;
	
	private Header header;
	
	private UploadResult ur;
	
	private boolean btClicked;
	
	@FXML
	public void initialize() {
		btClose.setOnKeyPressed(closeHandler);
		btClose.setOnMouseClicked(closeHandlerM);
		
		btReRpt.setOnKeyPressed(closeHandler);
		btReRpt.setOnMouseClicked(closeHandlerM);
	}
	
	@SuppressWarnings("unchecked")
	public void setUploadResult(UploadResult ur) {
		
		this.ur = ur;
		
		if(ur.getStatus().equals(Constants.RPT_STATUS_FAIL)) btReRpt.setVisible(true);
		
		String fileName = ur.getRptFileName();
		
		// 파일을 열고 해당 내용으로 오브젝트를 만든다.
		try {
			String rptSeCd = fileName.substring(9, 12);
			logger.debug(rptSeCd);
			String className = "kr.pe.withwind.nims.xml." + CamelUtil.convert2CamelCase2(rptSeCd + "_REGIST");
			Class cls = getClass().getClassLoader().loadClass(className);
			FileReader fr = new FileReader(Constants.RPT_DIR + fileName);
			
			Regist regist = (Regist) MakeXml.getInstance().getXml2ObjNoCheck(fr, cls);
			
			// 해당보고의 헤더정보를 모두 가져온다.
			XlsVoMappingManager xlsMapManager = XlsVoMappingManager.getInstance();		
			List<XlsMapping> hMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_HEADER);
			
			header = regist.getREPORTSET().getHEADER().get(0);
			
			ColumnConstraints column1 = new ColumnConstraints();
		    column1.setPercentWidth(15);
		    ColumnConstraints column2 = new ColumnConstraints();
		    column2.setPercentWidth(35);
		    ColumnConstraints column3 = new ColumnConstraints();
		    column3.setPercentWidth(15);
		    ColumnConstraints column4 = new ColumnConstraints();
		    column4.setPercentWidth(35);
		    gpInput.getColumnConstraints().addAll(column1,column2,column3,column4);
		     
			int i=0;
			
			for (XlsMapping mapInfo : hMappingList) {
				String label = mapInfo.getXlsLabel();
				String value = String.valueOf(BeanUtils.getProperty(header, mapInfo.getFieldNm()));
				
				if ("AdpChrgMpEncode".equals(mapInfo.getRefDefault()) ||
						"AdpPatIdNoEncode".equals(mapInfo.getRefDefault()) ) {
					value = "****(암호화됨)";
				}
				
				TextField tfTemp = new TextField(value);
				tfTemp.setEditable(false);
				
				gpInput.add(new Label(label), (i % 2) * 2, i / 2);
				gpInput.add(tfTemp, (i % 2) * 2 + 1, i / 2);
				
				logger.debug(label+"["+mapInfo.getFieldNm()+"] --> " + value + " / " + mapInfo.getRefDefault());
				i++;
			}
			
			gpInput.setHgap(5);
			gpInput.setVgap(5);
			
			//상세보고내용 출력하기
			List<XlsMapping> lMappingList = xlsMapManager.getMappingList(rptSeCd, APIConstants.TYPE_LINE);
			List<Line> lines = header.getLINES().getLINE();
			
			if (mainTable.getColumns().isEmpty() && !lines.isEmpty()) setTableColumn(lMappingList);
			mainTable.setItems(FXCollections.observableArrayList(lines));
			
		} catch (Exception e) {
			
			DialogManager.showExceptionDialog(e, "에러", "보고파일 로드중 에러가 발생했습니다.", "내용을 확인 하세요");
			Platform.runLater(() -> {dialogStage.close();});
		}
	}

	@Override
	public void handleSearch(Object source) {
	}

	@Override
	public void handleRegist(Object source) {
	}

	@Override
	public void handleClose(Object source) {
		
		if (source.equals(btReRpt)) {
			
			// 해당 보고가 신규 보고이면 무조건 재보고 가능
			if (ur.getRptTyCd().equals("0")) {
				btClicked = true;
			}else if (reRptAvailable()) {
				btClicked = true;
			}else {
				DialogManager.showDialog("알림", "해당보고는 재보고 할수 없습니다.", "원보고취소 또는 변경되었습니다.");
			}
		}
		
		super.handleClose(source);
	}
	
	/**
	 * 참조 보고 번호가 없으면 신규 가능, 참조 보고가 있으면 해당 참조 보고 번호의 상태가 정상인지 확인
	 * @return
	 */
	private boolean reRptAvailable() {
		String orgRptIdNo = header.getREFUSRRPTIDNO();
		if (orgRptIdNo == null || StringUtils.isEmpty(orgRptIdNo)) return true;
		
		String bsshCd = header.getBSSHCD();
		try {
			
			UploadResult param = new UploadResult();
			param.setBsshCd(bsshCd);
			param.setUsrRptIdNo(orgRptIdNo);
			
			param = DerbyManager.getInstance().listOne("RptInfo.getRptByPk", param);
			
			if (Constants.RPT_STATUS_SUCCESS.equals(param.getStatus())) return true;
			
		}catch(Exception e) {}
		
		return false;
	}

	public UploadResult getData() {
		if (!btClicked) return null;
		return ur;
	}
}
