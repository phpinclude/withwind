package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.ProductManager;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;
import kr.pe.withwind.utils.DerbyManager;

public class AdpProdQty implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			ProductInfo pInfo = ProductManager.getInstance().getProductInfo(line.getPRDUCTCD());
			line.setPRDMINDISTBQY(pInfo.getMinQty());
		}catch(NimsException e) {
			throw e;
		}catch(Exception e) {
			throw new NimsException("AdpProdQty : 제품최소유통수량 셋팅중 오류가 발생하였습니다.", e);
		}
	}
}
