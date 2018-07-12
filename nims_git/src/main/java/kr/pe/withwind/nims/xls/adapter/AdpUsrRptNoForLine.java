package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

/**
 * 사용자보고식별번호를 만들어주는 어뎁터
 * @author smpark
 *
 */
public class AdpUsrRptNoForLine implements NimsAdpInf{

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			line.setUSRRPTIDNO(header.getUSRRPTIDNO());
		}catch(Exception e) {
			throw new NimsException("라인의 사용자보고식별번호 생성시 오류가 발생했습니다.");
		}
	}
}