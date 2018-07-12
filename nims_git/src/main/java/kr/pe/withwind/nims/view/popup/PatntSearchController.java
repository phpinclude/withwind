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
import kr.pe.withwind.nims.domain.PatntInfo;
import kr.pe.withwind.nims.domain.StockInfo;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.domain.openapi.PlaceList;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.NimsPopupFactory;
import kr.pe.withwind.nims.view.PopupController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class PatntSearchController extends CommonController implements PopupController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	@FXML
    private TextField tfPatntNm;

    @FXML
    private TextField tfIdNo;

    @FXML
    private Button btSearch;
    
    @FXML
    private Button btRegist;

    @FXML
    private TableView<PatntInfo> mainTable;
    
    private PatntInfo patntInfo;
    
	@FXML
	public void initialize() {
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		
		btRegist.setOnMouseClicked(registHandlerM);
		btRegist.setOnKeyPressed(registHandler);
		
		tfPatntNm.setOnKeyPressed(searchHandler);
		tfIdNo.setOnKeyPressed(searchHandler);
		
		EventHandler<MouseEvent> eh = event -> {
			if (event.getClickCount() != 2) return;
			patntInfo = mainTable.getSelectionModel().getSelectedItem();
			handleClose(null);
		};
		
		mainTable.setOnMouseClicked(eh);
	}
	
	@Override
	public void setSearchText(String searchText) {
		if (StringUtils.isEmpty(searchText)) return;
		tfPatntNm.setText(searchText);
		handleSearch(btSearch);
	}

	@Override
	public <T> T getData() {
		
		if (patntInfo == null) return null;
		
		HashMap<String,String> reValue = new HashMap<String,String>();
		
		reValue.put("MDCPATNM",patntInfo.getPatntNm());
		reValue.put("MDCPATIDNO",patntInfo.getIdNo());
		reValue.put("MDCPATIDNOTYCD",patntInfo.getIdType());
	
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
		
		Thread thread = new Thread(task);
		thread.start();
	}

	@Override
	public void handleRegist(Object source) {
		if (source.equals(btRegist)) {
			
			PopupController controller = NimsPopupFactory.showPatntRegist(dialogStage, mainTable.getSelectionModel().getSelectedItem());
			
			PatntInfo info = controller.getData();
			logger.debug(info);
			if (info != null) {
				setTableColumn("PATNT_INFO");
				mainTable.getItems().add(info);
				mainTable.getSelectionModel().select(info);
			}
		}
	}
	
	private void doSearch() {
		
		PatntInfo param = new PatntInfo();
		if (!StringUtils.isEmpty(tfPatntNm.getText())) param.setPatntNm(tfPatntNm.getText());
		if (!StringUtils.isEmpty(tfIdNo.getText())) param.setIdNo(tfIdNo.getText());
		
		try {
			List<PatntInfo> items = (List<PatntInfo>) DerbyManager.getInstance().list("PatntInfo.selectByParam", param);
			
			Platform.runLater(()->{
				logger.debug("setTableColumn call !!");
				if (!items.isEmpty()) {
					setTableColumn("PATNT_INFO");
					mainTable.setItems(FXCollections.observableArrayList(items));
					if (items.size() == 1) {
						patntInfo = items.get(0);
						handleClose(null);
					}
				}else {
					DialogManager.showDialog("알림", null, "조회결과가 없습니다.");
				}
			});
			
		} catch (DerbyException e) {
			Platform.runLater(()->{
				DialogManager.showExceptionDialog(e, "에러", "환자명 조회중 오류가 발생했습니다", "아래내용을 확인 하세요");
			});
		}
	}

	@Override
	public void setData(Object obj) {
		return;
	}
}
