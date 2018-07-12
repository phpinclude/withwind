package kr.pe.withwind.nims.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.nims.customnode.CustomTextField;
import kr.pe.withwind.nims.customnode.NodeBindMap;
import kr.pe.withwind.nims.domain.CommonCode;

public class NimsBindNodeFactory {
	
	private BindNodeEventHandler bindNodeHandler;
	
	public NimsBindNodeFactory(BindNodeEventHandler handler) {
		this.bindNodeHandler = handler;
	}
	
	public Node getBindNode(NodeBindMap headerMap, String fieldNm) {
		return getBindNode(headerMap, fieldNm, "");
	}
	
	public Node getBindNode(NodeBindMap bindMap, String fieldNm, List<TreeCode> list) {
		
		ComboBox<CommonCode> temp = new ComboBox<CommonCode>(FXCollections.observableArrayList(list));
		temp.getSelectionModel().selectFirst();
		temp.setUserData(bindMap);
		temp.valueProperty().addListener((observable, oldValue, newValue ) -> {
			HashMap<String,String> userData = (HashMap<String, String>) temp.getUserData();
			userData.put(fieldNm, newValue.getCode());
		});
		
		bindMap.addNode(fieldNm, temp);
		bindMap.setValue(fieldNm, list.get(0).getCode());
		return temp;
	}
	
	public Node getBindNode(NodeBindMap bindMap, String fieldNm, String value) {
		
		bindMap.setValue(fieldNm, value);
		
		if ("HDRDE".equals(fieldNm)) {
			DatePicker temp = new DatePicker();
			temp.setUserData(bindMap);
			temp.valueProperty().addListener((observable, oldValue, newValue)->{
				HashMap<String,String> userData = (HashMap<String, String>) temp.getUserData();
				userData.put(fieldNm, newValue.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
			});
			
			bindMap.addNode(fieldNm, temp);
			
			if (value.length() == 8) temp.setValue(LocalDate.of(Integer.parseInt(value.substring(0, 4)), Integer.parseInt(value.substring(4, 2)), Integer.parseInt(value.substring(6, 2))));
			else temp.setValue(LocalDate.now());
			
			return temp;
		}else if ("OPPBSSHNM".equals(fieldNm) || "PRDUCTNM".equals(fieldNm)
				|| "MDCDISSCODE".equals(fieldNm) || "MDCPATNM".equals(fieldNm)){
			CustomTextField temp = new CustomTextField(fieldNm);
			Button btn = new Button("검색");
			btn.setOnMouseClicked(event->{
				if (bindNodeHandler != null) bindNodeHandler.bindNodeHandle(bindMap, fieldNm);
			});
			
			temp.setRightNode(btn);
			temp.setUserData(bindMap,fieldNm);
			temp.setEnterHandler(bindNodeHandler);
			
			bindMap.addNode(fieldNm, temp);
			if (!StringUtils.isEmpty(value)) bindMap.setValue(fieldNm, value);
			
			return temp;
		}else {
			TextField temp = new TextField();
			
			temp.setUserData(bindMap);
			temp.textProperty().addListener((observable, oldValue, newValue ) -> {
				HashMap<String,String> userData = (HashMap<String, String>) temp.getUserData();
				userData.put(fieldNm, newValue);
			});
			
			bindMap.addNode(fieldNm, temp);
			if (!StringUtils.isEmpty(value)) bindMap.setValue(fieldNm, value);
			return temp;
		}
	}
}
