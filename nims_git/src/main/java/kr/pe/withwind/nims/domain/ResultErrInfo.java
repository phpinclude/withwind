package kr.pe.withwind.nims.domain;

public class ResultErrInfo {
	private String bsshCd;
	private String usrRptIdNo;
	private int idx;
	private String errorCd;
	private String errorMsg;
	
	
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
	public String getErrorCd() {
		return errorCd;
	}
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	@Override
	public String toString() {
		return "ResultErrInfo [bsshCd=" + bsshCd + ", usrRptIdNo=" + usrRptIdNo + ", idx=" + idx + ", errorCd="
				+ errorCd + ", errorMsg=" + errorMsg + "]";
	}
}
