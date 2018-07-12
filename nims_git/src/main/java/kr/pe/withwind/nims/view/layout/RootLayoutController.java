package kr.pe.withwind.nims.view.layout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.reflections.Reflections;

import com.google.common.reflect.ClassPath;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.domain.openapi.BsshList;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.MainApp;
import kr.pe.withwind.nims.xls.adapter.NimsAdpInf;
import kr.pe.withwind.utils.DerbyManager;

public class RootLayoutController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	private MainApp mainApp;
	
	@FXML
	MenuItem mShowRoot;
	@FXML
	MenuItem mPreference;
	@FXML
	MenuItem mExlMap;
	@FXML
	MenuItem mSysSetting;
	@FXML
	MenuItem mExlDown;
	@FXML
	MenuItem mExlToXML;
	@FXML
	MenuItem mCsvToXML;
	@FXML
	MenuItem mProductCall;
	@FXML
	MenuItem mBsshCall;
	@FXML
	MenuItem mPlaceCall;
	@FXML
	MenuItem mStockCall;
	@FXML
	MenuItem mPurchaseCall;
	@FXML
	MenuItem mAcceptCall;
	@FXML
	MenuItem mSeqCall;
	@FXML
	MenuItem mOfficeCall;
	@FXML
	MenuItem mDestpCall;
	@FXML
	MenuItem mAboutDialog;
	
	// 보고관리
	@FXML
	MenuItem mRptSlm;
	@FXML
	MenuItem mRptPcm;
	@FXML
	MenuItem mRptTnt;
	@FXML
	MenuItem mRptTni;
	@FXML
	MenuItem mRptEpm;
	@FXML
	MenuItem mRptIpm;
	@FXML
	MenuItem mRptPmm;
	@FXML
	MenuItem mRptMcm;
	@FXML
	MenuItem mRptPdm;
	@FXML
	MenuItem mRptUem;
	@FXML
	MenuItem mRptAar;
	@FXML
	MenuItem mRptCnt;
	@FXML
	MenuItem mRptCni;
	@FXML
	MenuItem mRptEtt;
	@FXML
	MenuItem mRptEti;
	@FXML
	MenuItem mRptSmm;
	
	public RootLayoutController() {
	}
	
	/**
	 * 화면로딩시 자동으로 호출 되는 함수
	 * 초기 화면 데이터 셋팅 등을 한다.
	 */
	@FXML
	public void initialize() {
	}
	
	@FXML
	public void handleMenuEvent(ActionEvent event) {
		
		logger.debug("handleMenuEvent call !!");
		
		Object source = event.getSource();
		if (source == null) return;
		
		if (mainApp.checkTaskRun())	return;
		
		if (source.equals(mShowRoot)) {
			mainApp.initRootLayout();
		}else if (source.equals(mExlMap)) {
			mainApp.showLayout("layout/ExlSettingView.fxml");
		}else if (source.equals(mSysSetting)) {
			mainApp.showLayout("layout/CodeManageView.fxml");
		}else if (source.equals(mExlDown)) {
			mainApp.showLayout("layout/ExlFormDownView.fxml");
		}else if (source.equals(mExlToXML)) {
			mainApp.showLayout("layout/ExlToXMLView.fxml");
		}else if (source.equals(mCsvToXML)) {
			mainApp.showLayout("layout/CsvToXMLView.fxml");
		}else if (source.equals(mPreference)) {
			mainApp.showLayout("layout/PreferenceView.fxml");
		}else if (source.equals(mAcceptCall)) {
			mainApp.showLayout("layout/AcceptCallView.fxml");
		}else if (source.equals(mBsshCall)) {
			mainApp.showLayout("layout/BsshCallView.fxml");
		}else if (source.equals(mDestpCall)) {
			mainApp.showLayout("layout/DestpCallView.fxml");
		}else if (source.equals(mOfficeCall)) {
			mainApp.showLayout("layout/OfficeCallView.fxml");
		}else if (source.equals(mPlaceCall)) {
			mainApp.showLayout("layout/PlaceCallView.fxml");
		}else if (source.equals(mProductCall)) {
			mainApp.showLayout("layout/ProductCallView.fxml");
		}else if (source.equals(mPurchaseCall)) {
			mainApp.showLayout("layout/PurchaseCallView.fxml");
		}else if (source.equals(mSeqCall)) {
			mainApp.showLayout("layout/SeqCallView.fxml");
		}else if (source.equals(mStockCall)) {
			mainApp.showLayout("layout/StockCallView.fxml");
		}else if (source.equals(mAboutDialog)) {
			showAboutDialog();
		}
		
		// 보고관리 화면
		else if (source.equals(mRptSlm)) {
			mainApp.showRptLayout("SLM");
		}else if (source.equals(mRptPcm)) {
			mainApp.showRptLayout("PCM");
		}else if (source.equals(mRptTnt)) {
			mainApp.showRptLayout("TNT");
		}else if (source.equals(mRptTni)) {
			mainApp.showRptLayout("TNI");
		}else if (source.equals(mRptEpm)) {
			mainApp.showRptLayout("EPM");
		}else if (source.equals(mRptIpm)) {
			mainApp.showRptLayout("IPM");
		}else if (source.equals(mRptPmm)) {
			mainApp.showRptLayout("PMM");
		}else if (source.equals(mRptMcm)) {
			mainApp.showRptLayout("MCM");
		}else if (source.equals(mRptPdm)) {
			mainApp.showRptLayout("PDM");
		}else if (source.equals(mRptUem)) {
			mainApp.showRptLayout("UEM");
		}else if (source.equals(mRptAar)) {
			mainApp.showRptLayout("AAR");
		}else if (source.equals(mRptCnt)) {
			mainApp.showRptLayout("CNT");
		}else if (source.equals(mRptCni)) {
			mainApp.showRptLayout("CNI");
		}else if (source.equals(mRptEtt)) {
			mainApp.showRptLayout("ETT");
		}else if (source.equals(mRptEti)) {
			mainApp.showRptLayout("ETI");
		}else if (source.equals(mRptSmm)) {
			mainApp.showRptLayout("SMM");
		}
	}
	
	public void showAboutDialog() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(this.getClass().getResource("AboutDialog.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("프로그램 안내");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        dialogStage.showAndWait();
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}