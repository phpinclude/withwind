package kr.pe.withwind.nims.xls.adapter;

import java.security.SecureRandom;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

/**
 * 사용자보고식별번호를 만들어주는 어뎁터
 * @author smpark
 *
 */
public class AdpUsrRptNo implements NimsAdpInf{

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			SecureRandom sr = new SecureRandom();
			String usrRptIdNo = header.getRPTSECD() + System.currentTimeMillis();
			header.setUSRRPTIDNO(usrRptIdNo);
		}catch(Exception e) {
			throw new NimsException("사용자보고식별번호 생성시 오류가 발생했습니다.");
		}
	}
}
