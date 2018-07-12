package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.utils.RSAEncrypt;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

public class AdpPatIdNoEncode implements NimsAdpInf{

	@Override
	public void getData(Header header, Line line, String param) throws NimsException {
		try {
			
			TreeCode code = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE", "IS_TEST");
			
			String pubKey = "";
			
			if (code.getCode().equalsIgnoreCase("Y")) {
				pubKey = BsshInfoManager.getInstance().getBsshInfo(TYPE.TEST).getPublicKey();
			}else {
				pubKey = BsshInfoManager.getInstance().getBsshInfo(TYPE.REAL).getPublicKey();
			}
			
			header.setMDCPATIDNO(RSAEncrypt.EncryptRsa(param,pubKey));
		}catch(Exception e) {
			throw new NimsException("AdpPatIdNoEncode : 환자식별번호 암호화중 오류가 발생하였습니다.", e);
		}
	}
}
