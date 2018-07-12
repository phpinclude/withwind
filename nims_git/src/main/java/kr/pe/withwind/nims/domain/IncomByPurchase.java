package kr.pe.withwind.nims.domain;

import java.sql.Timestamp;

public class IncomByPurchase {
	
	private String bsshCd;
	private String usrRptIdNo;
	private String stdDe;
	private Timestamp regDt;
	
	public String getBsshCd() {
		return bsshCd;
	}
	public void setBsshCd(String bsshCd) {
		this.bsshCd = bsshCd;
	}
	public String getUsrRptIdNo() {
		return usrRptIdNo;
	}
	public void setUsrRptIdNo(String usrRptIdNo) {
		this.usrRptIdNo = usrRptIdNo;
	}
	public String getStdDe() {
		return stdDe;
	}
	public void setStdDe(String stdDe) {
		this.stdDe = stdDe;
	}
	public Timestamp getRegDt() {
		return regDt;
	}
	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	@Override
	public String toString() {
		return "IncomByPurchase [bsshCd=" + bsshCd + ", usrRptIdNo=" + usrRptIdNo + ", stdDe=" + stdDe + ", regDt="
				+ regDt + "]";
	}
}