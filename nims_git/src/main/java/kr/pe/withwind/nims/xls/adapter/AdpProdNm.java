package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.ProductManager;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

public class AdpProdNm implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			ProductInfo pInfo = ProductManager.getInstance().getProductInfo(line.getPRDUCTCD());
			line.setPRDUCTNM(pInfo.getProductNm());
		}catch(NimsException e) {
			throw e;
		}catch(Exception e) {
			throw new NimsException("AdpProdNm 오류", e);
		}
	}
}
