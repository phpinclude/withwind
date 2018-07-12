package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.ProductManager;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

public class AdpMdcAftDsuseUnit implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			ProductInfo pInfo = ProductManager.getInstance().getProductInfo(line.getPRDUCTCD());
			line.setMDCAFTDSUSEUNIT(pInfo.getPceNm());
		}catch(NimsException e) {
			throw e;
		}catch(Exception e) {
			throw new NimsException("AdpMdcAftDsuseUnit : 조제투약후 폐기량 단위 셋팅 오류", e);
		}
	}
}
