package kr.pe.withwind.nims.view.layout;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.domain.StockInfo;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.ProgressTask;
import kr.pe.withwind.nims.view.ProgressTask.ProgressTaskCallInf;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class StockCallController extends CommonController implements ProgressTaskCallInf{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	// 검색 파라메터용
	@FXML
	private ComboBox<String> cbSearchRange; // 조회범위
	@FXML
	private TextField tfParamP; // 제품코드
	
	@FXML
	private ComboBox<String> cbTargetSys; // 대상시스템
	
	@FXML
	private TableView<JSONObject> mainTable;
	
	@FXML
	private Button btSearch;
	
	@FXML
    private Button btRegistAll;

    @FXML
    private Button btRegistSel;
    
	@FXML
    private ProgressBar pgBar;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		logger.debug("initialize call !! 1");
		
		btSearch.setOnKeyPressed(searchHandler);
		btSearch.setOnMouseClicked(searchHandlerM);
		
		btRegistAll.setOnKeyPressed(registHandler);
		btRegistAll.setOnMouseClicked(registHandlerM);
		btRegistSel.setOnKeyPressed(registHandler);
		btRegistSel.setOnMouseClicked(registHandlerM);
		
		logger.debug("initialize call !! 2");
		
//		cbSearchRange.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","SEARCH_RANGE")));
		cbSearchRange.getItems().addAll("전체","특정제품");
		cbSearchRange.getSelectionModel().selectFirst();
		cbTargetSys.getItems().addAll("테스트","운영");
		
		
		logger.debug("initialize call !! 3");
		
		CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
		boolean isTest = isTestCode.getCodeNm().equals("Y") ? true : false;
		if (isTest) cbTargetSys.getSelectionModel().select(0);
		else cbTargetSys.getSelectionModel().select(1);
		
		
		logger.debug("initialize call !! 4");
		mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		logger.debug("initialize call !! 5");
		
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
		
		String urlParam = "pg=1&fg2=2";
		urlParam += "&fg=" + String.valueOf(cbSearchRange.getSelectionModel().getSelectedIndex() + 1);
		if (!StringUtils.isEmpty(tfParamP.getText())) urlParam += "&p=" + tfParamP.getText();
		
		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = mainClass.doTaskExcute(CALL_TYPE.STOCK_INFO,urlParam);
			
			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !result.isEmpty()) setTableColumn(result.get(0));
				mainTable.setItems(FXCollections.observableArrayList(result));
				DialogManager.showDialog("알림", null, "총 "+result.size()+"건의 데이터가 조회되었습니다.");
			});
			
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "제고정보 조회 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	@Override
	public void handleRegist(Object source) {
		
		if (!(source instanceof Node)) return;
		if (mainApp.checkTaskRun()) return;
		
		Node node = (Node)source;
		ProgressTask<Void> task = new ProgressTask<Void>(this, node.getId());
		pgBar.progressProperty().bind(task.progressProperty());
		Thread thread = new Thread(task);
		thread.start();
	}
	
	private void registAll(ProgressTask<Void> task) {
		
		ObservableList<JSONObject> list = mainTable.getItems();
		
		logger.debug("총 제품 개수 : " + list.size());
		
		int updateCnt = 0;
		int insertCnt = 0;
		int totCnt = 0;
		for (JSONObject jObj : list) {
			try {
				StockInfo stockInfo = new StockInfo();
				stockInfo.setLotNo(String.valueOf(jObj.get("MNF_NO")));
				stockInfo.setPieceCnt(Double.parseDouble(String.valueOf(jObj.get("PCE_INVT_QY"))));
				stockInfo.setProductCd(String.valueOf(jObj.get("PRDUCT_CD")));
				stockInfo.setSerialNo(String.valueOf(jObj.get("MNF_SEQ")));
				stockInfo.setStorageNo(String.valueOf(jObj.get("STORGE_NO")));
				stockInfo.setValidDe(String.valueOf(jObj.get("PRD_VALID_DE")));
				
				logger.debug(stockInfo);
				
				if (DerbyManager.getInstance().update("StockInfo.updateByPk", stockInfo) > 0) {
					updateCnt++;
				}else {
					insertCnt += DerbyManager.getInstance().insert("StockInfo.insert", stockInfo);
				}
				
				totCnt++;
				task.progressUpdate(totCnt, list.size());
				
			}catch(DerbyException e) {
				logger.debug("제고정보 업데이트 오류");
			}catch(Exception e) {
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}
		
		if (list.isEmpty()) task.progressUpdate(0,0);
		
		String msg = "Insert : " + insertCnt + "건" + System.lineSeparator() + "Update : " + updateCnt + "건";
		Platform.runLater(()->{DialogManager.showDialog("알림", null, msg);});
	}
	
	
	private void registSel(ProgressTask<Void> task) {
		
		ObservableList<JSONObject> list = mainTable.getSelectionModel().getSelectedItems();
		
		logger.debug("선택 제품 개수 : " + list.size());
		
		int updateCnt = 0;
		int insertCnt = 0;
		int totCnt = 0;
		for (JSONObject jObj : list) {
			try {
				StockInfo stockInfo = new StockInfo();
				stockInfo.setLotNo(String.valueOf(jObj.get("MNF_NO")));
				stockInfo.setPieceCnt(Double.parseDouble(String.valueOf(jObj.get("PCE_INVT_QY"))));
				stockInfo.setProductCd(String.valueOf(jObj.get("PRDUCT_CD")));
				stockInfo.setSerialNo(String.valueOf(jObj.get("MNF_SEQ")));
				stockInfo.setStorageNo(String.valueOf(jObj.get("STORGE_NO")));
				stockInfo.setValidDe(String.valueOf(jObj.get("PRD_VALID_DE")));
				
				logger.debug(stockInfo);
				
				if (DerbyManager.getInstance().update("StockInfo.updateByPk", stockInfo) > 0) {
					updateCnt++;
				}else {
					insertCnt += DerbyManager.getInstance().insert("StockInfo.insert", stockInfo);
				}
				
				totCnt++;
				task.progressUpdate(totCnt, list.size());
				
			}catch(DerbyException e) {
				logger.debug("제고정보 업데이트 오류");
			}catch(Exception e) {
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}
		
		if (list.isEmpty()) task.progressUpdate(0,0);
		
		String msg = "Insert : " + insertCnt + "건" + System.lineSeparator() + "Update : " + updateCnt + "건";
		Platform.runLater(()->{DialogManager.showDialog("알림", null, msg);});
	}

	@Override
	public Object doProgress(ProgressTask progressTask, String nodeId) {
		if ("btRegistAll".equals(nodeId)) {
			registAll(progressTask);
		}else if ("btRegistSel".equals(nodeId)) {
			registSel(progressTask);
		}
		return null;
	}
}