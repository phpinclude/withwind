package kr.pe.withwind.nims.domain;

import java.sql.Timestamp;
import java.util.Date;

import kr.pe.withwind.utils.DateUtils;

public class PatntInfo {
	private String patntNo;
	private String patntNm;
    private String idType;
    private String idNo;
    private String brthdy;
    private String sex;
    private String telNo;
    private String addr;
    private String etc;
    private Timestamp regDt;
    private String delYn;
    private String regDtStr;
    
	public String getPatntNo() {
		return patntNo;
	}
	public void setPatntNo(String patntNo) {
		this.patntNo = patntNo;
	}
	public String getPatntNm() {
		return patntNm;
	}
	public void setPatntNm(String patntNm) {
		this.patntNm = patntNm;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBrthdy() {
		return brthdy;
	}
	public void setBrthdy(String brthdy) {
		this.brthdy = brthdy;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public Timestamp getRegDt() {
		return regDt;
	}
	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegDtStr() {
		if (regDt == null) return DateUtils.getFormatted("yyyy/MM/dd HH:mm:ss");
		return DateUtils.getFormatted("yyyy/MM/dd HH:mm:ss",new Date(regDt.getTime()));
	}
	public void setRegDtStr(String regDtStr) {
		this.regDtStr = regDtStr;
	}
	@Override
	public String toString() {
		return "PatntInfo [patntNo=" + patntNo + ", patntNm=" + patntNm + ", idType=" + idType + ", idNo=" + idNo
				+ ", brthdy=" + brthdy + ", sex=" + sex + ", telNo=" + telNo + ", addr=" + addr + ", etc=" + etc
				+ ", regDt=" + regDt + ", delYn=" + delYn + ", regDtStr=" + regDtStr + "]";
	}
	
	
}