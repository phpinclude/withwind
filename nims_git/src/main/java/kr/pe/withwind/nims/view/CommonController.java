package kr.pe.withwind.nims.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.customnode.EditTextCell;
import kr.pe.withwind.nims.domain.ResultErrInfo;
import kr.pe.withwind.nims.domain.UploadResult;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.utils.CamelUtil;

public abstract class CommonController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private boolean isTaskRun = false;
	
	public Stage dialogStage;
	
	@FXML
	protected TableView<JSONObject> mainTable;
	
	protected MainApp mainApp;
	
	public EventHandler<KeyEvent> searchHandler = event -> {if (event.getCode() == KeyCode.ENTER) handleSearch(event.getSource());};
	public EventHandler<KeyEvent> registHandler = event -> {if (event.getCode() == KeyCode.ENTER) handleRegist(event.getSource());};
	public EventHandler<KeyEvent> closeHandler = event -> {if (event.getCode() == KeyCode.ENTER) handleClose(event.getSource());};
	
	public EventHandler<MouseEvent> searchHandlerM = event -> {handleSearch(event.getSource());};
	public EventHandler<MouseEvent> registHandlerM = event -> {handleRegist(event.getSource());};
	public EventHandler<MouseEvent> closeHandlerM = event -> {handleClose(event.getSource());};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void setTableColumn(JSONObject jObj) {
		
		List<TreeCode> paramNamelist = TreeCodeManager.getInstance().getChildCodeList("NIMS_CODE", "API_PARAM");
		
		for (TreeCode code : paramNamelist) {
			
			if (code.getViewYn().equalsIgnoreCase("N")) continue;
			
			if (jObj.get(code.getCode()) == null) continue;
			
			String colName = code.getCodeNm();
			
			TableColumn tColumn = new TableColumn<>(colName);
			tColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JSONObject, String>, ObservableValue<String>>(){

				@Override
				public ObservableValue<String> call(CellDataFeatures<JSONObject, String> param) {
					
					if (param.getValue().get(code.getCode()) == null) {
						return new SimpleStringProperty();
					}
					
					return new SimpleStringProperty(String.valueOf(param.getValue().get(code.getCode())));
				}
			});
			mainTable.getColumns().add(tColumn);
		}
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	protected void setTableColumn(UploadResult obj) {
//		
//		List<TreeCode> paramNamelist = TreeCodeManager.getInstance().getChildCodeList("TABLE_INFO", "RPT_LIST");
//		
//		for (TreeCode code : paramNamelist) {
//			
//			if (code.getViewYn().equalsIgnoreCase("N")) continue;
//			
//			final String camelName = CamelUtil.convert2CamelCase(code.getCode());
//			String value = null;
//			try {
//				value = BeanUtils.getProperty(obj, camelName);
//			} catch (Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
//			
//			if (value == null) continue;
//			
//			String colName = code.getCodeNm();
//			
//			TableColumn tColumn = new TableColumn<>(colName);
//			tColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UploadResult, String>, ObservableValue<String>>(){
//
//				@Override
//				public ObservableValue<String> call(CellDataFeatures<UploadResult, String> param) {
//					
//					String value = null;
//					try {
//						value = BeanUtils.getProperty(param.getValue(), camelName);
//					} catch (Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
//					
//					if (value == null) {
//						return new SimpleStringProperty();
//					}
//					
//					return new SimpleStringProperty(value);
//				}
//			});
//			mainTable.getColumns().add(tColumn);
//		}
//	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> void setTableColumn(String tbName) {
		
		List<TreeCode> paramNamelist = TreeCodeManager.getInstance().getChildCodeList("TABLE_INFO", tbName);
		
		for (TreeCode code : paramNamelist) {
			
			if (code.getViewYn().equalsIgnoreCase("N")) continue;
			
			final String camelName = CamelUtil.convert2CamelCase(code.getCode());
			
			String colName = code.getCodeNm();
			
			TableColumn tColumn = new TableColumn<>(colName);
			tColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<T, String> param) {
					
					String value = null;
					try {
						value = BeanUtils.getProperty(param.getValue(), camelName);
					} catch (Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
					
					if (value == null) {
						return new SimpleStringProperty();
					}
					
					return new SimpleStringProperty(value);
				}
			});
			
			mainTable.getColumns().add(tColumn);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> void setEditableTableColumn(T obj, String tbName) {
		
		List<TreeCode> paramNamelist = TreeCodeManager.getInstance().getChildCodeList("TABLE_INFO", tbName);
		
		
		
		for (TreeCode code : paramNamelist) {
			
			if (code.getViewYn().equalsIgnoreCase("N")) continue;
			
			final String camelName = CamelUtil.convert2CamelCase(code.getCode());
			String value = null;
			try {
				value = BeanUtils.getProperty(obj, camelName);
			} catch (Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
			
			if (value == null) continue;
			
			String colName = code.getCodeNm();
			
			TableColumn tColumn = new TableColumn<>(colName);
			tColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<T, String> param) {
					
					String value = null;
					try {
						value = BeanUtils.getProperty(param.getValue(), camelName);
					} catch (Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
					
					if (value == null) {
						return new SimpleStringProperty();
					}
					
					return new SimpleStringProperty(value);
				}
			});
			
			Callback<TableColumn,TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
				@Override
				public TableCell call(TableColumn param) {
					return new EditTextCell<T>();
				}
			};
			
			tColumn.setCellFactory(cellFactory);
			tColumn.setOnEditCommit(new EventHandler<CellEditEvent<T, String>>() {
				@Override
				public void handle(CellEditEvent<T, String> event) {
					T t = event.getTableView().getItems().get(event.getTablePosition().getRow());
					try {
						BeanUtils.setProperty(t, camelName, event.getNewValue());
					} catch (Exception e) {
						logger.error(this.getClass().getSimpleName() + "오류!!", e);
					}
				}
			});
			
			mainTable.getColumns().add(tColumn);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> void setTableColumn(List<XlsMapping> mappingList) {
				
		for (XlsMapping mapping : mappingList) {
			
			final String voField = mapping.getFieldNm();
		
			String colName = mapping.getXlsLabel();
			
			TableColumn tColumn = new TableColumn<>(colName);
			tColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<T, String> param) {
					
					String value = null;
					try {
						value = BeanUtils.getProperty(param.getValue(), voField);
					} catch (Exception e) {logger.error(this.getClass().getSimpleName() + "오류!!", e);}
					
					if (value == null) {
						return new SimpleStringProperty();
					}
					
					return new SimpleStringProperty(value);
				}
			});
			
			mainTable.getColumns().add(tColumn);
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void handleClose(Object source) {
		logger.debug("handleClose call !!");
		dialogStage.close();
	}
	
	public boolean isTaskRun() {
		return this.isTaskRun;
	}
	
	public void setTaskRun(boolean isTaskRun) {
		this.isTaskRun = isTaskRun;
	}
	
	public boolean checkTaskRun() {
		if (isTaskRun) {
			DialogManager.showDialog("알림", null, "요청 처리중이 입니다.\r\n잠시 기다리세요.");
			return true;
		}
		return false;
	}

	public abstract void handleSearch(Object source);
	public abstract void handleRegist(Object source);
}
