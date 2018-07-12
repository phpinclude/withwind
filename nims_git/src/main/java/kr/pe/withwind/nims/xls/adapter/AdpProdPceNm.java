package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.ProductManager;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

public class AdpProdPceNm implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			ProductInfo pInfo = ProductManager.getInstance().getProductInfo(line.getPRDUCTCD());
			line.setPRDPCEUNIT(pInfo.getPceNm());
		}catch(NimsException e) {
			throw e;
		}catch(Exception e) {
			throw new NimsException("AdpProdPceNm : 제품 낱개단위 셋팅중 오류가 발생하였습니다.", e);
		}
	}
}
