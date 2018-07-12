package kr.pe.withwind.nims.domain;

import java.sql.Timestamp;
import java.util.Date;

import kr.pe.withwind.utils.DateUtils;

public class DissCode {
	private String dissSeq;
	private String kcdReformOdr;
	private String dissCd;
	private String upperDissCd;
	private String dissNm;
	private String dissNmEn;
	private Timestamp regDt;
	private String regDtStr;
	public String getDissSeq() {
		return dissSeq;
	}
	public void setDissSeq(String dissSeq) {
		this.dissSeq = dissSeq;
	}
	public String getKcdReformOdr() {
		return kcdReformOdr;
	}
	public void setKcdReformOdr(String kcdReformOdr) {
		this.kcdReformOdr = kcdReformOdr;
	}
	public String getDissCd() {
		return dissCd;
	}
	public void setDissCd(String dissCd) {
		this.dissCd = dissCd;
	}
	public String getUpperDissCd() {
		return upperDissCd;
	}
	public void setUpperDissCd(String upperDissCd) {
		this.upperDissCd = upperDissCd;
	}
	public String getDissNm() {
		return dissNm;
	}
	public void setDissNm(String dissNm) {
		this.dissNm = dissNm;
	}
	public String getDissNmEn() {
		return dissNmEn;
	}
	public void setDissNmEn(String dissNmEn) {
		this.dissNmEn = dissNmEn;
	}
	public Timestamp getRegDt() {
		return regDt;
	}
	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	public String getRegDtStr() {
		if (regDt == null) return DateUtils.getFormatted("yyyy/MM/dd HH:mm:ss");
		return DateUtils.getFormatted("yyyy/MM/dd HH:mm:ss",new Date(regDt.getTime()));
	}
	public void setRegDtStr(String regDtStr) {
		this.regDtStr = regDtStr;
	}
}
