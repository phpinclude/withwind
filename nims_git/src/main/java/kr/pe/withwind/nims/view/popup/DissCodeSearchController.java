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
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.DissCode;
import kr.pe.withwind.nims.domain.StockInfo;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.domain.openapi.PlaceList;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.PopupController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class DissCodeSearchController extends CommonController implements PopupController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
    private TextField tfDissNm;

    @FXML
    private TextField tfDissCd;

    @FXML
    private Button btSearch;

    @FXML
    private TableView<DissCode> mainTable;
    
    @FXML
    private ProgressBar pgBar;
    
    @FXML
    private TextField tfDissCdStr;

    @FXML
    private Button btClose;
    
	@FXML
	public void initialize() {
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		
		tfDissNm.setOnKeyPressed(searchHandler);
		tfDissCd.setOnKeyPressed(searchHandler);

		btClose.setOnKeyPressed(closeHandler);
		btClose.setOnMouseClicked(closeHandlerM);
		
		EventHandler<MouseEvent> eh = event -> {
			if (event.getClickCount() != 1) return;
			
			String selectCd = mainTable.getSelectionModel().getSelectedItem().getDissCd();
			String dissCdStr = tfDissCdStr.getText();
			
			if (StringUtils.isEmpty(dissCdStr)) {
				tfDissCdStr.setText(selectCd);
			}else {
				if (dissCdStr.contains(selectCd)) {
					dissCdStr = dissCdStr.replaceAll(selectCd, "");
					dissCdStr = dissCdStr.replaceAll("//", "/");
					if (dissCdStr.startsWith("/")) dissCdStr = dissCdStr.substring(1);
					if (dissCdStr.endsWith("/")) dissCdStr = dissCdStr.substring(0,dissCdStr.length()-1);
					tfDissCdStr.setText(dissCdStr);
				}else {
					tfDissCdStr.setText(dissCdStr + "/" + selectCd);
				}
			}
		};
		
		mainTable.setOnMouseClicked(eh);
	}
	
	@Override
	public void setSearchText(String searchText) {
		if (StringUtils.isEmpty(searchText)) return;
		tfDissCdStr.setText(searchText);
	}

	@Override
	public <T> T getData() {

		HashMap<String,String> reValue = new HashMap<String,String>();
		reValue.put("MDCDISSCODE",tfDissCdStr.getText());
		
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
	}
	
	private void doSearch() {
		
		DissCode param = new DissCode();
		if (!StringUtils.isEmpty(tfDissNm.getText())) param.setDissNm(tfDissNm.getText());
		if (!StringUtils.isEmpty(tfDissCd.getText())) param.setDissCd(tfDissCd.getText());
		
		try {
			List<DissCode> items = (List<DissCode>) DerbyManager.getInstance().list("DissCode.selectByParam", param);
			
			Platform.runLater(()->{
				logger.debug("setTableColumn call !!");
				if (!items.isEmpty()) {
					setTableColumn("DISS_CODE");
					mainTable.setItems(FXCollections.observableArrayList(items));
					DialogManager.showDialog("알림", null, "총 " + items.size() + "건의 코드가 검색되었습니다.");
				}else {
					DialogManager.showDialog("알림", null, "조회된 제품이 없습니다.");
				}
			});
			
		} catch (DerbyException e) {
			Platform.runLater(()->{
				DialogManager.showExceptionDialog(e, "에러", "질병코드 조회중 오류가 발생했습니다", "아래내용을 확인 하세요");
			});
		}
	}

	@Override
	public void setData(Object obj) {
		return;
	}
}
