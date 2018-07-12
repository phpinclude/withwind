package kr.pe.withwind.nims.view.layout.rpt;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.Constants;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.nims.view.layout.ExlEditDialogController;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class RptListController extends CommonController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	private String rptSeCd;
	private String rptSeCdNm;
	
	@FXML
    private Label lbTitle;

    @FXML
    private DatePicker dpSearchSt;

    @FXML
    private DatePicker dpSearchEn;

    @FXML
    private Button btSearch;

    @FXML
    private Button btDetail;

    @FXML
    private Button btChange;

    @FXML
    private Button btCancel;

    @FXML
    private Button btNew;
    
    @FXML
    private HBox hbNodes;
    
    @FXML
	private TableView<UploadResult> mainTable;
    
	public RptListController() {
	}
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		btSearch.setOnKeyPressed(searchHandler);
		btSearch.setOnMouseClicked(searchHandlerM);
		btDetail.setOnKeyPressed(searchHandler);
		btDetail.setOnMouseClicked(searchHandlerM);
		btChange.setOnKeyPressed(searchHandler);
		btChange.setOnMouseClicked(searchHandlerM);
		btCancel.setOnKeyPressed(searchHandler);
		btCancel.setOnMouseClicked(searchHandlerM);
		btNew.setOnKeyPressed(searchHandler);
		btNew.setOnMouseClicked(searchHandlerM);
		
		dpSearchSt.setValue(LocalDate.now().plusDays(-7l));
		dpSearchEn.setValue(LocalDate.now());
		
		mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
		mainTable.setOnKeyPressed(event -> {
			if (keyCodeCopy.match(event)) {
				CommonUtil.copySelectionToClipboard(mainTable);
			}
		});
		
		EventHandler<MouseEvent> eh = event -> {
			if (event.getClickCount() != 2) return;
			UploadResult ur = mainTable.getSelectionModel().getSelectedItem();
			
			if ("0000".equals(ur.getResultCd())) {
				// 성공이다.
				showDetailView(ur);
			}else {
				// 실패이다.
				showFailView(ur);
			}
		};
		
		mainTable.setOnMouseClicked(eh);
		
		ObservableList<Node> nodes = hbNodes.getChildren();
		for (Node node : nodes) {
			hbNodes.setMargin(node, new Insets(0, 10, 0, 0));
		}
	}
	
	
	@Override
	public void handleSearch(Object source) {

		if (!(source instanceof Node)) return;
		Node node = (Node)source;
		
		if ("btSearch".equals(node.getId())) {
			
			if (dpSearchSt.getValue() == null) {
				DialogManager.showDialog("알림", null, "조회시작일을 선택하세요");
				return;
			}
			
			if (dpSearchEn.getValue() == null) {
				DialogManager.showDialog("알림", null, "조회종료일을 선택하세요");
				return;
			}
			
			Timestamp tsSt = Timestamp.valueOf(dpSearchSt.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")));
			Timestamp tsEn = Timestamp.valueOf(dpSearchEn.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59")));
			
			UploadResult us = new UploadResult();
			us.setSearchSt(tsSt);
			us.setSearchEn(tsEn);
			us.setRptSeCd(rptSeCd);

			try {
				List<UploadResult> list = (List<UploadResult>) DerbyManager.getInstance().list("RptInfo.getRptList", us);
				
				if (list.isEmpty()) {
					
					Platform.runLater(()->{DialogManager.showDialog("알림", null, "조회된 목록이 없습니다.");});
					return;
				}
				
				if (mainTable.getColumns().isEmpty() && !list.isEmpty()) setTableColumn("RPT_LIST");
				mainTable.setItems(FXCollections.observableArrayList(list));
			} catch (DerbyException e) {
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}else if ("btDetail".equals(node.getId())) {
			UploadResult ur = mainTable.getSelectionModel().getSelectedItem();
			
			if (ur == null) {
				DialogManager.showDialog("알림", null, "목록을 선택하세요");
				return;
			}
			
			showDetailView(ur);
			
		}else if ("btChange".equals(node.getId())) {
			UploadResult ur = mainTable.getSelectionModel().getSelectedItem();
			if (ur == null) {
				DialogManager.showDialog("알림", null, "목록을 선택하세요");
				return;
			}
			
			if (!Constants.RPT_STATUS_SUCCESS.equals(ur.getStatus())) {
				DialogManager.showDialog("알림", null, "성공한 보고에 대해서만 변경보고가 가능 합니다.");
				return;
			}
			showModView(ur.getRptFileName());
		}else if ("btCancel".equals(node.getId())) {
			UploadResult ur = mainTable.getSelectionModel().getSelectedItem();
			if (ur == null) {
				DialogManager.showDialog("알림", null, "목록을 선택하세요");
				return;
			}
			
			if (!Constants.RPT_STATUS_SUCCESS.equals(ur.getStatus())) {
				DialogManager.showDialog("알림", null, "성공한 보고에 대해서만 취소보고가 가능 합니다.");
				return;
			}
		}else if ("btNew".equals(node.getId())) {
			showNewView(null);
		}
	}

	public void showNewView(String fileName) {
		logger.debug("showNewView Call !!");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("RptNewFormView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("[" + rptSeCdNm + "] 신규보고");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        RptNewFormController controller = loader.getController();
	        
	        controller.setDialogStage(dialogStage);
	        controller.setData(rptSeCd,fileName);
	        controller.setMainApp(mainApp);
	        
	        dialogStage.showAndWait();
	        
	        handleSearch(btSearch);
	        
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	public void showModView(String fileName) {
		logger.debug("showNewView Call !!");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("RptModFormView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("[" + rptSeCdNm + "] 변경규보고");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        RptModFormController controller = loader.getController();
	        
	        controller.setDialogStage(dialogStage);
	        controller.setData(rptSeCd,fileName);
	        controller.setMainApp(mainApp);
	        
	        dialogStage.showAndWait();
	        
	        handleSearch(btSearch);
	        
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	public void showFailView(UploadResult ur) {
		logger.debug("showFailView Call !!");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ErrorView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("[" + rptSeCdNm + "]오류 상세 내용");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        ErrorController controller = loader.getController();
	        
	        controller.setUploadResult(ur);
	        controller.setDialogStage(dialogStage);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	private void showDetailView(UploadResult ur) {
		logger.debug("showDetailView Call !!");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("RptDetailFormView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("[" + rptSeCdNm + "] 상세 내용");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        RptDetailFormController controller = loader.getController();
	        
	        controller.setDialogStage(dialogStage);
	        controller.setUploadResult(ur);
	        
	        dialogStage.showAndWait();
	        
	        ur = controller.getData();
	        
	        if (ur == null) return;
	        
	        logger.debug(ur);
	        
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	public void setRptSeCd(String rptSeCd) {
		this.rptSeCd = rptSeCd;
		this.rptSeCdNm = TreeCodeManager.getInstance().getCodeInfo("RPT_TYPE", rptSeCd).getCodeNm();
		this.lbTitle.setText(rptSeCdNm + " :: 조회");
		
		handleSearch(btSearch);
	}

	@Override
	public void handleRegist(Object source) {
		// TODO Auto-generated method stub
	}
}