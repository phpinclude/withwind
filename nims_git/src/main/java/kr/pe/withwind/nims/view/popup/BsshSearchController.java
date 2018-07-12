package kr.pe.withwind.nims.view.popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.openapi.PlaceList;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.PopupController;
import kr.pe.withwind.nims.view.layout.DialogManager;

public class BsshSearchController extends CommonController implements PopupController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
    private TextField tfBsshNm;

    @FXML
    private TextField tfBizRno;

    @FXML
    private TextField tfHptlNo;

    @FXML
    private Button btSearch;

    @FXML
    private Label lbStorgeNo;

    @FXML
    private ComboBox<CommonCode> cbStorgeNm;

    @FXML
    private Button btClose;

    @FXML
    private TableView<JSONObject> mainTable;
    
    @FXML
    private ProgressBar pgBar;
    
	@FXML
	public void initialize() {
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		
		btClose.setOnMouseClicked(closeHandlerM);
		btClose.setOnKeyPressed(closeHandler);
		
		mainTable.setOnMouseClicked(event->{
			JSONObject jObj = mainTable.getSelectionModel().getSelectedItem();
			if (jObj != null) {
				storgeSearch((String) jObj.get("BSSH_CD"));
			}
		});
		
	}
	
	private void storgeSearch(String bsshCd) {

		try{
			List<TreeCode> storgeList = ApiCall.storgeSearch(bsshCd);
			
			cbStorgeNm.setItems(FXCollections.observableArrayList(storgeList));
			cbStorgeNm.getSelectionModel().selectFirst();
			
			lbStorgeNo.disableProperty().setValue(false);
			cbStorgeNm.disableProperty().setValue(false);
			btClose.disableProperty().setValue(false);
			
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "저장소정보 찾기 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	@Override
	public void setSearchText(String searchText) {
		if (StringUtils.isEmpty(searchText)) return;
		tfBsshNm.setText(searchText);
		handleSearch(btSearch);
	}

	@Override
	public <T> T getData() {
		HashMap<String,String> reValue = new HashMap<String,String>();
		JSONObject jObj = mainTable.getSelectionModel().getSelectedItem();
		
		if (jObj != null && cbStorgeNm.getValue() != null) {
			reValue.put("OPPBSSHCD",(String) jObj.get("BSSH_CD"));
			reValue.put("OPPBSSHNM",(String) jObj.get("BSSH_NM"));
			reValue.put("OPPSTORGENO",cbStorgeNm.getValue().getCode());
		}else {
			reValue.put("OPPBSSHCD","");
			reValue.put("OPPBSSHNM","");
			reValue.put("OPPSTORGENO","");
		}
		
		return (T) reValue;
	}

	@Override
	public void handleSearch(Object source) {
		if (!(source instanceof Node)) return;
		
		if (checkTaskRun()) return;
		
		Task<Void> task = new Task<Void>() {
		    public Void call() throws Exception {
		    	setTaskRun(true);
		    	doSearch();
		    	setTaskRun(false);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleClose(Object source) {
		// TODO Auto-generated method stub
		super.handleClose(source);
	}
	
	
	private void doSearch() {
		CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
		boolean isTest = isTestCode.getCodeNm().equals("Y") ? true : false;
		
		String urlParam = "pg=1&fg=1";
		urlParam += "&bn=" + tfBsshNm.getText();
		urlParam += "&bi=" + tfBizRno.getText();
		urlParam += "&hp=" + tfHptlNo.getText();
		
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

	@Override
	public void setData(Object obj) {
		return;
	}
}
