package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.ProductManager;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.utils.DateUtils;

public class AdpFileDtL implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			line.setFILECREATDT(header.getFILECREATDT());
		}catch(Exception e) {
			throw new NimsException("라인 파일생성일시 셋팅시 오류가 발생하였습니다.", e);
		}
	}
}
