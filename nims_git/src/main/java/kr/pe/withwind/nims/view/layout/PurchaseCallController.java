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

public class PurchaseCallController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	// 검색 파라메터용
	@FXML
	private ComboBox<String> cbSearchRange; // 조회범위
	@FXML
	private TextField tfParamBn; // 판매업체명
	@FXML
	private TextField tfParamBc; // 판매업체취급자식별번호
	
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
		
//		cbSearchRange.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE","SEARCH_RANGE")));
		cbSearchRange.getItems().addAll("전체","특정업체");
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

		if (!StringUtils.isEmpty(tfParamBn.getText()) || !StringUtils.isEmpty(tfParamBc.getText())) cbSearchRange.getSelectionModel().select(1);
			
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
		
		String fgParam = "&ymd=20180531&fg=";
		if (!StringUtils.isEmpty(tfParamBn.getText()) || !StringUtils.isEmpty(tfParamBc.getText())){
			fgParam += "2";
		}else {
			fgParam += "1";
		}
		
		String urlParam = "pg=1";
		
		
		urlParam += fgParam;
		urlParam += "&bn=" + tfParamBn.getText();
		urlParam += "&bc=" + tfParamBc.getText();
		
		logger.debug(urlParam);
		
		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = mainClass.doTaskExcute(CALL_TYPE.PURCHASE_PD_INFO,urlParam);
			
			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !result.isEmpty()) setTableColumn(result.get(0));
				mainTable.setItems(FXCollections.observableArrayList(result));
				DialogManager.showDialog("알림", null, "총 "+result.size()+"건의 데이터가 조회되었습니다.");
			});
			
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "구입대상정보 조회 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	@Override
	public void handleRegist(Object source) {
		
	}
	
	
	
public static void main(String[] args) {
		
		double a;
		String str = "123";
		String str2 = "123 ";
		String str3 = " 123";
		String str4 = "1 23";
	
		if(isNumber(str4))
			System.out.println("true");
		else
			System.out.println("false");
	}
	 public static boolean isNumber(String str) {  // 숫자로 초기화 시킬 수 있는가
	        try {
	        	
	        	Double.parseDouble(str);  //이것으로 하면  (공백)12 를 숫자로 인식하게됨
//	            Integer.parseInt(str);
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }

	    }
}