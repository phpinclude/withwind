package kr.pe.withwind.nims;

import java.util.List;

import kr.pe.withwind.nims.domain.BsshSetting;
import kr.pe.withwind.utils.DerbyManager;

public class BsshInfoManager {
	
	public enum TYPE{
		TEST("test")
		,REAL("real");
		
		final private String name;
		
		private TYPE(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name();
		}
	};
	
	private static BsshInfoManager biManager;
	
	private BsshSetting bsshInfoTest;
	private BsshSetting bsshInfoReal;
	
	private BsshInfoManager() {
		try {
			bsshInfoTest = DerbyManager.getInstance().listOne("BsshSetting.selectByPk", TYPE.TEST.toString());
			bsshInfoReal = DerbyManager.getInstance().listOne("BsshSetting.selectByPk", TYPE.REAL.toString());
			
			if (bsshInfoTest == null) bsshInfoTest = new BsshSetting();
			if (bsshInfoReal == null) bsshInfoReal = new BsshSetting();
			
		}catch(Exception e) {
			bsshInfoTest = new BsshSetting();
			bsshInfoReal = new BsshSetting();
		}
	}
	
	public void setBsshInfo(BsshSetting bsshInfo) throws NimsException {
		try {
			if (bsshInfo.getInfType().equals(TYPE.REAL.toString())) {
				this.bsshInfoReal = bsshInfo;
				if (DerbyManager.getInstance().update("BsshSetting.updateByPk", bsshInfo) < 1) {
					DerbyManager.getInstance().insert("BsshSetting.insert", bsshInfo);
				}
			}else if (bsshInfo.getInfType().equals(TYPE.TEST.toString())) {
				this.bsshInfoTest = bsshInfo;
				if (DerbyManager.getInstance().update("BsshSetting.updateByPk", bsshInfo) < 1) {
					DerbyManager.getInstance().insert("BsshSetting.insert", bsshInfo);
				}
			}
		}catch(Exception e) {
			throw new NimsException("기초정보 저장 오류",e);
		}
	}
	
	public static BsshInfoManager getInstance() {
		if (biManager == null) biManager = new BsshInfoManager();
		
		return biManager;
	}
	
	public BsshSetting getBsshInfo(TYPE type) throws NimsException {
		switch (type) {
		case TEST:
			return bsshInfoTest;
		case REAL:
			return bsshInfoReal;
		}
		
		throw new NimsException("운영정보를 확인 할 수 없습니다.");
	}
}
