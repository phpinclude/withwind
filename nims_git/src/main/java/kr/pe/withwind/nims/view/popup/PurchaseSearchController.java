package kr.pe.withwind.nims.view.popup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.customnode.CustomTextField;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.IncomByPurchase;
import kr.pe.withwind.nims.utils.ApiCall;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.nims.view.CommonController;
import kr.pe.withwind.nims.view.NimsPopupFactory;
import kr.pe.withwind.nims.view.PopupController;
import kr.pe.withwind.nims.view.layout.DialogManager;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.nims.xml.Lines;
import kr.pe.withwind.nims.xml.PcmRegist;
import kr.pe.withwind.nims.xml.ReportSet;
import kr.pe.withwind.utils.DerbyManager;

public class PurchaseSearchController extends CommonController implements PopupController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	@FXML
    private HBox hbHeader;

    @FXML
    private DatePicker dpStd;
    
    @FXML
    private AnchorPane apCustom;
    
    @FXML
    private ComboBox<String> cbContainYn;

    @FXML
    private Button btSearch;

    @FXML
    private TableView<JSONObject> mainTable;

    @FXML
    private ProgressBar pgBar;
    
    @FXML
    private Button btClose;

    @FXML
    private Label lbSelectRow;
    
    private boolean isNoneClickClose = true;
    
    private CustomTextField ctBsshCd;
    
    private ArrayList<JSONObject> searchAllDatas;
    
    public PurchaseSearchController() {
    	ctBsshCd = new CustomTextField("bsshCd");
		Button btn = new Button("검색");
		btn.setOnMouseClicked(event->{
			showBsshPopup(ctBsshCd.getText());
		});
		ctBsshCd.setRightNode(btn);
    }
    
	private void showBsshPopup(String searchText) {
		PopupController controller = NimsPopupFactory.showBsshSearch(dialogStage, searchText);
		HashMap<String,String> info = controller.getData();
		
		if (info != null && info.get("OPPBSSHCD") != null) {
			ctBsshCd.setText(info.get("OPPBSSHCD"));
		}
	}

	@FXML
	public void initialize() {
		btSearch.setOnMouseClicked(searchHandlerM);
		btSearch.setOnKeyPressed(searchHandler);
		
		btClose.setOnKeyPressed(closeHandler);
		btClose.setOnMouseClicked(closeHandlerM);
		
		dpStd.setValue(LocalDate.now());
		apCustom.getChildren().add(ctBsshCd);
		
		cbContainYn.getItems().addAll("포함","미포함");
		cbContainYn.getSelectionModel().selectLast();
		
		mainTable.setOnMouseClicked(event->{
			if (event.getClickCount() == 1) {
				
				JSONObject jObj = mainTable.getSelectionModel().getSelectedItem();
				
				if (jObj == null) return;
				
				StringBuilder sb = new StringBuilder();
				sb.append(jObj.get("RPTR_ENTRPS_NM"));
				sb.append("[").append(jObj.get("BSSH_CD"));
				sb.append("] 업체의 보고번호 : ").append(jObj.get("USR_RPT_ID_NO"));
				lbSelectRow.setText(sb.toString());
				btClose.setDisable(false);
			}
		});
	}

	@Override
	public void setSearchText(String searchText) {
		if (StringUtils.isEmpty(searchText)) return;
		ctBsshCd.setText(searchText);
		handleSearch(btSearch);
	}

	@Override
	public <T> T getData() {
		// 현재 선택된 목록의 사용자식별번호와 보고식별번호가 같은 목록을 searchAllDatas에서 찾아서 리턴해준다.
		if (searchAllDatas == null || isNoneClickClose) return null;
		JSONObject selectObj = mainTable.getSelectionModel().getSelectedItem();
		if (selectObj == null) return null;
		
		String chkBsshCd = (String) selectObj.get("BSSH_CD");
		String chkUsrRptIdNo = (String) selectObj.get("USR_RPT_ID_NO");
		
		if (chkBsshCd == null || chkUsrRptIdNo == null) {
			
			DialogManager.showDialog("알림", null, "선택하신 목록에 상대 취급자 식별번호와 보고자 식별번호가 존재하지 않습니다.");
			
			return null;
		}
		
		// 헤더정보 셋팅
		Header header = new Header();
		header.setOPPBSSHCD(chkBsshCd);
		header.setOPPBSSHNM((String) selectObj.get("RPTR_ENTRPS_NM"));
		header.setOPPSTORGENO((String) selectObj.get("STORGE_NO"));
		header.setUSRRPTIDNO(chkUsrRptIdNo);// 이건 상대업체 보고식별번호를 담아서 나중에 체크시에 사용한다.
		header.setHDRDE((String) selectObj.get("HDR_DE"));// 이건 상대업체 보고시의 처리일자를 담아서 나중에 체크시 사용한다.
		
		ReportSet reportSet = new ReportSet();
		reportSet.getHEADER().add(header);
		
		Lines lines = new Lines();
		List<Line> lineList = lines.getLINE();
		
		for (JSONObject jObj : searchAllDatas) {
			if (chkBsshCd.equals((String)jObj.get("BSSH_CD")) && chkUsrRptIdNo.equals((String)jObj.get("USR_RPT_ID_NO"))){
				Line line = new Line();
				
				// 이동유형코드
				line.setMVMNTYCD("0201"); // 구매입고
				
				line.setPRDUCTCD((String) jObj.get("PRDUCT_CD"));
				line.setMNFNO(jObj.get("MNF_NO") == null ? "" : (String) jObj.get("MNF_NO"));
				line.setMNFSEQ(jObj.get("MNF_SEQ") == null ? "" : (String) jObj.get("MNF_SEQ"));
				line.setPRDVALIDDE(jObj.get("PRD_VALID_DE") == null ? "" : (String) jObj.get("PRD_VALID_DE"));
				line.setSTORGENO("S0001");
				line.setPRDUCTNM((String) jObj.get("PRDUCT_NM"));
				line.setPRDSGTIN(jObj.get("PRD_SGTIN") == null ? "" : (String) jObj.get("PRD_SGTIN"));
				line.setMINDISTBQY(String.valueOf((Long) jObj.get("MIN_DISTB_QY")));
				
				lineList.add(line);
			}
		}
		
		header.setLINES(lines);
		
		PcmRegist pcmRegist = new PcmRegist();
		pcmRegist.setREPORTSET(reportSet);
		
		return (T) pcmRegist;
	}

	@Override
	public void handleSearch(Object source) {
		
		if (source.equals(btSearch)) {
		
			if (checkTaskRun()) return;
			
			Task<Void> task = new Task<Void>() {
			    public Void call() throws Exception {
			    	setTaskRun(true);
			    	doSearch();
			    	setTaskRun(false);
			    	updateProgress(0, 0);
					return null;
			    }
			};
			
			pgBar.progressProperty().bind(task.progressProperty());
			Thread thread = new Thread(task);
			thread.start();
		}
	}
	
	private void doSearch() {
		
		ArrayList<IncomByPurchase> checkData = new ArrayList<IncomByPurchase>();
		
		try {
			IncomByPurchase param = new IncomByPurchase();
			param.setStdDe(dpStd.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			checkData = (ArrayList<IncomByPurchase>) DerbyManager.getInstance().list("IncomByPurchase.selectByStdDe", param);
		}catch(Exception e) {}
		
		
		CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
		boolean isTest = isTestCode.getCodeNm().equals("Y") ? true : false;
		
		String urlParam = "pg=1";
		if (!StringUtils.isEmpty(ctBsshCd.getText())) {
			urlParam += "&fg=2";
		}else {
			urlParam += "&fg=1";
		}
		urlParam += "&bc=" + ctBsshCd.getText();
		urlParam += "&ymd=" + dpStd.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> results = mainClass.doTaskExcute(CALL_TYPE.PURCHASE_PD_INFO,urlParam);
			
			searchAllDatas = new ArrayList<JSONObject>();
			
			// 이미보고된 것이면 목록에서 제외시킨다.
			if (checkData.isEmpty() || cbContainYn.getValue().equals("포함")) {
				searchAllDatas = results;
			}else {
				for (JSONObject jObj : results) {
					if (hasPurchase(checkData,jObj)) continue;
					searchAllDatas.add(jObj);
				}
			}
			
			// 결과 내용에 대한 정렬처리
			Collections.sort(searchAllDatas, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					return ((String)o1.get("USR_RPT_ID_NO")).compareTo((String)o2.get("USR_RPT_ID_NO"));
				}
			});
			
			Platform.runLater(()->{
				if (mainTable.getColumns().isEmpty() && !searchAllDatas.isEmpty()) setTableColumn(searchAllDatas.get(0));
				mainTable.setItems(FXCollections.observableArrayList(searchAllDatas));
				DialogManager.showDialog("알림", null, "총 "+searchAllDatas.size()+"건의 데이터가 조회되었습니다.");
			});
			
		}catch(NimsException e) {
			Platform.runLater(()->{DialogManager.showDialog("알림", null, e.getMessage());});
		}catch(Exception e) {
			Platform.runLater(()->{DialogManager.showExceptionDialog(e, "에러", "구입대상정보 조회 실패", "내용을 확인해주세요.");});
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}

	private boolean hasPurchase(ArrayList<IncomByPurchase> checkData, JSONObject jObj) {
		for (IncomByPurchase obj : checkData) {
			if (obj.getBsshCd().equals((String)jObj.get("BSSH_CD"))
					&& obj.getUsrRptIdNo().equals((String)jObj.get("USR_RPT_ID_NO"))) return true;
		}
		return false;
	}

	@Override
	public void setData(Object obj) {
		return;
	}
	
	@Override
	public void handleRegist(Object source) {
	}

	@Override
	public void handleClose(Object source) {
		if (source.equals(btClose)) isNoneClickClose = false;
		super.handleClose(source);
	}
}
