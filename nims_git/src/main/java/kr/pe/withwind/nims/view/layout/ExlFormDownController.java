package kr.pe.withwind.nims.view.layout;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.PrevInfoManager;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.ExlMake;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.utils.DateUtils;
import kr.pe.withwind.utils.DerbyException;

public class ExlFormDownController extends CommonController{

	@FXML
	private ComboBox<CommonCode> cbRptType;
	@FXML
	private TextField tfPrevFolder;
	@FXML
	private TextField tfPrevChrgNM;
	@FXML
	private TextField tfPrevChrgTelNo;
	@FXML
	private TextField tfPrevChrgMpNo;
	@FXML
	private TextField tfPrevChrgId;
	
	@FXML
	private TextField tfPrevOppBsshCd;
	@FXML
	private TextField tfPrevOppBsshNm;
	@FXML
	private TextField tfPrevOppStorgeNo;
	
	@FXML
    private ComboBox<String> cbRptSeCd;

    @FXML
    private DatePicker dpHdrDe;

    @FXML
    private Label lbRefUsrRptIdNo;
    @FXML
    private TextField tfRefUsrRptIdNo;

    @FXML
    private Label lbRmk;
    @FXML
    private TextField tfRmk;

    @FXML
    private Button btRegist;
	
	private MainApp mainApp;
	
	public ExlFormDownController() {
	}
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 * @throws DerbyException 
	 */
	@FXML
	public void initialize() throws DerbyException {
		
		cbRptSeCd.getItems().addAll("신규","취소","변경");
		cbRptSeCd.getSelectionModel().selectFirst();
		
		cbRptSeCd.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue observable, String oldValue, String newValue) {
				if (newValue.equals("신규")) {
					lbRefUsrRptIdNo.setVisible(false);
					lbRmk.setVisible(false);
					tfRefUsrRptIdNo.setVisible(false);
					tfRefUsrRptIdNo.setEditable(false);
					tfRmk.setVisible(false);
					tfRmk.setEditable(false);
				}else {
					lbRefUsrRptIdNo.setVisible(true);
					lbRmk.setVisible(true);
					tfRefUsrRptIdNo.setVisible(true);
					tfRefUsrRptIdNo.setEditable(true);
					tfRmk.setVisible(true);
					tfRmk.setEditable(true);
				}
			}
		});
		
		lbRefUsrRptIdNo.setVisible(false);
		lbRmk.setVisible(false);
		tfRefUsrRptIdNo.setVisible(false);
		tfRefUsrRptIdNo.setEditable(false);
		tfRmk.setVisible(false);
		tfRmk.setEditable(false);
		
		
		cbRptType.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","RPT_TYPE")));
		cbRptType.getSelectionModel().selectFirst();
		
		tfPrevFolder.setText(PrevInfoManager.getInstance().getValue("FOLDER_NM"));
		tfPrevChrgNM.setText(PrevInfoManager.getInstance().getValue("CHRGNM"));
		tfPrevChrgTelNo.setText(PrevInfoManager.getInstance().getValue("CHRGTELNO"));
		tfPrevOppBsshCd.setText(PrevInfoManager.getInstance().getValue("OPPBSSHCD"));
		tfPrevChrgMpNo.setText(PrevInfoManager.getInstance().getValue("CHRGMPNO"));
		tfPrevOppBsshNm.setText(PrevInfoManager.getInstance().getValue("OPPBSSHNM"));
		tfPrevOppStorgeNo.setText(PrevInfoManager.getInstance().getValue("OPPSTORGENO"));
		tfPrevChrgId.setText(PrevInfoManager.getInstance().getValue("REGISTERID"));
		
		dpHdrDe.setValue(LocalDate.now());
		
		// 버튼 리스너 등록
		btRegist.setOnKeyPressed(registHandler);
		btRegist.setOnMouseClicked(registHandlerM);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void showDirectoryChooser() {
		DirectoryChooser dc = new DirectoryChooser();
		File selectedDc = dc.showDialog(mainApp.getPrimaryStage());
		if (selectedDc != null) tfPrevFolder.setText(selectedDc.getPath());
	}
	
	public void createFile() {
		if (isValid()) {
			ExlMake exlMaker = new ExlMake();
			try {
				
				// 사용자 입력분에 대해 최근 정보 등록
				PrevInfoManager.getInstance().setValue("FOLDER_NM", tfPrevFolder.getText());
				PrevInfoManager.getInstance().setValue("CHRGNM", tfPrevChrgNM.getText());
				PrevInfoManager.getInstance().setValue("CHRGTELNO", tfPrevChrgTelNo.getText());
				PrevInfoManager.getInstance().setValue("OPPBSSHCD", tfPrevOppBsshCd.getText());
				PrevInfoManager.getInstance().setValue("CHRGMPNO", tfPrevChrgMpNo.getText());
				PrevInfoManager.getInstance().setValue("OPPBSSHNM", tfPrevOppBsshNm.getText());
				PrevInfoManager.getInstance().setValue("OPPSTORGENO", tfPrevOppStorgeNo.getText());
				PrevInfoManager.getInstance().setValue("REGISTERID", tfPrevChrgId.getText());
				PrevInfoManager.getInstance().setValue("HDRDE", dpHdrDe.getValue().toString().replaceAll("-", ""));
				
				PrevInfoManager.getInstance().setValue("RPTTYCD", String.valueOf(cbRptSeCd.getSelectionModel().getSelectedIndex()));
				
				
				if (!cbRptSeCd.getValue().equals("신규")) {
					PrevInfoManager.getInstance().setValue("REFUSRRPTIDNO", tfRefUsrRptIdNo.getText());
					PrevInfoManager.getInstance().setValue("RMK", tfRmk.getText());
				}else {
					PrevInfoManager.getInstance().setValue("REFUSRRPTIDNO", "");
					PrevInfoManager.getInstance().setValue("RMK", "");
				}
				
				String newFile = exlMaker.doMake(cbRptType.getValue().getCode(), tfPrevFolder.getText());
				
				DialogManager.showDialog("알림", null, "'" + newFile + "' 파일이 생성되었습니다.");
			} catch (IOException e) {
				DialogManager.showExceptionDialog(e, "에러", null, "내용을 확인해주세요");
			} catch (DerbyException e) {
				DialogManager.showExceptionDialog(e, "에러", null, "내용을 확인해주세요");
			}
		}
	}

	private boolean isValid() {
		
		String errMsg = "";
		TextField target = null;
		
		if (tfPrevFolder.getText() == null || tfPrevFolder.getText().length() == 0 ) {
			errMsg += "엑셀파일이 생성될 폴더를 선택하세요"+System.lineSeparator();
			target = tfPrevFolder;
		}
		
		if (dpHdrDe.getValue() == null) {
			errMsg += "취급일자를 선택하세요"+System.lineSeparator();
			target = tfPrevFolder;
		}
		
		if (errMsg.length() == 0) {
			return true;
		}else {
			
			DialogManager.showDialog(mainApp.getPrimaryStage(), "안내", null, errMsg);
			
			target.setFocusTraversable(true);
			return false;
		}
	}

	@Override
	public void handleSearch(Object source) {
		
	}

	@Override
	public void handleRegist(Object source) {
		if (!(source instanceof Node)) return;
		
		Node node = (Node)source;
		
		if (node.getId().equals("btRegist")) {
			createFile();
		}
	}
}