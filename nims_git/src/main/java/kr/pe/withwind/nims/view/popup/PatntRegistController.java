package kr.pe.withwind.nims.view.popup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HandshakeCompletedListener;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import kr.pe.withwind.common.TreeCode;
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
import kr.pe.withwind.nims.view.PopupController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class PatntRegistController extends CommonController implements PopupController {

	@FXML
    private TextField tfPatntNm;

    @FXML
    private ComboBox<TreeCode> tfIdType;

    @FXML
    private TextField tfIdNo;

    @FXML
    private DatePicker tfBrthdy;

    @FXML
    private ComboBox<TreeCode> tfSex;

    @FXML
    private TextField tfTelNo;

    @FXML
    private TextField tfAddr;

    @FXML
    private TextField tfEtc;

    @FXML
    private Button btClose;

    @FXML
    private Button btRegist;
    
    private PatntInfo patntInfo;
    
	@FXML
	public void initialize() {
		
		tfEtc.setOnKeyPressed(registHandler);
		btRegist.setOnKeyPressed(registHandler);
		btRegist.setOnMouseClicked(registHandlerM);
		
		btClose.setOnKeyPressed(closeHandler);
		btClose.setOnMouseClicked(closeHandlerM);
		
		tfSex.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE", "ND_01")));
		tfSex.getSelectionModel().selectFirst();
		
		tfIdType.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE", "ND_97")));
		tfIdType.getSelectionModel().selectFirst();
		
		tfBrthdy.setValue(LocalDate.now());
	}
	
	@Override
	public void setSearchText(String searchText) {
		return;
	}

	@Override
	public <T> T getData() {
		
		return (T) patntInfo;
	}

	@Override
	public void handleSearch(Object source) {
		return;
	}

	@Override
	public void handleRegist(Object source) {
		if (patntInfo == null) patntInfo = new PatntInfo();
		patntInfo.setAddr(tfAddr.getText());
		patntInfo.setBrthdy(tfBrthdy.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		patntInfo.setEtc(tfEtc.getText());
		patntInfo.setIdNo(tfIdNo.getText());
		patntInfo.setIdType(tfIdType.getValue().getCode());
		patntInfo.setPatntNm(tfPatntNm.getText());
		patntInfo.setSex(tfSex.getValue().getCode());
		patntInfo.setTelNo(tfTelNo.getText());
		
		try {
			DerbyManager.getInstance().insert("PatntInfo.insert", patntInfo);
			patntInfo = (PatntInfo) DerbyManager.getInstance().list("PatntInfo.selectByParam", patntInfo).get(0);
			
			DialogManager.showDialog("알림", null, "정상등록되었습니다.");
			handleClose(btClose);
		} catch (DerbyException e) {
			DialogManager.showExceptionDialog(e, "에러", "환자정보 등록 중 오류가 발생했습니다.", "오류내용을 확인 하세요");
		}
	}

	@Override
	public void setData(Object obj) {
		if (!(obj instanceof PatntInfo)) return;
		
		patntInfo = (PatntInfo) obj;
		
		tfPatntNm.setText(patntInfo.getPatntNm());
		tfAddr.setText(patntInfo.getAddr());
		
	}
}
