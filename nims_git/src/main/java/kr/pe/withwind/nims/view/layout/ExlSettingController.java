package kr.pe.withwind.nims.view.layout;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.XlsVoMappingManager;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class ExlSettingController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	@FXML
	private TableView<XlsMapping> mapInfoTable;
	@FXML
	private TableColumn<XlsMapping, String> rptTypeCol;
	@FXML
	private TableColumn<XlsMapping, String> fieldNmCol;
	@FXML
	private TableColumn<XlsMapping, String> hlTypeCol;
	@FXML
	private TableColumn<XlsMapping, String> xlsLabelCol;
	@FXML
	private TableColumn<XlsMapping, Integer> colIdxCol;
	@FXML
	private TableColumn<XlsMapping, String> viewYnCol;
	@FXML
	private TableColumn<XlsMapping, String> refTypeCol;
	@FXML
	private TableColumn<XlsMapping, String> refDefaultCol;
	@FXML
	private ComboBox<CommonCode> rptNameCombo;
	@FXML
	private ComboBox<CommonCode> sourceCb;
	@FXML
	private ComboBox<CommonCode> targetCb;
	
	private MainApp mainApp;

	
	public ExlSettingController() {
	}

	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
		
		CommonCode tmpCode = new CommonCode();
        tmpCode.setCode("ALL");
        tmpCode.setCodeNm("전체");
        rptNameCombo.getItems().add(tmpCode);
		
		settingData();
		
		rptTypeCol.setCellValueFactory(cellData -> cellData.getValue().rptTypeProperty());
		fieldNmCol.setCellValueFactory(cellData -> cellData.getValue().fieldNmProperty());
		hlTypeCol.setCellValueFactory(cellData -> cellData.getValue().hlTypeProperty());
		xlsLabelCol.setCellValueFactory(cellData -> cellData.getValue().xlsLabelProperty());
		colIdxCol.setCellValueFactory(cellData -> cellData.getValue().colIdxProperty().asObject());
		viewYnCol.setCellValueFactory(cellData -> cellData.getValue().viewYnProperty());
		refTypeCol.setCellValueFactory(cellData -> cellData.getValue().refTypeProperty());
		refDefaultCol.setCellValueFactory(cellData -> cellData.getValue().refDefaultProperty());
	}
	
	/**
	 * 기초데이터 셋팅
	 */
	private void settingData() {
		
//		if (!rptNameCombo.getItems().contains("전체")) rptNameCombo.getItems().add("전체");

		try {
			List<XlsMapping> tableItems = (List<XlsMapping>) DerbyManager.getInstance().list("XlsMapping.getXlsMapping",null);
			
			List<TreeCode> rptList = TreeCodeManager.getInstance().getChildCodeList("ROOT","RPT_TYPE");
			
	        mapInfoTable.setItems(FXCollections.observableArrayList(tableItems));
	        
	        List<TreeCode> hasRptList = new ArrayList<TreeCode>();
	        
	        for (TreeCode code : rptList) {
	        	if (hasIt(tableItems, code.getCode())) hasRptList.add(code);
	        }
	        
	        rptNameCombo.getItems().removeAll(hasRptList);
	        rptNameCombo.getItems().addAll(hasRptList);
	        
	        targetCb.setItems(FXCollections.observableArrayList(rptList));
	        sourceCb.setItems(FXCollections.observableArrayList(hasRptList));
	        
	        // 상단 콤보박스 기본 값 선택
	        rptNameCombo.getSelectionModel().selectFirst();
	        sourceCb.getSelectionModel().selectFirst();
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e, "에러", null, "내용을 확인해주세요");
		}
        
	}
	
	private boolean hasIt(List<XlsMapping> list, String data) {
		
		for (XlsMapping xls : list) {
			if (xls.getRptType().equals(data)) return true;
		}
		
		return false;
	}
	
	public void checkData(Object observable, XlsMapping oldValue, XlsMapping xlsMapping) {
		
		logger.debug("observable type : " + observable.getClass().getName());
		
		ReadOnlyProperty prop = (ReadOnlyProperty) observable;
		
		logger.debug(prop.getBean().getClass().getName());
		logger.debug(prop.getName());
		
	}
	
	@FXML
	public void handleCopyBt() {
		
		if (targetCb.getValue() == null) {
			return;
		}
		
		String source = sourceCb.getValue().getCode();
		String target = targetCb.getValue().getCode();
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("source", source);
		param.put("target",target);
		
		try {
			DerbyManager.getInstance().insert("XlsMapping.copyXlsMapping", param);
			settingData();
			DialogManager.showDialog("알림", null, "정상처리되었습니다.");
		} catch (DerbyException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
	}
	
	@FXML
	public void handleCombo(ActionEvent event) {
		
		try {
			if (event.getSource() instanceof ComboBox) {
				ComboBox<CommonCode> source = (ComboBox<CommonCode>) event.getSource();
				CommonCode selectValue = source.getValue();
				
				if ("ALL".equals(selectValue.getCode())) {
					settingData();
					return;
				}
				
				List<XlsMapping> tableItems = (List<XlsMapping>) DerbyManager.getInstance().list("XlsMapping.getXlsMapping",null);
				
				ObservableList<XlsMapping> fillteredData = FXCollections.observableArrayList();
				
				for (XlsMapping data : tableItems) {
					if (data.getRptType().equals(selectValue.getCode())) {
						fillteredData.add(data);
					}
				}
				
				mapInfoTable.setItems(fillteredData);
				
			}
		}catch(Exception e) {
			DialogManager.showExceptionDialog(e, "에러", null, "내용을 확인하세요");
		}
	}
	@FXML
	public void handleDeleteBtn() {
		int selectedIdx = mapInfoTable.getSelectionModel().getSelectedIndex();
		
		if (selectedIdx < 0) {
			DialogManager.showDialog("안내", null, "목록을 선택하셔야 합니다.");
			return;
		}
		
		Optional<ButtonType> result = DialogManager.showConfirm("경고", null, "정말로 삭제하시겠습니까?");
		
		if (result.get() == ButtonType.OK){
			
			XlsMapping xlsMapping = mapInfoTable.getItems().get(selectedIdx);
			
			// DB에 데이터를 넣는다.
			int cnt = 0;

			try {
				cnt = XlsVoMappingManager.getInstance().delete(xlsMapping);
			}catch(Exception e){
				
			}
		    
			if (cnt > 0) mapInfoTable.getItems().remove(selectedIdx);
		}
	}
	
	@FXML
	public void handelEditBtn() {
		int selectedIdx = mapInfoTable.getSelectionModel().getSelectedIndex();
		
		if (selectedIdx < 0) {
			DialogManager.showDialog("안내", null, "목록을 선택하셔야 합니다.");
			return;
		}
		
		if (showEditDialog(mapInfoTable.getItems().get(selectedIdx))) {
			reSort();
		}
	}
	
	@FXML
	public void handelNewBtn() {
		XlsMapping newItem = new XlsMapping();
		
		if (showEditDialog(newItem)) {
			 mapInfoTable.getItems().add(newItem);
			 reSort();
		}
	}
	
	@FXML
	public void handleCopyAdd() throws IllegalAccessException, InvocationTargetException {
		int selectedIdx = mapInfoTable.getSelectionModel().getSelectedIndex();
		
		if (selectedIdx < 0) {
			DialogManager.showDialog("안내", null, "목록을 선택하셔야 합니다.");
			return;
		}
		
		XlsMapping target = new XlsMapping();
		BeanUtils.copyProperties(target, mapInfoTable.getItems().get(selectedIdx));
		
		if (showEditDialog(target)) {
			mapInfoTable.getItems().add(target);
			reSort();
		}
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	private void reSort() {
		mapInfoTable.getItems().sort((o1,o2) -> {
			return o1.getColIdx() > o2.getColIdx() ? 1 : -1;
		});
		
		mapInfoTable.getItems().sort((o1,o2) -> {
			return StringUtils.compare(o1.getHlType(), o2.getHlType());
		});
		
		mapInfoTable.getItems().sort((o1,o2) -> {
			return StringUtils.compare(o1.getRptType(), o2.getRptType());
		});
	}
	
	public boolean showEditDialog(XlsMapping xlsMapping) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("ExlEditDialog.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("엑셀파일 양식 수정");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        
	        ExlEditDialogController controller = loader.getController();
	        
	        controller.setDialogStage(dialogStage);
	        controller.setXlsMapping(xlsMapping);
	        
	        dialogStage.showAndWait();
	        
	        return controller.isOkClicked();
	        
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
			return false;
		}
	}
	
	@FXML
	public void handleArangeOrdr() {
		logger.debug("handleArangeOrdr call!!");
		
		if (rptNameCombo.getSelectionModel().getSelectedIndex() == 0) {
			DialogManager.showDialog("알림", "보고유형을 선택하세요", null);
			return;
		}
		
		ObservableList<XlsMapping> list = mapInfoTable.getItems();
		
		String prevHlType = "";
		int idx = 0;
		
		for (XlsMapping xlsMapping : list) {
			if (!xlsMapping.getHlType().equals(prevHlType)) {
				prevHlType = xlsMapping.getHlType();
				idx = 0;
			}
			
			xlsMapping.setColIdx(idx);
			
			try {
				int cnt = DerbyManager.getInstance().update("XlsMapping.updateXlsMapping", xlsMapping);
				
				if (cnt < 1) {
					DerbyManager.getInstance().insert("XlsMapping.insertXlsMapping", xlsMapping);
				}
			}catch(Exception e){
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
			idx++;
		}
		
		XlsVoMappingManager.getInstance().init();
		
	}

	@Override
	public void handleSearch(Object source) {
		
	}

	@Override
	public void handleRegist(Object source) {
		
	}
}
