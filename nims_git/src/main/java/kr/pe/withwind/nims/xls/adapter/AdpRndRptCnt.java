package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

/**
 * 수불상세보고수 셋팅 어뎁터
 * @author smpark
 *
 */
public class AdpRndRptCnt implements NimsAdpInf {

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			int cnt = header.getLINES().getLINE().size();
			header.setRNDDTLRPTCNT(String.valueOf(cnt));
		}catch(Exception e) {
			throw new NimsException("수불상세보고수 셋팅시 오류가 발생했습니다.");
		}
	}
}
