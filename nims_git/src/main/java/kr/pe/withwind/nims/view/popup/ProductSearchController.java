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
import javafx.scene.control.CheckBox;
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

public class ProductSearchController extends CommonController implements PopupController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
    private TextField tfProductNm;

    @FXML
    private TextField tfProductCd;

    @FXML
    private Button btSearch;

    @FXML
    private TableView<StockInfo> mainTable;
    
    @FXML
    private ProgressBar pgBar;
    
    @FXML
    private CheckBox chkZero;
    
    private StockInfo stockInfo;
    
	@FXML
	public void initialize() {
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		
		tfProductNm.setOnKeyPressed(searchHandler);
		tfProductCd.setOnKeyPressed(searchHandler);
		
		EventHandler<MouseEvent> eh = event -> {
			if (event.getClickCount() != 2) return;
			stockInfo = mainTable.getSelectionModel().getSelectedItem();
			handleClose(null);
		};
		
		mainTable.setOnMouseClicked(eh);
	}
	
	@Override
	public void setSearchText(String searchText) {
		if (StringUtils.isEmpty(searchText)) return;
		tfProductNm.setText(searchText);
		handleSearch(btSearch);
	}

	@Override
	public <T> T getData() {
		
		if (stockInfo == null) return null;
		
		HashMap<String,String> reValue = new HashMap<String,String>();
		
		reValue.put("STORGENO",stockInfo.getStorageNo());
		reValue.put("PRDUCTCD",stockInfo.getProductCd());
		reValue.put("PRDUCTNM",stockInfo.getProductNm());
		reValue.put("MNFNO",stockInfo.getLotNo());
		reValue.put("PRDVALIDDE",stockInfo.getValidDe());
		reValue.put("PRDSGTIN",stockInfo.getBarcodeStr() == null ? "" : stockInfo.getBarcodeStr());
		reValue.put("MNFSEQ",stockInfo.getSerialNo());
	
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
		
		StockInfo param = new StockInfo();
		if (!StringUtils.isEmpty(tfProductNm.getText())) param.setProductNm(tfProductNm.getText());
		if (!StringUtils.isEmpty(tfProductCd.getText())) param.setProductCd(tfProductCd.getText());
		
		if (chkZero.isSelected()) param.setChkZero("Yes");
		else param.setChkZero("No");
		
		try {
			List<StockInfo> items = (List<StockInfo>) DerbyManager.getInstance().list("StockInfo.selectByParam", param);
			
			Platform.runLater(()->{
				logger.debug("setTableColumn call !!");
				if (!items.isEmpty()) {
					if (mainTable.getColumns().isEmpty()) setTableColumn("STOCK_INFO");
					mainTable.setItems(FXCollections.observableArrayList(items));
					DialogManager.showDialog("알림", null, "총 " + items.size() + "건의 제품이 검색되었습니다." + System.lineSeparator() + "목록에서 더블클릭하시면 적용됩니다.");
				}else {
					DialogManager.showDialog("알림", null, "조회된 제품이 없습니다.");
				}
			});
			
		} catch (DerbyException e) {
			Platform.runLater(()->{
				DialogManager.showExceptionDialog(e, "에러", "제품(제고)정보 조회중 오류가 발생했습니다", "아래내용을 확인 하세요");
			});
		}
	}

	@Override
	public void setData(Object obj) {
		return;
	}
}
