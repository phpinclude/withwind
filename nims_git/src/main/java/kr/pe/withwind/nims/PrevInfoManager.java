package kr.pe.withwind.nims;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.pe.withwind.nims.domain.PrevInfo;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class PrevInfoManager {
	
	private static PrevInfoManager prevInfoManager;
	
	private List<PrevInfo> prevInfoList;
	
	private PrevInfoManager() throws DerbyException {
		init();
	}
	
	private void init() throws DerbyException {
		prevInfoList = (List<PrevInfo>) DerbyManager.getInstance().list("PrevInfo.selectAll", null);
	}
	
	public static PrevInfoManager getInstance() throws DerbyException {
		if (prevInfoManager == null) prevInfoManager = new PrevInfoManager();
		return prevInfoManager;
	}
	
	public String getValue(String type) {
		if (StringUtils.isEmpty(type)) return null;
		
		for (PrevInfo info : prevInfoList) {
			if (info.getInfoType().equals(type)) return info.getInfoValue();
		}
		
		return "";
	}
	
	public void setValue(String type, String value) throws DerbyException {
		PrevInfo info = getPrevInfo(type);
		
		if (info == null) {
			// insert;
			info = new PrevInfo();
			info.setInfoType(type);
			info.setInfoValue(value);
			DerbyManager.getInstance().insert("PrevInfo.insert", info);
			prevInfoList.add(info);
		}else {
			info.setInfoValue(value);
			// update;
			DerbyManager.getInstance().update("PrevInfo.updateByPk", info);
		}
	}

	private PrevInfo getPrevInfo(String type) {
		
		if (StringUtils.isEmpty(type)) return null;
		
		for (PrevInfo info : prevInfoList) {
			if (info.getInfoType().equals(type)) return info;
		}
		
		return null;
	}
}
