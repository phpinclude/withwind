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
import java.util.Set;
import java.util.TreeSet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import kr.pe.withwind.common.TreeCode;
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
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.ExlRead;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.nims.xml.SlmRegist;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DateUtils;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class SeqCallController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	// 검색 파라메터용
	@FXML
	private ComboBox<String> cbSearchRange; // 조회범위
	@FXML
	private TextField tfParamP; // 판매업체명
	@FXML
	private TextField tfParamT; // 판매업체취급자식별번호
	
	@FXML
	private ComboBox<String> cbTargetSys; // 대상시스템
	
	@FXML
	private TableView<JSONObject> mainTable;
	
	@FXML
	private Button btSearch;
	
	@FXML
    private ProgressBar pgBar;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		btSearch.setOnKeyPressed(searchHandler);
		btSearch.setOnMouseClicked(searchHandlerM);
		
		cbSearchRange.getItems().addAll("제조번호","일련번호","바코드/RFID");
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
	
	@Override
	public void handleSearch(Object source) {
		
		if (!(source instanceof Node)) return;
		
		if (mainApp.checkTaskRun()) return;
		
		Task<Void> task = new Task<Void>() {
		    public Void call() throws Exception {
		    	mainApp.setTaskRun(true);
		    	doSearch();
		    	mainApp.setTaskRun(false);
		    	updateProgress(0, 0);
				return null;
		    }
		};
		
		pgBar.progressProperty().bind(task.progressProperty());
		
		Thread thread = new Thread(task);
		thread.start();
	}
	
	private void doSearch() {
		boolean isTest = cbTargetSys.getSelectionModel().getSelectedIndex() == 0 ? true : false;
		
		String urlParam = "pg=1";
		urlParam += "&fg=" + (cbSearchRange.getSelectionModel().getSelectedIndex() + 1);
		urlParam += "&p=" + tfParamP.getText();
		urlParam += "&t=" + tfParamT.getText();

		try{
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = mainClass.doTaskExcute(CALL_TYPE.SEQ_INFO,urlParam);
			
			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !result.isEmpty()) setTableColumn(result.get(0));
				mainTable.setItems(FXCollections.observableArrayList(result));
				DialogManager.showDialog("알림", null, "총 "+result.size()+"건의 데이터가 조회되었습니다.");
			});
			
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "일련번호 조회 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	@Override
	public void handleRegist(Object source) {
		
	}
}