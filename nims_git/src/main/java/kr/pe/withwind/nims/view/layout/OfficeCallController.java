package kr.pe.withwind.nims.view.layout;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;

public class OfficeCallController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	// 검색 파라메터용
	@FXML
	private TextField tfParamOnm; // 기관명
	@FXML
	private TextField tfParamOcd; // 기관코드
	@FXML
	private TextField tfParamAdr; // 주소
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
		
		tfParamOnm.setOnKeyPressed(searchHandler);
		tfParamOcd.setOnKeyPressed(searchHandler);
		tfParamAdr.setOnKeyPressed(searchHandler);
		
		btSearch.setOnKeyPressed(searchHandler);
		btSearch.setOnMouseClicked(searchHandlerM);

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
		
		if (StringUtils.isEmpty(tfParamOnm.getText())
				&& StringUtils.isEmpty(tfParamOcd.getText())
				&&StringUtils.isEmpty(tfParamAdr.getText())) {
			DialogManager.showDialog("알림", null, "기관명, 기관코드, 주소 중 하나는 값이 있어야 합니다.");
			return;
		}
		
		String urlParam = "pg=1&fg=1";
		urlParam += "&onm=" + tfParamOnm.getText();
		urlParam += "&ocd=" + tfParamOcd.getText();
		urlParam += "&adr=" + tfParamAdr.getText();
		
		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = mainClass.doTaskExcute(CALL_TYPE.OFFICE_INFO,urlParam);
			
			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !result.isEmpty()) setTableColumn(result.get(0));
				mainTable.setItems(FXCollections.observableArrayList(result));
				DialogManager.showDialog("알림", null, "총 "+result.size()+"건의 데이터가 조회되었습니다.");
			});
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "허가관청정보 조회 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	@Override
	public void handleRegist(Object source) {
		return;
	}
}