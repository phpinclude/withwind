package kr.pe.withwind.nims.view.layout.rpt;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kr.pe.withwind.nims.domain.ResultErrInfo;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class ErrorController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
    private Label lbBsshCd;

    @FXML
    private Label lbUsrRptIdNo;

    @FXML
    private Label lbResultCd;
    
    @FXML
    private Button btClose;
    
	@FXML
	private TableView<ResultErrInfo> mainTable;
	
	
	private Stage dialogStage;
	private UploadResult ur;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		btClose.setOnKeyPressed(searchHandler);
		btClose.setOnMouseClicked(searchHandlerM);
		
		
		
		mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
		mainTable.setOnKeyPressed(event -> {
			if (keyCodeCopy.match(event)) {
				CommonUtil.copySelectionToClipboard(mainTable);
			}
		});
	}
	
	public void setUploadResult(UploadResult ur) {
		this.ur = ur;
		
		lbBsshCd.setText(ur.getBsshCd());
		lbUsrRptIdNo.setText(ur.getUsrRptIdNo());
		lbResultCd.setText(ur.getResultCd());
		
		try {
			ResultErrInfo param = new ResultErrInfo();
			param.setBsshCd(ur.getBsshCd());
			param.setUsrRptIdNo(ur.getUsrRptIdNo());
			
			logger.debug(param);
			
			List<ResultErrInfo> errList = (List<ResultErrInfo>) DerbyManager.getInstance().list("RptInfo.getRptErrList", param);
			
			for (ResultErrInfo err : errList) logger.debug(err);
			
//			mainTable.setEditable(true);
			if (mainTable.getColumns().isEmpty() && !errList.isEmpty()) setTableColumn("RPT_ERR_INFO");
			mainTable.setItems(FXCollections.observableArrayList(errList));
			
		} catch (DerbyException e) {
			DialogManager.showExceptionDialog(e, "에러", "목록조회중 오류가 발생 했습니다.", "에러내용을 확인 하세요.");
		}
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@Override
	public void handleSearch(Object source) {
		dialogStage.close();
	}

	@Override
	public void handleRegist(Object source) {
		// TODO Auto-generated method stub
	}
}