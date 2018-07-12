package kr.pe.withwind.nims.view;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.pe.withwind.nims.domain.PatntInfo;
import kr.pe.withwind.nims.view.layout.rpt.RptNewFormController;

public class NimsPopupFactory {
	
	private static final Logger logger = LogManager.getLogger(NimsPopupFactory.class);
	
	public static PopupController showBsshSearch(Stage owner, String searchText) {
		
		PopupController reValue = null;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NimsPopupFactory.class.getResource("popup/BsshSearchView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("상대업체검색");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(owner);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        
	        reValue = loader.getController();
	        reValue.setDialogStage(dialogStage);
	        reValue.setSearchText(searchText);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(NimsPopupFactory.class.getSimpleName() + "오류!!", e);
		}
		
		return reValue;
	}
	
	public static PopupController showProductSearch(Stage owner, String searchText) {
		
		PopupController reValue = null;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NimsPopupFactory.class.getResource("popup/ProductSearchView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("제품(재고)검색");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(owner);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        
	        reValue = loader.getController();
	        reValue.setDialogStage(dialogStage);
	        reValue.setSearchText(searchText);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(NimsPopupFactory.class.getSimpleName() + "오류!!", e);
		}
		
		return reValue;
	}

	public static PopupController showDissSearch(Stage owner, String searchText) {
		
		PopupController reValue = null;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NimsPopupFactory.class.getResource("popup/DissCodeSearchView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("질병코드 검색");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(owner);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        
	        reValue = loader.getController();
	        reValue.setDialogStage(dialogStage);
	        reValue.setSearchText(searchText);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(NimsPopupFactory.class.getSimpleName() + "오류!!", e);
		}
		
		return reValue;
	}

	public static PopupController showPatntSearch(Stage owner, String searchText) {
		PopupController reValue = null;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NimsPopupFactory.class.getResource("popup/PatntSearchView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("조제/투약 환자명 검색");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(owner);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        
	        reValue = loader.getController();
	        reValue.setDialogStage(dialogStage);
	        reValue.setSearchText(searchText);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(NimsPopupFactory.class.getSimpleName() + "오류!!", e);
		}
		
		return reValue;
	}

	public static <T> PopupController showPatntRegist(Stage owner, T obj) {
		PopupController reValue = null;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NimsPopupFactory.class.getResource("popup/PatntRegistView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("환자 등록");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(owner);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        
	        reValue = loader.getController();
	        reValue.setDialogStage(dialogStage);
	        reValue.setData(obj);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(NimsPopupFactory.class.getSimpleName() + "오류!!", e);
		}
		
		return reValue;
	}
	
	public static <T> PopupController showPurchaseRegist(Stage owner, T obj) {
		PopupController reValue = null;
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NimsPopupFactory.class.getResource("popup/PurchaseSearchView.fxml"));
			AnchorPane page = loader.load();
			
			// 다이얼로그 스테이지 만든다.
			Stage dialogStage = new Stage();
	        dialogStage.setTitle("판매업체 보고내역 찾기");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(owner);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.setResizable(false);
	        
	        reValue = loader.getController();
	        reValue.setDialogStage(dialogStage);
	        reValue.setData(obj);
	        
	        dialogStage.showAndWait();
	        
		} catch (IOException e) {
			logger.error(NimsPopupFactory.class.getSimpleName() + "오류!!", e);
		}
		
		return reValue;
	}
}
