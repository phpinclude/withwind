package kr.pe.withwind.nims.view.layout;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import kr.pe.withwind.nims.utils.CSVConverter;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.MainApp;

public class CsvToXMLController extends CommonController{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
	private TextField tfFileName;
	
	@FXML
	private TextArea taCSVStr;
	
	private MainApp mainApp;
	
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
	}
	
	@FXML
	public void handleFindBt() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\보고파일검증"));
		File selectedFc = fc.showOpenDialog(mainApp.getPrimaryStage());
		tfFileName.setText(selectedFc.getPath());
	}
	
	@FXML
	public void handleChangeBt() {
		
		if (mainApp.checkTaskRun()) return;
		
		String csvFileNm = tfFileName.getText();
		
		String errMsg = "";
		if (StringUtils.isEmpty(csvFileNm)) 
			errMsg += "CSV파일을 선택하세요." + System.lineSeparator();
				
		if (!"".equals(errMsg)) {
			DialogManager.showDialog("항목을 확인해주세요", null, errMsg);
			return;
		}
				
		taCSVStr.setText("처리시작");
		
		Task<Void> task = new Task<Void>() {
		    public Void call() throws Exception {
		    	mainApp.setTaskRun(true);
		    	doChange(csvFileNm);
		    	mainApp.setTaskRun(false);
				return null;
		    }
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}
	
	private void doChange(String fileName) {
		try {
			CSVConverter c = new CSVConverter();
			String value = c.parseCSV(fileName);
			Platform.runLater(()->{taCSVStr.setText(value);});
		}catch(Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "에러내용을 확인 해주세요.", e.getMessage());});
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void handleSearch(Object source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleRegist(Object source) {
		// TODO Auto-generated method stub
		
	}
}