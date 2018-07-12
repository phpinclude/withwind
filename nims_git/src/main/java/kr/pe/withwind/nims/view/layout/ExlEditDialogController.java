package kr.pe.withwind.nims.view.layout;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.reflect.ClassPath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.xls.XlsVoMappingManager;
import kr.pe.withwind.nims.xls.adapter.AdpChrgMpEncode;
import kr.pe.withwind.nims.xls.adapter.NimsAdpInf;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.utils.DerbyManager;

public class ExlEditDialogController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
	private ComboBox<CommonCode> rptTypeCb;
	@FXML
	private ComboBox<CommonCode> fieldNmCb;
	@FXML
	private ComboBox<CommonCode> hlTypeCb;
	
	@FXML
	private TextField xlsLabelField;
	@FXML
	private TextField colIdxField;
	@FXML
	private ComboBox<CommonCode> viewYnCb;
	@FXML
	private ComboBox<CommonCode> refTypeCb;
	
	@FXML
	private TextField refDefaultField;
	
	@FXML
	private ComboBox<String> refDefaultCb;
	
	private Stage dialogStage;
	private XlsMapping xlsMapping;
	private boolean okClicked = false;
	
	public ExlEditDialogController() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	}
	
	@FXML
	public void initalize() {
		logger.debug("ExlEditDialogController.initalize call !!");		
	}
	
	
	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;
		
		logger.debug("HL_TYPE size : " + TreeCodeManager.getInstance().getChildCodeList("ROOT","HL_TYPE").size());
		
		rptTypeCb.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","RPT_TYPE")));
		fieldNmCb.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","VO_FIELD")));
		hlTypeCb.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","HL_TYPE")));
		viewYnCb.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","USE_YN")));
		refTypeCb.setItems(FXCollections.observableArrayList(TreeCodeManager.getInstance().getChildCodeList("ROOT","REF_TYPE")));
		// 굳이 전부 구현할 필요 없이 콤보박스에 들어가는 객체에 toString을 Override 하면 됨
//		rptTypeCb.setCellFactory(new Callback<ListView<CommonCode>, ListCell<CommonCode>>() {
//			
//			@Override
//			public ListCell<CommonCode> call(ListView<CommonCode> param) {
//				final ListCell<CommonCode> cell = new ListCell<CommonCode>() {
//
//					@Override
//					protected void updateItem(CommonCode item, boolean empty) {
//						super.updateItem(item, empty);
//						
//						if (item != null) {
//							setText(item.getCodeNm());
//						}else {
//							setText(null);
//						}
//					}
//					
//				};
//				return cell;
//			}
//		});
		
//		rptTypeCb.setConverter(new StringConverter<CommonCode>() {
//
//			@Override
//			public String toString(CommonCode object) {
//				return object.getCodeNm() + " " + object.getCode();
//			}
//
//			@Override
//			public CommonCode fromString(String string) {
//				CommonCode reValue = new CommonCode();
//				List<CommonCode> list = rptTypeCb.getItems();
//				for (CommonCode code : list) {
//					if (string.equals(code.getCodeNm() + " " + code.getCode())) return code;
//				}
//				return null;
//			}
//		});
		
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();

		try {
			for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
				if (info.getName().startsWith("kr.pe.withwind.nims.xls.adapter")) {
					final Class<?> clazz = info.load();
					if (clazz.isInterface()) continue;
//					logger.debug(clazz.getTypeName());
					refDefaultCb.getItems().add(clazz.getSimpleName());
				}
			}
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
		rptTypeCb.getSelectionModel().selectFirst();
		fieldNmCb.getSelectionModel().selectFirst();
		hlTypeCb.getSelectionModel().selectFirst();
		viewYnCb.getSelectionModel().selectFirst();
		refDefaultCb.getSelectionModel().selectFirst();
	}
	
	public void setXlsMapping(XlsMapping xlsMapping) {
		this.xlsMapping = xlsMapping;
		
		rptTypeCb.getSelectionModel().select(getIdx(rptTypeCb,xlsMapping.getRptType(),"code"));
		fieldNmCb.getSelectionModel().select(getIdx(fieldNmCb,xlsMapping.getFieldNm(),"code"));
		hlTypeCb.getSelectionModel().select(getIdx(hlTypeCb,xlsMapping.getHlType(),"code"));
		if (xlsMapping.getXlsLabel() != null) xlsLabelField.setText(xlsMapping.getXlsLabel());
		colIdxField.setText(String.valueOf(xlsMapping.getColIdx()));
		viewYnCb.getSelectionModel().select(getIdx(viewYnCb,xlsMapping.getViewYn(),"code"));
		refTypeCb.getSelectionModel().select(getIdx(refTypeCb,xlsMapping.getRefType(),"code"));
		
		if (xlsMapping.getRefType() != null && (xlsMapping.getRefType().equalsIgnoreCase("A") || xlsMapping.getRefType().equalsIgnoreCase("L"))) {
			if (!StringUtils.isEmpty(xlsMapping.getRefDefault())) {
				refDefaultCb.setValue(xlsMapping.getRefDefault());
			}
		}else {
			refDefaultField.setText(xlsMapping.getRefDefault());
		}
		
		handleFieldNmChange();
		handleRefType();
	}
	
	private <T> int getIdx(ComboBox<T> comboBox, String source, String fieldNm) {
		
		if (source == null)	return 0;
		
		List<T> list = comboBox.getItems();

		try {
			for (int i=0; i < list.size(); i++) {
				Object obj = list.get(i);
				if (source.equals(BeanUtils.getProperty(obj, fieldNm))) return i;
			}
		}catch(Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
				
		return 0;
	}

	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	public void handleOk() {

		if (isInputVaild()) {
			
			try {
				XlsMapping old = new XlsMapping();
				
				BeanUtils.copyProperties(old, xlsMapping);
				
				xlsMapping.setColIdx(Integer.parseInt(colIdxField.getText()));
				xlsMapping.setFieldNm(fieldNmCb.getValue().getCode());
				xlsMapping.setHlType(hlTypeCb.getValue().getCode());
				
				if (refTypeCb.getValue().getCode().equalsIgnoreCase("A") || refTypeCb.getValue().getCode().equalsIgnoreCase("L")) {
					xlsMapping.setRefDefault(refDefaultCb.getValue());
				}else {
					xlsMapping.setRefDefault(refDefaultField.getText());
				}
				
				xlsMapping.setRefType(refTypeCb.getValue().getCode());
				xlsMapping.setRptType(rptTypeCb.getValue().getCode());
				xlsMapping.setViewYn(viewYnCb.getValue().getCode());
				xlsMapping.setXlsLabel(xlsLabelField.getText());
				
				// DB에 데이터를 넣는다.
				try {
					
					int cnt = XlsVoMappingManager.getInstance().update(xlsMapping);
					
					if (cnt < 1) {
						XlsVoMappingManager.getInstance().insert(xlsMapping);
					}
					
				}catch(Exception e){
					BeanUtils.copyProperties(xlsMapping, old);
					throw e;
				}
				
				okClicked = true;
				dialogStage.close();
			}catch(Exception e) {
				DialogManager.showExceptionDialog(e, "에러", "에러내용을 확인 해주세요.", e.getMessage());
			}
		}
	}
	
	/**
	 * 필드명 바뀔때 엑셀 표시이름도 같이 바꿔준다.
	 */
	@FXML
	public void handleFieldNmChange() {
		xlsLabelField.setText(fieldNmCb.getValue().getCodeNm());
	}
	
	/**
	 * 필드명 바뀔때 엑셀 표시이름도 같이 바꿔준다.
	 */
	@FXML 
	public void handleRefType() {
		
		if (refTypeCb.getValue().getCode().equalsIgnoreCase("A") || refTypeCb.getValue().getCode().equalsIgnoreCase("L")) { 
			refDefaultCb.setVisible(true);
			refDefaultField.setVisible(false);
		}else {
			refDefaultCb.setVisible(false);
			refDefaultField.setVisible(true);
		}
	}
	
	/**
	 * 취소버튼
	 */
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	/**
	 * 검증버튼
	 * @return
	 */
	@FXML
	public void handleConfirm() {
		if (!refTypeCb.getValue().getCode().equals("A") && !refTypeCb.getValue().getCode().equals("L")) return;

		boolean isLoaded = false;
		
		try {
			Class cls = this.getClass().getClassLoader().loadClass("kr.pe.withwind.nims.xls.adapter." + refDefaultCb.getValue());
			NimsAdpInf aas =  (NimsAdpInf) cls.newInstance();
			
			isLoaded = true;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
		logger.debug(isLoaded);
	}

	private boolean isInputVaild() {
		
		String errMsg = "";

		if (colIdxField.getText() == null || colIdxField.getText().length() == 0 ) {
			errMsg += "엑셀파일에 표현될 셀 번호를 입력하세요."+System.lineSeparator();
		}else {
			try {
				Integer.parseInt(colIdxField.getText());
			}catch (NumberFormatException e) {
				errMsg += "셀번호는 숫자여야만 합니다."+System.lineSeparator();
			}
		}
		
		if (errMsg.length() == 0) {
			return true;
		}else {
			
			DialogManager.showDialog(dialogStage, "안내", "입력 데이터를 확인해 주세요.", errMsg);
			
			return false;
		}
	}
}
