package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.ProductManager;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

public class AdpProdPceCnt implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			ProductInfo pInfo = ProductManager.getInstance().getProductInfo(line.getPRDUCTCD());
			line.setPRDTOTPCEQY(pInfo.getPceCnt());
		}catch(NimsException e) {
			throw e;
		}catch(Exception e) {
			throw new NimsException("제품총낱개단위 수량 셋팅 오류", e);
		}
	}

}
