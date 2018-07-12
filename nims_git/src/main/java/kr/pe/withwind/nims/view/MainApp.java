package kr.pe.withwind.nims.view;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.jetty.NimsHttp;
import kr.pe.withwind.nims.view.layout.AcceptCallController;
import kr.pe.withwind.nims.view.layout.BsshCallController;
import kr.pe.withwind.nims.view.layout.CodeManageController;
import kr.pe.withwind.nims.view.layout.CsvToXMLController;
import kr.pe.withwind.nims.view.layout.DestpCallController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.nims.view.layout.ExlFormDownController;
import kr.pe.withwind.nims.view.layout.ExlSettingController;
import kr.pe.withwind.nims.view.layout.ExlToXMLController;
import kr.pe.withwind.nims.view.layout.OfficeCallController;
import kr.pe.withwind.nims.view.layout.PlaceCallController;
import kr.pe.withwind.nims.view.layout.PreferenceController;
import kr.pe.withwind.nims.view.layout.ProductCallController;
import kr.pe.withwind.nims.view.layout.PurchaseCallController;
import kr.pe.withwind.nims.view.layout.RootLayoutController;
import kr.pe.withwind.nims.view.layout.SeqCallController;
import kr.pe.withwind.nims.view.layout.StockCallController;
import kr.pe.withwind.nims.view.layout.rpt.RptListController;

public class MainApp extends Application {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private RootLayoutController rootController;
	
	private boolean isTaskRun = false;
	
	public MainApp() {
		TreeCodeManager.getInstance().getChildCodeList(null);
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
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;	
		this.primaryStage.setTitle("마약류 통합관리 시스템 인터페이스 프로그램");
		initRootLayout();
	}

	public void initRootLayout() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();

			rootController = loader.getController();
			rootController.setMainApp(this);
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
//	public void showExlSettingView() {
//		
//		try {
//			
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("layout/ExlSettingView.fxml"));
//			Node viewNode = loader.load();
//			
//			rootLayout.setCenter(viewNode);
//
//			// 메인 애플리케이션이 컨트롤러를 이용할 수 있게 한다.
//	        ExlSettingController controller = loader.getController();
//	        controller.setMainApp(this);
//
//		} catch (Exception e) {
//			logger.error(this.getClass().getSimpleName() + "오류!!", e);
//		}
//	}

	/**
     * 메인 스테이지를 반환한다.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		
//		NimsHttp server = NimsHttp.getInstance(8081);
//		server.startHttp();
		launch(args);
//		server.stopHttp();
	}
	
	public void showLayout(String xmlSource) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(xmlSource));
			Node viewNode = loader.load();

			CommonController controller = loader.getController();
			controller.setMainApp(this);
			rootLayout.setCenter(viewNode);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
	
	public void showRptLayout(String rptSeCd) {
		logger.debug("rptSeCd : " + rptSeCd);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("layout/rpt/RptListView.fxml"));
			Node viewNode = loader.load();

			RptListController controller = loader.getController();
			controller.setMainApp(this);
			controller.setRptSeCd(rptSeCd);
			rootLayout.setCenter(viewNode);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
}
