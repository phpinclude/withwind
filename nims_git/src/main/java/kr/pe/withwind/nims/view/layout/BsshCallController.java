package kr.pe.withwind.nims.view.layout;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.util.Callback;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.openapi.BsshList;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class BsshCallController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	////////////////////////////
	// 업체검색결과 테이블관련
	@FXML
	private TableView<JSONObject> mainTable;
	
	// 검색 파라메터용
	@FXML
	private TextField tfParamBn;
	@FXML
	private ComboBox<String> cbSearchRange;
	@FXML
	private TextField tfParamBi;
	@FXML
	private TextField tfParamHp;
	@FXML
	private TextField tfParamBc;
	
	@FXML
	private Button btSearch;
	@FXML
	private Button btRegist;
	@FXML
	private Button btRegistAll;
	
	@FXML
	private ComboBox<String> cbTargetSys;
	
	@FXML
    private ProgressBar pgBar;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		
		tfParamBn.setOnKeyPressed(searchHandler);
		
//		cbSearchRange.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","SEARCH_RANGE")));
		cbSearchRange.getItems().addAll("전체","내거래처");
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

	@Override
	public void handleRegist(Object source) {
		
		if (!(source instanceof Node)) return;
		
		Node node = (Node)source;
		if (node.getId().equals("btRegist")) {
			regist();
		}else if (node.getId().equals("btRegistAll")) {
			registAll();
		}
	}
	
	private void doSearch() {
		boolean isTest = cbTargetSys.getSelectionModel().getSelectedIndex() == 0 ? true : false;
		
		String urlParam = "pg=1&fg=";
		urlParam += String.valueOf(cbSearchRange.getSelectionModel().getSelectedIndex() + 1);
		urlParam += "&bn=" + tfParamBn.getText();
		urlParam += "&bi=" + tfParamBi.getText();
		urlParam += "&hp=" + tfParamHp.getText();
		urlParam += "&bc=" + tfParamBc.getText();
		
		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = mainClass.doTaskExcute(CALL_TYPE.BSSH_INFO,urlParam);

			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !result.isEmpty()) setTableColumn(result.get(0));
				mainTable.setItems(FXCollections.observableArrayList(result));
				DialogManager.showDialog("알림", null, "총 "+result.size()+"건의 데이터가 조회되었습니다.");
			});
			
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "업체 찾기 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	private void regist() {
		
		if (mainTable.getSelectionModel().getSelectedIndex() < 0) {
			DialogManager.showDialog("알림", null, "선택된 목록이 없습니다.");
			return;
		}

		try {
			
			JSONObject jObj = mainTable.getSelectionModel().getSelectedItem();
			jObj = CamelUtil.toCamel(jObj);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			BsshList selectedItem = mapper.readValue(jObj.toJSONString(), BsshList.class);
			
			BsshList tmp = DerbyManager.getInstance().listOne("BsshList.selectByPk", selectedItem);
			
			if (tmp != null) throw new NimsException("이미등록된 건입니다.");
			
			DerbyManager.getInstance().insert("BsshList.insert", selectedItem);
			
			DialogManager.showDialog("알림", null, "등록되었습니다.");
			
		} catch (DerbyException e) {
			DialogManager.showExceptionDialog(e, "에러", "내거래처로 등록중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		} catch (NimsException e) {
			DialogManager.showDialog("알림", null, e.getMessage());
		} catch (Exception e) {
			DialogManager.showExceptionDialog(e, "에러", "내거래처로 등록중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		}
	}

	public void registAll() {

		List<JSONObject> list = mainTable.getItems();
		
		if (list.isEmpty()) {
			DialogManager.showDialog("알림", null, "검색된 목록이 없습니다.");
			return;
		}
		
		try {

			int updateCnt = 0;
			int insertCnt = 0;
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			for (JSONObject item : list) {
				
				item = CamelUtil.toCamel(item);
				
				BsshList bssh = mapper.readValue(item.toJSONString(), BsshList.class);
				
				int chk = DerbyManager.getInstance().update("BsshList.updateByPk", bssh);
						
				if (chk > 0) {
					updateCnt += chk;
					continue;
				}
				
				insertCnt += DerbyManager.getInstance().insert("BsshList.insert", item);
			}
			
			DialogManager.showDialog("알림", "처리되었습니다.", "수정 : " + updateCnt + "건, 입력 : " + insertCnt + "건");

		} catch (DerbyException e) {
			DialogManager.showExceptionDialog(e, "에러", "내거래처로 등록중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		} catch (Exception e) {
			DialogManager.showExceptionDialog(e, "에러", "내거래처로 등록중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		}
	}
}