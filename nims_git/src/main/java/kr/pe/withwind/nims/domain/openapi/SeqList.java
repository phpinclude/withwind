package kr.pe.withwind.nims.domain.openapi;

public class SeqList {
	
	private String prductCd;
	private String prductNm;
	private String mnfNo;
	private String prdValidDe;
	private String mnfSeq;
	
	/** 제품코드 */
	public String getPrductCd() {
		return prductCd;
	}
	/** 제품코드 */
	public void setPrductCd(String prductCd) {
		this.prductCd = prductCd;
	}
	/** 제품명 */
	public String getPrductNm() {
		return prductNm;
	}
	/** 제품명 */
	public void setPrductNm(String prductNm) {
		this.prductNm = prductNm;
	}
	/** 제조번호 */
	public String getMnfNo() {
		return mnfNo;
	}
	/** 제조번호 */
	public void setMnfNo(String mnfNo) {
		this.mnfNo = mnfNo;
	}
	/** 일련번호 */
	public String getMnfSeq() {
		return mnfSeq;
	}
	/** 일련번호 */
	public void setMnfSeq(String mnfSeq) {
		this.mnfSeq = mnfSeq;
	}
	
	/** 제품 유효기한 일자 */
	public String getPrdValidDe() {
		return prdValidDe;
	}
	/** 제품 유효기한 일자 */
	public void setPrdValidDe(String prdValidDe) {
		this.prdValidDe = prdValidDe;
	}
	
}