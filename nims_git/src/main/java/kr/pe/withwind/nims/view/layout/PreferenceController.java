package kr.pe.withwind.nims.view.layout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.domain.BsshSetting;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.utils.MakeXml;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.ExlRead;
import kr.pe.withwind.nims.xml.Regist;
import kr.pe.withwind.nims.xml.SlmRegist;
import kr.pe.withwind.utils.CamelUtil;
import kr.pe.withwind.utils.DateUtils;

public class PreferenceController extends CommonController{
	
	@FXML
	private TextField tfPublicKeyT;
	@FXML
	private TextField tfBsshCdT;
	@FXML
	private TextField tfAuthKeyT;
	@FXML
	private TextField tfRptrNmT;
	@FXML
	private TextField tfRptrEntrpsNmT;
	
	@FXML
	private TextField tfPublicKeyR;
	@FXML
	private TextField tfBsshCdR;
	@FXML
	private TextField tfAuthKeyR;
	@FXML
	private TextField tfRptrNmR;
	@FXML
	private TextField tfRptrEntrpsNmR;
	
	@FXML
    private TextField tfChrgNmT;

    @FXML
    private TextField tfRegisterIdT;

    @FXML
    private TextField tfChrgTelNoT;

    @FXML
    private TextField tfChrgMpNoT;

    @FXML
    private TextField tfChrgNmR;

    @FXML
    private TextField tfRegisterIdR;

    @FXML
    private TextField tfChrgTelNoR;

    @FXML
    private TextField tfChrgMpNoR;
	
	private MainApp mainApp;
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		setData();
	}

	private void setData() {
		try {
			BsshSetting testInfo = BsshInfoManager.getInstance().getBsshInfo(TYPE.TEST);
			BsshSetting realInfo = BsshInfoManager.getInstance().getBsshInfo(TYPE.REAL);
			
			tfAuthKeyR.setText(realInfo.getAuthKey());
			tfBsshCdR.setText(realInfo.getBsshCd());;
			tfRptrEntrpsNmR.setText(realInfo.getRptrEntrpsNm());
			tfRptrNmR.setText(realInfo.getRptrNm());
			tfPublicKeyR.setText(realInfo.getPublicKey());
			tfChrgNmR.setText(realInfo.getChrgNm());
			tfRegisterIdR.setText(realInfo.getRegisterId());
			tfChrgTelNoR.setText(realInfo.getChrgTelNo());
			tfChrgMpNoR.setText(realInfo.getChrgMpNo());
			
			tfAuthKeyT.setText(testInfo.getAuthKey());
			tfBsshCdT.setText(testInfo.getBsshCd());;
			tfRptrEntrpsNmT.setText(testInfo.getRptrEntrpsNm());
			tfRptrNmT.setText(testInfo.getRptrNm());
			tfPublicKeyT.setText(testInfo.getPublicKey());
			tfChrgNmT.setText(testInfo.getChrgNm());
			tfRegisterIdT.setText(testInfo.getRegisterId());
			tfChrgTelNoT.setText(testInfo.getChrgTelNo());
			tfChrgMpNoT.setText(testInfo.getChrgMpNo());
			
		} catch (NimsException e) {
			DialogManager.showExceptionDialog(e, "오류", "기초정보 불러오는 중 오류가 발생했습니다.", e.getMessage());
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	public void handleTestBt() {
		BsshSetting bsshInfo = new BsshSetting();
		bsshInfo.setAuthKey(tfAuthKeyT.getText());
		bsshInfo.setBsshCd(tfBsshCdT.getText());
		bsshInfo.setInfType(TYPE.TEST.getName());
		bsshInfo.setRptrEntrpsNm(tfRptrEntrpsNmT.getText());
		bsshInfo.setRptrNm(tfRptrNmT.getText());
		bsshInfo.setPublicKey(tfPublicKeyT.getText());
		bsshInfo.setChrgNm(tfChrgNmT.getText());
		bsshInfo.setChrgTelNo(tfChrgTelNoT.getText());
		bsshInfo.setChrgMpNo(tfChrgMpNoT.getText());
		bsshInfo.setRegisterId(tfRegisterIdT.getText());
		
		try {
			BsshInfoManager.getInstance().setBsshInfo(bsshInfo);
			DialogManager.showDialog("안내", "처리성공", "TEST 기초정보가 수정되었습니다.");
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e, "오류", "TEST 기초정보 처리중 오류가 발생했습니다.", e.getMessage());
		}
	}
	
	@FXML
	public void handleRealBt() {
		BsshSetting bsshInfo = new BsshSetting();
		bsshInfo.setAuthKey(tfAuthKeyR.getText());
		bsshInfo.setBsshCd(tfBsshCdR.getText());
		bsshInfo.setInfType(TYPE.REAL.getName());
		bsshInfo.setRptrEntrpsNm(tfRptrEntrpsNmR.getText());
		bsshInfo.setRptrNm(tfRptrNmR.getText());
		bsshInfo.setPublicKey(tfPublicKeyR.getText());
		bsshInfo.setChrgNm(tfChrgNmR.getText());
		bsshInfo.setChrgTelNo(tfChrgTelNoR.getText());
		bsshInfo.setChrgMpNo(tfChrgMpNoR.getText());
		bsshInfo.setRegisterId(tfRegisterIdR.getText());
		
		try {
			BsshInfoManager.getInstance().setBsshInfo(bsshInfo);
			DialogManager.showDialog("안내", "처리성공", "운영 기초정보가 수정되었습니다.");
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e, "오류", "운영 기초정보 처리중 오류가 발생했습니다.", e.getMessage());
		}
	}

	@Override
	public void handleSearch(Object source) {
	}

	@Override
	public void handleRegist(Object source) {
	}
}