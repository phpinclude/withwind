package kr.pe.withwind.nims.xls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.pe.withwind.nims.domain.XlsMapping;
import kr.pe.withwind.nims.utils.APIConstants;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class XlsVoMappingManager {
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	private static XlsVoMappingManager manager;

	private HashMap<String, List<XlsMapping>> xlsMapList;

	private XlsVoMappingManager() {
		init();
	}

	public static XlsVoMappingManager getInstance() {
		if (manager == null)
			manager = new XlsVoMappingManager();
		return manager;
	}

	public void init() {
		xlsMapList = new HashMap<String, List<XlsMapping>>();
		setXlsMapList();
	}

	private void setXlsMapList() {

		try {
			List<XlsMapping> list = (List<XlsMapping>) DerbyManager.getInstance().list("XlsMapping.getXlsMapping",null);

			for (XlsMapping data : list) {
				if (!xlsMapList.containsKey(data.getRptType())) {
					xlsMapList.put(data.getRptType().toUpperCase(), new ArrayList<XlsMapping>());
				}
				xlsMapList.get(data.getRptType().toUpperCase()).add(data);
			}

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}

	}

	public List<XlsMapping> getMappingList(String reportType, String eleType) {

		try {
			if (reportType == null || eleType == null)
				return null;

			if (!xlsMapList.containsKey(reportType)) {
				List<XlsMapping> list = (List<XlsMapping>) DerbyManager.getInstance().list("XlsMapping.selectByRptType",reportType);
				xlsMapList.put(reportType, list);
			}

			ArrayList<XlsMapping> reValue = new ArrayList<XlsMapping>();
			List<XlsMapping> sourceList = xlsMapList.get(reportType);

			for (XlsMapping source : sourceList) {
				if (eleType.equals(source.getHlType()))
					reValue.add(source);
			}

			return reValue;

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}

		return null;
	}

	public int delete(XlsMapping xlsMapping) throws DerbyException {

		int reValue = 0;
		reValue = DerbyManager.getInstance().delete("XlsMapping.deleteXlsMapping", xlsMapping);
		xlsMapList.remove(xlsMapping.getRptType());

		return reValue;
	}

	public int update(XlsMapping xlsMapping) throws DerbyException {

		int reValue = 0;
		reValue = DerbyManager.getInstance().update("XlsMapping.updateXlsMapping", xlsMapping);
		xlsMapList.remove(xlsMapping.getRptType());
		
		logger.debug("update " + xlsMapping.getRptType());

		return reValue;
	}

	public int insert(XlsMapping xlsMapping) throws DerbyException {
		int reValue = 0;
		reValue = DerbyManager.getInstance().insert("XlsMapping.insertXlsMapping", xlsMapping);
		xlsMapList.remove(xlsMapping.getRptType());
		return reValue;
	}
}