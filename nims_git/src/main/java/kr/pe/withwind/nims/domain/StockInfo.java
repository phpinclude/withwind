package kr.pe.withwind.nims.domain;

import java.sql.Timestamp;

public class StockInfo {
	
	// 원 테이블 내용
	private String storageNo = "S0001";
    private String productCd ="";
    private String lotNo = "";
    private String validDe = "99991231";
    private String serialNo = "";
    private String barcodeStr;
    private double pieceCnt = 1d;
    private String reasonCd = "DEF";
    private String usrRptIdNo;
    private Timestamp regDt = new Timestamp(System.currentTimeMillis());
    private Timestamp modDt = new Timestamp(System.currentTimeMillis());
    
    // 부가내용
    private String productNm;
    private String reasonNm;
    private String regDtDp;
    private String modDtDp;
    
    // 검색용 파람
    private String chkZero = "Yes";
    
	public String getStorageNo() {
		return storageNo;
	}
	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getValidDe() {
		return validDe;
	}
	public void setValidDe(String validDe) {
		this.validDe = validDe;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getBarcodeStr() {
		return barcodeStr;
	}
	public void setBarcodeStr(String barcodeStr) {
		this.barcodeStr = barcodeStr;
	}
	public double getPieceCnt() {
		return pieceCnt;
	}
	public void setPieceCnt(double pieceCnt) {
		this.pieceCnt = pieceCnt;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public String getUsrRptIdNo() {
		return usrRptIdNo;
	}
	public void setUsrRptIdNo(String usrRptIdNo) {
		this.usrRptIdNo = usrRptIdNo;
	}
	public Timestamp getRegDt() {
		return regDt;
	}
	public void setRegDt(Timestamp regDt) {
		this.regDt = regDt;
	}
	public Timestamp getModDt() {
		return modDt;
	}
	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}
	public String getProductNm() {
		return productNm;
	}
	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}
	public String getReasonNm() {
		return reasonNm;
	}
	public void setReasonNm(String reasonNm) {
		this.reasonNm = reasonNm;
	}
	public String getRegDtDp() {
		return regDtDp;
	}
	public void setRegDtDp(String regDtDp) {
		this.regDtDp = regDtDp;
	}
	public String getModDtDp() {
		return modDtDp;
	}
	public void setModDtDp(String modDtDp) {
		this.modDtDp = modDtDp;
	}
	public String getChkZero() {
		return chkZero;
	}
	public void setChkZero(String chkZero) {
		this.chkZero = chkZero;
	}
	@Override
	public String toString() {
		return "StockInfo [storageNo=" + storageNo + ", productCd=" + productCd + ", lotNo=" + lotNo + ", validDe="
				+ validDe + ", serialNo=" + serialNo + ", barcodeStr=" + barcodeStr + ", pieceCnt=" + pieceCnt
				+ ", reasonCd=" + reasonCd + ", usrRptIdNo=" + usrRptIdNo + ", regDt=" + regDt + ", modDt=" + modDt
				+ ", productNm=" + productNm + ", reasonNm=" + reasonNm + ", regDtDp=" + regDtDp + ", modDtDp="
				+ modDtDp + "]";
	}
}
