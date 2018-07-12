package kr.pe.withwind.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kr.pe.withwind.nims.domain.CommonCode;

public class TreeCode extends CommonCode{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private static final long serialVersionUID = 948806025451802507L;
	
	private IntegerProperty level = new SimpleIntegerProperty();
	
	private String naviName;
	private List<TreeCode> childTree;
	private boolean reOrder = true;
	
	protected TreeCode(CommonCode menu, int level, String pName, int ordNo){
		
		if (ordNo < 0) reOrder = false;
		else reOrder = true;
		
		try {
			BeanUtils.copyProperties(this, menu);
			this.setLevel(level);
			if (reOrder) setOrdNo(ordNo);
			this.naviName = "".equals(pName) ? this.getCodeNm() : pName + ">" + this.getCodeNm();
			
			childTree = new ArrayList<TreeCode>();
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
	}
	
	public TreeCode() {
	}

	public void setData(List<CommonCode> dataList){
		int ordNo = 0;
		for (CommonCode obj: dataList){
			if (obj.getPCode().equals(this.getCode())){
				TreeCode treeMenu = new TreeCode(obj, this.getLevel()+1, naviName, reOrder ? ordNo : -1);
				treeMenu.setData(dataList);
				this.childTree.add(treeMenu);
				
				ordNo++;
			}
		}
	}
	
	public void setOrderList(ArrayList<TreeCode> list){
		for (TreeCode data : childTree){
			list.add(data);
			data.setOrderList(list);
		}
	}
	
	public List<TreeCode> getChildTree(){
		return this.childTree;
	}
	
	public String getNaviName() {
		return naviName;
	}

	public void setNaviName(String naviName) {
		this.naviName = naviName;
	}

	public IntegerProperty levelProperty() {
		return this.level;
	}
	

	public int getLevel() {
		return this.levelProperty().get();
	}
	

	public void setLevel(final int level) {
		this.levelProperty().set(level);
	}

	public final IntegerProperty childCntProperty() {
		return new SimpleIntegerProperty(childTree.size());
	}

	public final int getChildCnt() {
		return this.childCntProperty().get();
	}
	

	public final void setChildCnt(final int childCnt) {
		this.childCntProperty().set(childTree.size());
	}

	public final StringProperty hasUpProperty() {
		if (getPCode().equals(TreeCodeManager.TOP_PCODE)) {
			return new SimpleStringProperty("");
		}else {
			return new SimpleStringProperty("[상위코드]");
		}
	}
	

	public final String getHasUp() {
		return this.hasUpProperty().get();
	}
	

	public final void setHasUp(final String hasUp) {
		this.hasUpProperty().set(hasUp);
	}
	
}
