package kr.pe.withwind.nims.view.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.BsshSetting;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.openapi.BsshList;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.MakeXml;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.ExlRead;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.nims.xml.SlmRegist;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DateUtils;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class BsshCallController_backup {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	////////////////////////////
	// 업체검색결과 테이블관련
	@FXML
	private TableView<BsshList> mainTable;
	@FXML
	private TableColumn<BsshList, String> colBsshCd;
	@FXML
	private TableColumn<BsshList, String> colBsshNm;
	@FXML
	private TableColumn<BsshList, String> colIndutyNm;
	@FXML
	private TableColumn<BsshList, String> colHdntCd;
	@FXML
	private TableColumn<BsshList, String> colHdntNm;
	@FXML
	private TableColumn<BsshList, String> colBizrNo;
	@FXML
	private TableColumn<BsshList, String> colTelNo;
	@FXML
	private TableColumn<BsshList, String> colRprsntvNm;
	@FXML
	private TableColumn<BsshList, String> colChrgNm;
	@FXML
	private TableColumn<BsshList, String> colHptlNo;
	
	// 검색 파라메터용
	@FXML
	private TextField tfParamBn;
	@FXML
	private ComboBox<CommonCode> cbSearchRange;
	@FXML
	private TextField tfParamBi;
	@FXML
	private TextField tfParamHp;
	@FXML
	private TextField tfParamBc;
	
	@FXML
	private ComboBox<String> cbTargetSys;
	
	
	private MainApp mainApp;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		cbSearchRange.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","SEARCH_RANGE")));
		cbSearchRange.getSelectionModel().selectFirst();
		cbTargetSys.getItems().addAll("테스트","운영");
		CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
		boolean isTest = isTestCode.getCodeNm().equals("Y") ? true : false;
		if (isTest) cbTargetSys.getSelectionModel().select(0);
		else cbTargetSys.getSelectionModel().select(1);

		mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
		mainTable.setOnKeyPressed(event -> {
			if (keyCodeCopy.match(event)) {
				CommonUtil.copySelectionToClipboard(mainTable);
			}
		});
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void handleBsshSearch() {
		
		boolean isTest = cbTargetSys.getSelectionModel().getSelectedIndex() == 0 ? true : false;
		String paramFg = cbSearchRange.getValue().getCode();
		String paramBn = tfParamBn.getText();
		String paramBi = tfParamBi.getText();
		String paramHp = tfParamHp.getText();
		String paramBc = tfParamBc.getText();
		
		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = null;
			
			result = mainClass.doTaskExcute(CALL_TYPE.BSSH_INFO,"pg=1&fg="+paramFg+"&bn="+paramBn+"&bi="+paramBi+"&hp="+paramHp+"&bc="+paramBc);

			ArrayList<BsshList> bsshList = new ArrayList<BsshList>();

			for (int i=0; i < result.size(); i++){
				BsshList tmp = new BsshList();
				tmp.setBizrno((String) result.get(i).get("BIZRNO"));
				tmp.setBsshCd((String) result.get(i).get("BSSH_CD"));
				tmp.setBsshNm((String) result.get(i).get("BSSH_NM"));
				tmp.setChrgNm((String) result.get(i).get("CHRG_NM"));
				tmp.setHdntCd((String) result.get(i).get("HDNT_CD"));
				tmp.setHdntNm((String) result.get(i).get("HDNT_NM"));
				tmp.setHptlNo((String) result.get(i).get("HPTL_NO"));
				tmp.setIndutyNm((String) result.get(i).get("INDUTY_NM"));
				tmp.setIndutyCd((String) result.get(i).get("INDUTY_CD"));
				tmp.setRprsntvNm((String) result.get(i).get("RPRSNTV_NM"));
				tmp.setTelNo((String) result.get(i).get("TEL_NO"));
				
				bsshList.add(tmp);
			}
			
			mainTable.setItems(FXCollections.observableArrayList(bsshList));
			
			colBizrNo.setCellValueFactory(cellData -> cellData.getValue().bizrnoProperty());
			colBsshCd.setCellValueFactory(cellData -> cellData.getValue().bsshCdProperty());
			colBsshNm.setCellValueFactory(cellData -> cellData.getValue().bsshNmProperty());
			colChrgNm.setCellValueFactory(cellData -> cellData.getValue().chrgNmProperty());
			colHdntCd.setCellValueFactory(cellData -> cellData.getValue().hdntCdProperty());
			colHdntNm.setCellValueFactory(cellData -> cellData.getValue().hdntNmProperty());
			colHptlNo.setCellValueFactory(cellData -> cellData.getValue().hptlNoProperty());
			colIndutyNm.setCellValueFactory(cellData -> cellData.getValue().indutyNmProperty());
			colRprsntvNm.setCellValueFactory(cellData -> cellData.getValue().rprsntvNmProperty());
			colTelNo.setCellValueFactory(cellData -> cellData.getValue().telNoProperty());
			
			DialogManager.showDialog("알림", null, "총 "+result.size()+"건의 데이터가 조회되었습니다.");
			
		}catch(NimsException e) {
			DialogManager.showDialog("알림", null, e.getMessage());
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e, "에러", "업체 찾기 실패", "내용을 확인해주세요.");
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	@FXML
	public void handleRegist() {
		
		logger.debug("handleRegistMyBssh call!!");
		
		if (mainTable.getSelectionModel().getSelectedIndex() < 0) {
			DialogManager.showDialog("알림", null, "선택된 목록이 없습니다.");
			return;
		}
		
		BsshList selectedItem = mainTable.getSelectionModel().getSelectedItem();
		
		try {
			
			BsshList tmp = DerbyManager.getInstance().listOne("BsshList.selectByPk", selectedItem);
			
			if (tmp != null) throw new NimsException("이미등록된 건입니다.");
			
			DerbyManager.getInstance().insert("BsshList.insert", selectedItem);
			
		} catch (DerbyException e) {
			DialogManager.showExceptionDialog(e, "에러", "내거래처로 등록중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		} catch (NimsException e) {
			DialogManager.showDialog("알림", null, e.getMessage());
		}
	}
	
	@FXML
	public void handleRegistAll() {
		List<BsshList> list = mainTable.getItems();
		
		logger.debug("list cnt : " + list.size());
		
		if (list.isEmpty()) {
			DialogManager.showDialog("알림", null, "검색된 목록이 없습니다.");
			return;
		}
		
		try {

			int updateCnt = 0;
			int insertCnt = 0;
			
			for (BsshList item : list) {
				
				int chk = DerbyManager.getInstance().update("BsshList.updateByPk", item);
						
				if (chk > 0) {
					updateCnt += chk;
					continue;
				}
				
				insertCnt += DerbyManager.getInstance().insert("BsshList.insert", item);
			}
			
			DialogManager.showDialog("알림", "등록되었습니다.", "수정 : " + updateCnt + "건, 입력 : " + insertCnt + "건");

		} catch (DerbyException e) {
			DialogManager.showExceptionDialog(e, "에러", "내거래처로 등록중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		}
	}
}