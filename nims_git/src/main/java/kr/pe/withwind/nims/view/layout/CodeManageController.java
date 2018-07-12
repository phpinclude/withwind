package kr.pe.withwind.nims.view.layout;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.customnode.BtnType;
import kr.pe.withwind.nims.customnode.ButtonCell.ButtonCellListener;
import kr.pe.withwind.nims.customnode.ButtonCellFactory;
import kr.pe.withwind.nims.customnode.ButtonCellValueFactory;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.CommonUtil;
import kr.pe.withwind.nims.view.MainApp;

public class CodeManageController extends CommonController implements ButtonCellListener{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
		
	@FXML
	private TableView<TreeCode> codeInfoTable;
	@FXML
	private TableColumn<TreeCode, String> pCodeCol;
	@FXML
	private TableColumn<TreeCode, String> codeCol;
	@FXML
	private TableColumn<TreeCode, Integer> childCntCol;
	@FXML
	private TableColumn<TreeCode, String> codeTypeCol;
	@FXML
	private TableColumn<TreeCode, String> codeNmCol;
	@FXML
	private TableColumn<TreeCode, Integer> ordNoCol;
	@FXML
	private TableColumn<TreeCode, String> useYnCol;
	@FXML
	private TableColumn<TreeCode, String> viewYnCol;
	@FXML
	private TableColumn<TreeCode, String> codeDescCol;
	@FXML
	private TableColumn<TreeCode, Boolean> modBtCol;
	@FXML
	private TableColumn<TreeCode, Boolean> delBtCol;
	@FXML
	private TableColumn<TreeCode, Boolean> addChildBtCol;
	
	@FXML
	private TextField tfPCode;
	@FXML
	private TextField tfCode;
	@FXML
	private TextField tfCodeType;
	@FXML
	private TextField tfCodeNm;
	@FXML
	private TextField tfOrdNo;
	@FXML
	private ComboBox<String> cbUseYn;
	@FXML
	private ComboBox<String> cbViewYn;;
	@FXML
	private TextField tfCodeDesc;
	@FXML
	private Button actionBtn;
	
	private ObservableList<TreeCode> codeList;
	
	private MainApp mainApp;
	
	private String currentPCode;
	
	public CodeManageController() {
		cbUseYn = new ComboBox<String>();
	}
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		final KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
		codeInfoTable.setOnKeyPressed(event -> {
			if (keyCodeCopy.match(event)) {
				CommonUtil.copySelectionToClipboard(codeInfoTable);
			}
		});
		
		EventHandler<KeyEvent> eHandle = event -> {if (event.getCode() == KeyCode.ENTER) handleRegist();};
		
//		tfCodeNm.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			@Override
//			public void handle(KeyEvent event) {
//				if (event.getCode() == KeyCode.ENTER) {
//					handleRegist();
//				}
//			}
//		});
		
//		tfCodeNm.setOnKeyPressed(event -> {
//			if (event.getCode() == KeyCode.ENTER) handleRegist();
//		});
		
		tfCodeNm.setOnKeyPressed(eHandle);
		tfCodeDesc.setOnKeyPressed(eHandle);
		
		cbUseYn.getItems().addAll("Y","N");
		cbViewYn.getItems().addAll("Y","N");
		currentPCode = tfPCode.getText();
		settingData();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	private void settingData() {
		codeList = FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList(currentPCode));
        codeInfoTable.setItems(codeList);
        
        int newOrdNo = codeList.get(codeList.size()-1).getOrdNo() + 1;
        
        tfPCode.setText(currentPCode);
		tfCode.setText("");
		tfCodeType.setText("COM");
		tfCodeNm.setText("");
		tfOrdNo.setText(String.valueOf(newOrdNo));
		cbUseYn.getSelectionModel().selectFirst();
		cbViewYn.getSelectionModel().selectFirst();
		tfCodeDesc.setText("");
        
        pCodeCol.setCellValueFactory(cellData -> cellData.getValue().pCodeProperty());
		codeCol.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
		childCntCol.setCellValueFactory(cellData -> cellData.getValue().childCntProperty().asObject());
		codeTypeCol.setCellValueFactory(cellData -> cellData.getValue().codeTypeProperty());
		codeNmCol.setCellValueFactory(cellData -> cellData.getValue().codeNmProperty());
		ordNoCol.setCellValueFactory(cellData -> cellData.getValue().ordNoProperty().asObject());
		useYnCol.setCellValueFactory(cellData -> cellData.getValue().useYnProperty());
		viewYnCol.setCellValueFactory(cellData -> cellData.getValue().viewYnProperty());
		codeDescCol.setCellValueFactory(cellData -> cellData.getValue().codeDescProperty());
		
		modBtCol.setCellValueFactory(new ButtonCellValueFactory<TreeCode>());
		modBtCol.setCellFactory(new ButtonCellFactory<TreeCode>(BtnType.BTN_TYPE_MODIFY,"수정", CodeManageController.this));  
		
		delBtCol.setCellValueFactory(new ButtonCellValueFactory<TreeCode>());
		delBtCol.setCellFactory(new ButtonCellFactory<TreeCode>(BtnType.BTN_TYPE_DELETE,"삭제", CodeManageController.this));  
		
		addChildBtCol.setCellValueFactory(new ButtonCellValueFactory<TreeCode>());
		addChildBtCol.setCellFactory(new ButtonCellFactory<TreeCode>(BtnType.BTN_TYPE_ADD_CHILD,"추가", CodeManageController.this));
	
		List<TableColumn<TreeCode, ?>> columList =  codeInfoTable.getColumns();
		for (TableColumn<TreeCode, ?> node : columList) {
			if (node.getId().equals("codeNmCol")) {
				node.setStyle("-fx-alignment: CENTER-RIGHT;");
			}else{
				node.setStyle("-fx-alignment: CENTER;");
			}
		}
		
		actionBtn.setText("등록");
		
	}
	
	@FXML
	public void handleTableMouse(MouseEvent click) {
		if (click.getClickCount() == 2) {
			
			if (codeInfoTable.getSelectionModel().getSelectedIndex() < 0) return;
			
			@SuppressWarnings("rawtypes")
			TablePosition pos = codeInfoTable.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();
			int col = pos.getColumn();
			@SuppressWarnings("rawtypes")
			TableColumn column = pos.getTableColumn();
			String val = column.getCellData(row).toString();
			logger.debug("Selected Value, " + val + ", Column: " + col + ", Row: " + row);
			
			if (column.getId().equals("codeCol")) {
				TreeCode tCode = codeInfoTable.getItems().get(row);
				if (tCode.getChildCnt() > 0) {
					currentPCode = val;
					settingData();
				}
			}else if (column.getId().equals("pCodeCol")) {
				TreeCode tCode = codeInfoTable.getItems().get(row);
				tCode = TreeCodeManager.getInstance().getCodeInfo(tCode.getPCode());
				if (tCode != null) {
					currentPCode = tCode.getPCode();
					settingData();
				}
			}
		}
	}
	
	@FXML
	public void handleRegist() {
		
		if (StringUtils.isEmpty(tfCode.getText()) || StringUtils.isEmpty(tfCodeNm.getText())) {
			DialogManager.showDialog("알림", null, "내용을 입력하세요");
			return;
		}

		CommonCode code = new CommonCode();
		
		try {
			
			code.setPCode(tfPCode.getText());
			code.setCode(tfCode.getText());
			code.setCodeNm(tfCodeNm.getText());
			code.setCodeType(tfCodeType.getText());
			code.setOrdNo(Integer.parseInt(tfOrdNo.getText()));
			code.setUseYn(cbUseYn.getValue());
			code.setViewYn(cbViewYn.getValue());
			code.setCodeDesc(tfCodeDesc.getText());
			
			if (actionBtn.getText().equals("수정")) {
				TreeCodeManager.getInstance().updateCode(code);
			}else {
				
				if (TreeCodeManager.getInstance().getCodeInfo(code.getPCode(), code.getCode()) != null) {
					DialogManager.showDialog("알림", null, "해당 데이터가 이미 존재합니다.");
					return;
				}
				
				TreeCodeManager.getInstance().insertCode(code);
			}

			tfCode.setText("");
			tfCodeNm.setText("");
			tfCodeType.setText("");
			tfOrdNo.setText("");
			cbUseYn.getSelectionModel().selectFirst();
			cbViewYn.getSelectionModel().selectFirst();
			tfCodeDesc.setText("");
			
			tfCode.requestFocus();
			
			settingData();
			
		} catch (Exception e) {
			DialogManager.showExceptionDialog(e, "에러", "처리중오류가 발생했습니다.", "상세내용을 확인해주세요.");
		}
	}
	
	@Override
	public void doClick(Object obj, int idx, BtnType btnType) {
		
		switch (btnType) {
			case BTN_TYPE_ADD_CHILD:{
				TreeCode target = (TreeCode) obj;
				tfPCode.setText(target.getCode());
				tfOrdNo.setText(String.valueOf(target.getChildCnt()));
				actionBtn.setText("등록");
			}
			break;
			
			case BTN_TYPE_DELETE:{
				TreeCode target = (TreeCode) obj;
				try {
					TreeCodeManager.getInstance().deleteCode(target);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(this.getClass().getSimpleName() + "오류!!", e);
				}
				settingData();
			}
			break;
			
			case BTN_TYPE_MODIFY:{
				TreeCode target = (TreeCode) obj;
				tfPCode.setText(target.getPCode());
				tfCode.setText(target.getCode());
				tfCodeType.setText(target.getCodeType());
				tfCodeNm.setText(target.getCodeNm());
				tfOrdNo.setText(String.valueOf(target.getOrdNo()));
				if (target.getUseYn().equalsIgnoreCase("Y")) cbUseYn.getSelectionModel().selectFirst();
				else cbUseYn.getSelectionModel().select(1);
				
				if (target.getViewYn().equalsIgnoreCase("Y")) cbViewYn.getSelectionModel().selectFirst();
				else cbViewYn.getSelectionModel().select(1);
				
				tfCodeDesc.setText(target.getCodeDesc());
				
				actionBtn.setText("수정");
			}
			break;
		}
	}
	
	@FXML
	public void handleReOrder() {
		List<TreeCode> list = codeInfoTable.getItems();
		
		try {
			for (int i=0; i < list.size(); i++) {
				TreeCode code = list.get(i);
				code.setOrdNo(i);
				TreeCodeManager.getInstance().updateCode(code);
			}
			
			settingData();
			
			DialogManager.showDialog("알림", null, "재정렬에 성공했습니다.");
			
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e, "오류", "재정렬중 오류가 발생했습니다.", "아래내용을 확인해주세요");
		}
	}
	
	@FXML
	public void handleBtEnter() {
		handleRegist();
	}

	@Override
	public void handleSearch(Object source) {
	}

	@Override
	public void handleRegist(Object source) {
	}
}
