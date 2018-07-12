package kr.pe.withwind.nims.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import kr.pe.withwind.utils.DateUtils;

public class UploadResult {
	private String uid;
	private String bsshCd;
	private String rptTyCd;
	private String rptTyCdNm;
	private String status;
	private String statusNm;
	private String rptSeCd;
	private String rptSeCdNm;
	private String usrRptIdNo;
	private String resultCd;
	private String resultMsg;
	private String rptRceptNo;
	private ArrayList<ResultErrInfo> errInfoList;
	private String rptFileName;
	private Timestamp regDt;
	
	private Timestamp searchSt;
	private Timestamp searchEn;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getBsshCd() {
		return bsshCd;
	}
	public void setBsshCd(String bsshCd) {
		this.bsshCd = bsshCd;
	}
	public String getRptTyCd() {
		return rptTyCd;
	}
	public void setRptTyCd(String rptTyCd) {
		this.rptTyCd = rptTyCd;
	}
	public String getRptTyCdNm() {
		return rptTyCdNm;
	}
	public void setRptTyCdNm(String rptTyCdNm) {
		this.rptTyCdNm = rptTyCdNm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusNm() {
		return statusNm;
	}
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}
	public String getRptSeCd() {
		return rptSeCd;
	}
	public void setRptSeCd(String rptSeCd) {
		this.rptSeCd = rptSeCd;
	}
	public String getRptSeCdNm() {
		return rptSeCdNm;
	}
	public void setRptSeCdNm(String rptSeCdNm) {
		this.rptSeCdNm = rptSeCdNm;
	}
	public String getUsrRptIdNo() {
		return usrRptIdNo;
	}
	public void setUsrRptIdNo(String usrRptIdNo) {
		this.usrRptIdNo = usrRptIdNo;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getRptRceptNo() {
		return rptRceptNo;
	}
	public void setRptRceptNo(String rptRceptNo) {
		this.rptRceptNo = rptRceptNo;
	}
	public ArrayList<ResultErrInfo> getErrInfoList() {
		if (errInfoList == null) errInfoList = new ArrayList<ResultErrInfo>();
		return errInfoList;
	}
	public void addErrInfoList(ResultErrInfo errInfo) {
		getErrInfoList().add(errInfo);
	}

	public String getRptFileName() {
		return rptFileName;
	}
	public void setRptFileName(String rptFileName) {
		this.rptFileName = rptFileName;
	}

	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	
	public String getRegDt() {
		return regDt.toString();
	}
	
	public Timestamp getSearchSt() {
		return searchSt;
	}
	public void setSearchSt(Timestamp searchSt) {
		this.searchSt = searchSt;
	}
	public Timestamp getSearchEn() {
		return searchEn;
	}
	public void setSearchEn(Timestamp searchEn) {
		this.searchEn = searchEn;
	}
	@Override
	public String toString() {
		return "UploadResult [uid=" + uid + ", bsshCd=" + bsshCd + ", rptTyCd=" + rptTyCd + ", rptTyCdNm=" + rptTyCdNm
				+ ", status=" + status + ", statusNm=" + statusNm + ", rptSeCd=" + rptSeCd + ", rptSeCdNm=" + rptSeCdNm
				+ ", usrRptIdNo=" + usrRptIdNo + ", resultCd=" + resultCd + ", resultMsg=" + resultMsg + ", rptRceptNo="
				+ rptRceptNo + ", errInfoList=" + errInfoList + ", rptFileName=" + rptFileName + ", regDt=" + regDt
				+ ", searchSt=" + searchSt + ", searchEn=" + searchEn + "]";
	}
}
