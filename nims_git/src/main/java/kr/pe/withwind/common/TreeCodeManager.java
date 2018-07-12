package kr.pe.withwind.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.utils.DerbyManager;


public class TreeCodeManager {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	public static final String TOP_PCODE = "ROOT";
	public static final String DEFAULT_CODE_TYPE = "C";
	
	private static TreeCodeManager treeCodeManager;
	
	private ArrayList<TreeCode> rootCodeList;
	private ArrayList<TreeCode> orderedCodeList;
	
	private TreeCodeManager(){
	}
	
	public static TreeCodeManager getInstance() {
		if (treeCodeManager == null) treeCodeManager = new TreeCodeManager();
		return treeCodeManager;
	}
	
	public TreeCode getCodeInfo(String code) {
		for (TreeCode treeCode : orderedCodeList){
			if (code.equals(treeCode.getCode())){
				return treeCode;
			}
		}
		
		return null;
	}
	
	public TreeCode getCodeInfo(String pCode, String code) {
		for (TreeCode treeCode : orderedCodeList){
			if (pCode.equals(treeCode.getpCode()) && code.equals(treeCode.getCode())){
				return treeCode;
			}
		}
		
		return null;
	}
	
	public List<TreeCode> getChildCodeList(String code){
		
		if (orderedCodeList == null) init();
		
		if (code == null || "".equals(code.trim())) code = TOP_PCODE;
		
		if (TOP_PCODE.equals(code)) return rootCodeList;
		
		for (TreeCode treeCode : orderedCodeList){
			if (code.equals(treeCode.getCode())){
				return treeCode.getChildTree();
			}
		}
		
		return new ArrayList<TreeCode>();
	}
	
	public List<TreeCode> getChildCodeList(String pCode, String code){
		
		if (orderedCodeList == null) init();
		
		if (code == null || "".equals(code.trim())) code = TOP_PCODE;
		
		if (TOP_PCODE.equals(code)) return rootCodeList;
		
		for (TreeCode treeCode : orderedCodeList){
			if (pCode.equals(treeCode.getpCode()) && code.equals(treeCode.getCode())){
				return treeCode.getChildTree();
			}
		}
		
		return new ArrayList<TreeCode>();
	}
	
	public List<TreeCode> getRootCodeList(){
		if (orderedCodeList == null) init();
		return rootCodeList;
	}
	
	public int updateCode(CommonCode code) throws Exception{
		
		int reValue = DerbyManager.getInstance().update("Mapper.SysCode.update",code);
		init();
		
		return reValue;
	}
	
	public int deleteCode(CommonCode code) throws Exception{
		
		TreeCode chkCode = getTreeCode(code.getCode());
		
		if (!chkCode.getChildTree().isEmpty()) return -1;
		
		int reValue = DerbyManager.getInstance().delete("Mapper.SysCode.delete", chkCode);
		init();
		
		return reValue;
	}

	private TreeCode getTreeCode(String pCode, String code) {
		
		for (TreeCode treeCode : orderedCodeList){
			if (pCode.equals(treeCode.getpCode()) && code.equals(treeCode.getCode())){
				return treeCode;
			}
		}
		
		return null;
	}
	
	private TreeCode getTreeCode(String code) {
		
		for (TreeCode treeCode : orderedCodeList){
			if (treeCode.getCode().equals(code)) return treeCode;
			
		}
		
		return null;
	}

	public int insertCode(CommonCode code) throws Exception{
		if (orderedCodeList == null) init();
		
		if (code.getPCode() == null) code.setPCode(TOP_PCODE);
		if (code.getCodeType() == null) code.setCodeType(DEFAULT_CODE_TYPE);
		if (code.getUseYn() == null) code.setUseYn("Y");
		
		List<TreeCode> childList = null;
		if (TOP_PCODE.equals(code.getPCode())){
			childList = getRootCodeList();
		}else{
			// 최상위가 아니면서 P_code가 code에등록되어 있지 않으면 등록불가
			if (!hasCode(code.getPCode())) return -1;
			childList = getChildCodeList(code.getPCode());
		}
		
		int reValue = -1;
		
		logger.debug(code.toString());
		
//		reValue = DerbyManager.getInstance().update("Mapper.SysCode.update",code);
//		
//		if (reValue != 1) {
			reValue = DerbyManager.getInstance().insert("Mapper.SysCode.insert",code);
//		}
		
		init();
		
		return reValue;
	}
	
	private boolean hasCode(String pCode) {
		for (TreeCode obj: orderedCodeList){
			if (pCode.equals(obj.getCode())) return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private synchronized void init(){
		
		try{
			CommonCode param = new CommonCode();
			List<CommonCode> dataList = (List<CommonCode>) DerbyManager.getInstance().list("Mapper.SysCode.selectAll",param);
			
			rootCodeList = new ArrayList<TreeCode>();
			int ordNo = -1;
			for (CommonCode data : dataList){
				if (TOP_PCODE.equals(data.getPCode())){
					TreeCode treeCode = new TreeCode(data, 0, "",ordNo);
					treeCode.setData(dataList);
					rootCodeList.add(treeCode);
					if (ordNo > -1) ordNo++;
				}
			}
			
			orderedCodeList = new ArrayList<TreeCode>();
			for (TreeCode data : rootCodeList){
				orderedCodeList.add(data);
				data.setOrderList(orderedCodeList);
			}
			
//			for (TreeCode data : orderedCodeList){
//				logger.debug(">>>>> P_CODE["+data.getPCode()+"] CODE["+data.getCode()+"] LEVEL[" + data.getLevel() + "] ORDER["+data.getOrdNo()+"] naviname : " + data.getNaviName());
//			}
			
		}catch(Exception e){
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
	}
}