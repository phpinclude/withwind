package kr.pe.withwind.nims.domain;

public class BarcodeInfo {
	
	/** GS 구분자를 제외한 바코드 읽은 문자 */
	private String barcodeStr;
	/** 제품코드 */
	private String prdCode;
	/** 유효기한 */
	private String prdValidDe;
	/** 제조번호 */
	private String mnfNo;
	/** 일련번호 */
	private String mnfSeq;
	
	/** 제품명 */
	private String prdNm;
	
	public String getBarcodeStr() {
		return barcodeStr;
	}
	public void setBarcodeStr(String barcodeStr) {
		this.barcodeStr = barcodeStr;
	}
	public String getPrdCode() {
		return prdCode;
	}
	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}
	public String getPrdValidDe() {
		return prdValidDe;
	}
	public void setPrdValidDe(String prdValidDe) {
		this.prdValidDe = prdValidDe;
	}
	public String getMnfNo() {
		return mnfNo;
	}
	public void setMnfNo(String mnfNo) {
		this.mnfNo = mnfNo;
	}
	public String getMnfSeq() {
		return mnfSeq;
	}
	public void setMnfSeq(String mnfSeq) {
		this.mnfSeq = mnfSeq;
	}
	@Override
	public String toString() {
		return "BarcodeInfo [barcodeStr=" + barcodeStr + ", prdCode=" + prdCode + ", prdValidDe=" + prdValidDe
				+ ", mnfNo=" + mnfNo + ", mnfSeq=" + mnfSeq + "]";
	}
	public String getPrdNm() {
		return prdNm;
	}
	public void setPrdNm(String prdNm) {
		this.prdNm = prdNm;
	}
}
