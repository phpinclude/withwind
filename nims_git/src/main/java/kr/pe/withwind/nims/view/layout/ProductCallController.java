package kr.pe.withwind.nims.view.layout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.barcode.ReadForComPort;
import kr.pe.withwind.nims.barcode.ReadForComPort.BarcodeListener;
import kr.pe.withwind.nims.domain.BarcodeInfo;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.ProgressTask;
import kr.pe.withwind.nims.view.ProgressTask.ProgressTaskCallInf;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class ProductCallController extends CommonController implements BarcodeListener, ProgressTaskCallInf{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	// 검색 파라메터용
	@FXML
	private ComboBox<String> cbSearchRange; // 조회범위
	@FXML
	private ComboBox<String> cbPrtmSe; // 중점일반구분
	@FXML
	private TextField tfParamP; // 제품명코드
	@FXML
	private TextField tfParamPn; // 제품명
	
	@FXML
    private Button btSearch; // 찾기버튼
	
	@FXML
	private ComboBox<String> cbTargetSys;
	
	@FXML
	private TableView<JSONObject> mainTable;
	
	private ReadForComPort codeReader;
	
	@FXML
	private Button btRegistAll;
	@FXML
	private Button btRegistOne;
	
	@FXML
    private ProgressBar pgBar;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		tfParamPn.setOnKeyPressed(searchHandler);
		tfParamP.setOnKeyPressed(searchHandler);
		
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		btRegistAll.setOnMouseClicked(registHandlerM);
		btRegistAll.setOnKeyPressed(registHandler);
		btRegistOne.setOnMouseClicked(registHandlerM);
		btRegistOne.setOnKeyPressed(registHandler);
		
		cbSearchRange.getItems().addAll("전체품목","내거래품목","청구코드(EDI)조회");
		cbSearchRange.getSelectionModel().selectFirst();
		
		cbPrtmSe.getItems().addAll("전체","중점","일반");
		cbPrtmSe.getSelectionModel().selectFirst();
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
		
		try {
			codeReader = ReadForComPort.getInstance();
			codeReader.removeAllListener();
			codeReader.addBarcodeListener(this);
		}catch(Exception e) {
			DialogManager.showDialog("알림", null, e.getMessage());
		}
	}
	
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
		try{
			boolean isTest = cbTargetSys.getSelectionModel().getSelectedIndex() == 0 ? true : false;
			String urlParam = "pg=1";
			urlParam += "&fg=" + (cbSearchRange.getSelectionModel().getSelectedIndex() + 1);
			
			if (cbPrtmSe.getSelectionModel().getSelectedIndex() != 0) urlParam += "&fg2=" + cbPrtmSe.getSelectionModel().getSelectedIndex();
			if (!StringUtils.isEmpty(tfParamP.getText())) urlParam += "&p=" + tfParamP.getText();
			if (!StringUtils.isEmpty(tfParamPn.getText())) urlParam += "&pn=" + tfParamPn.getText();
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = mainClass.doTaskExcute(CALL_TYPE.PRODUCT_KD_INFO, urlParam);
			
			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !result.isEmpty()) setTableColumn(result.get(0));
				mainTable.setItems(FXCollections.observableArrayList(result));
				DialogManager.showDialog("알림", null, "총 " + mainClass.getTotalCnt() + "건중에서 " + result.size()+"건의 데이터가 조회되었습니다.");
			});

		}catch(NimsException e) {
			Platform.runLater(()->{
				DialogManager.showDialog("알림", null, e.getMessage());
			});
		}catch(Exception e) {
			Platform.runLater(()->{
				DialogManager.showExceptionDialog(e, "에러", "제품 찾기 실패", "내용을 확인해주세요.");
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			});
		}
	}
	
    public void handleRegist(Object source) {
    	if (!(source instanceof Node)) return;
		if (mainApp.checkTaskRun()) return;
		
		Node node = (Node)source;
		
//		if ("btRegistAll".equals(node.getId())) {
			
			ProgressTask<Void> task = new ProgressTask<Void>(this, node.getId());
			
			// progress property binding
			pgBar.progressProperty().bind(task.progressProperty());
			 
			Thread thread = new Thread(task);
			thread.start();

//				
//		}else if ("btRegistOne".equals(node.getId())) {
//			registOne();
//		}
    }
    

	private void registOne(ProgressTask<Void> task) {
		logger.debug("registOne call !!");
		ObservableList<TablePosition> list = mainTable.getSelectionModel().getSelectedCells();
		
		int updateCnt = 0;
		int insertCnt = 0;
		int totCnt = 0;
		for (TablePosition tPosition : list) {
			JSONObject jObj = mainTable.getItems().get(tPosition.getRow());
			
			ProductInfo productInfo = new ProductInfo();
			productInfo.setMinQty(String.valueOf(jObj.get("PRD_MIN_DISTB_QY")));
			productInfo.setMinQtyNm(String.valueOf(jObj.get("STD_PACKNG_STLE_NM")));
			productInfo.setNrcdSeNm(String.valueOf(jObj.get("NRCD_SE_NM")));
			productInfo.setPceCnt(String.valueOf(jObj.get("PRD_TOT_PCE_QY")));
			productInfo.setPceNm(String.valueOf(jObj.get("PCE_CO_UNIT_NM")));
			productInfo.setProductCd(String.valueOf(jObj.get("PRDUCT_CD")));
			productInfo.setProductMstCd(String.valueOf(jObj.get("PRDLST_MST_CD")));
			productInfo.setProductNm(String.valueOf(jObj.get("PRDUCT_NM")));
			productInfo.setPrtmSeNm(String.valueOf(jObj.get("PRTM_SE_NM")));
			
			try {
				if (DerbyManager.getInstance().update("ProductInfo.updateByPk", productInfo) > 0) {
					updateCnt++;
				}else {
					insertCnt += DerbyManager.getInstance().insert("ProductInfo.insert", productInfo);
				}
			}catch(DerbyException e) {
				logger.debug("제품정보 업데이트 오류");
			}
			
			totCnt++;
			task.progressUpdate(totCnt, list.size());
		}
		
		if (list.isEmpty()) task.progressUpdate(0,0);
		
		String msg = "Insert : " + insertCnt + "건" + System.lineSeparator() + "Update : " + updateCnt + "건";
		Platform.runLater(()->{DialogManager.showDialog("알림", null, msg);});
	}

	private void registAll(ProgressTask<Void> task) {
		
		logger.debug("registAll call !!");
		
		ObservableList<JSONObject> list = mainTable.getItems();
		
		int updateCnt = 0;
		int insertCnt = 0;
		int totCnt = 0;
		for (JSONObject jObj : list) {

			ProductInfo productInfo = new ProductInfo();
			productInfo.setMinQty(String.valueOf(jObj.get("PRD_MIN_DISTB_QY")));
			productInfo.setMinQtyNm(String.valueOf(jObj.get("STD_PACKNG_STLE_NM")));
			productInfo.setNrcdSeNm(String.valueOf(jObj.get("NRCD_SE_NM")));
			productInfo.setPceCnt(String.valueOf(jObj.get("PRD_TOT_PCE_QY")));
			productInfo.setPceNm(String.valueOf(jObj.get("PCE_CO_UNIT_NM")));
			productInfo.setProductCd(String.valueOf(jObj.get("PRDUCT_CD")));
			productInfo.setProductMstCd(String.valueOf(jObj.get("PRDLST_MST_CD")));
			productInfo.setProductNm(String.valueOf(jObj.get("PRDUCT_NM")));
			productInfo.setPrtmSeNm(String.valueOf(jObj.get("PRTM_SE_NM")));
			
			try {
				if (DerbyManager.getInstance().update("ProductInfo.updateByPk", productInfo) > 0) {
					updateCnt++;
				}else {
					insertCnt += DerbyManager.getInstance().insert("ProductInfo.insert", productInfo);
				}
			}catch(DerbyException e) {
				logger.debug("제품정보 업데이트 오류");
			}
			
			totCnt++;
			task.progressUpdate(totCnt, list.size());
		}
		
		if (list.isEmpty()) task.progressUpdate(0,0);
		
		String msg = "Insert : " + insertCnt + "건" + System.lineSeparator() + "Update : " + updateCnt + "건";
		Platform.runLater(()->{DialogManager.showDialog("알림", null, msg);});
	}

	@Override
	public void listenBarcode(BarcodeInfo barcodeInfo) {
		tfParamP.setText(barcodeInfo.getPrdCode());
		Platform.runLater(()->{handleSearch(btSearch);});
	}

	@Override
	public Void doProgress(ProgressTask progressTask, String nodeId) {
		
		if ("btRegistAll".equals(nodeId)) {
			registAll(progressTask);
		}else if ("btRegistOne".equals(nodeId)) {
			registOne(progressTask);
		}
		
		return null;
	}
}