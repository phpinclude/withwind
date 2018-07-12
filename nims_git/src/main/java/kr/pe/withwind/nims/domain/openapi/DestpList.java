package kr.pe.withwind.nims.domain.openapi;

public class DestpList {
	
	private String hdrDe;
	private String oppNrcdHdrIdNo;
	private String oppNrcdEntrpsNm;
	private String prductCd;
	private String prductNm;
	private String mnfNo;
	private String prdValidDe;
	private String mnfSeq;
	private String minDistbQy;
	private String prdMinDistbUnit;
	private String pceQy;
	private String prdPceUnit;
	private String prdSgtin;
	
	/** 취급일자 */
	public String getHdrDe() {
		return hdrDe;
	}
	/** 취급일자 */
	public void setHdrDe(String hdrDe) {
		this.hdrDe = hdrDe;
	}
	/** 상대업체취급자식별코드 */
	public String getOppNrcdHdrIdNo() {
		return oppNrcdHdrIdNo;
	}
	/** 상대업체취급자식별코드 */
	public void setOppNrcdHdrIdNo(String oppNrcdHdrIdNo) {
		this.oppNrcdHdrIdNo = oppNrcdHdrIdNo;
	}
	/** 상대업체명 */
	public String getOppNrcdEntrpsNm() {
		return oppNrcdEntrpsNm;
	}
	/** 상대업체명 */
	public void setOppNrcdEntrpsNm(String oppNrcdEntrpsNm) {
		this.oppNrcdEntrpsNm = oppNrcdEntrpsNm;
	}
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
	/** 최소유통단위수량 - 판매된유통단위수량(마약류는 1, 향정을 n개) */
	public String getMinDistbQy() {
		return minDistbQy;
	}
	/** 최소유통단위수량 - 판매된유통단위수량(마약류는 1, 향정을 n개) */
	public void setMinDistbQy(String minDistbQy) {
		this.minDistbQy = minDistbQy;
	}
	/** 제품최소유통단위 (박스,PTP 등) */
	public String getPrdMinDistbUnit() {
		return prdMinDistbUnit;
	}
	/** 제품최소유통단위 (박스,PTP 등) */
	public void setPrdMinDistbUnit(String prdMinDistbUnit) {
		this.prdMinDistbUnit = prdMinDistbUnit;
	}
	/** 낱개단위 수량 - 판매된 낱개단위수량(원칙적으로 낱개단위 유통은 안됨으로 0) */
	public String getPceQy() {
		return pceQy;
	}
	/** 낱개단위 수량 - 판매된 낱개단위수량(원칙적으로 낱개단위 유통은 안됨으로 0) */
	public void setPceQy(String pceQy) {
		this.pceQy = pceQy;
	}
	/** 제품낱개단위 */
	public String getPrdPceUnit() {
		return prdPceUnit;
	}
	/** 제품낱개단위 */
	public void setPrdPceUnit(String prdPceUnit) {
		this.prdPceUnit = prdPceUnit;
	}
	/** 제품 바코드 */
	public String getPrdSgtin() {
		return prdSgtin;
	}
	/** 제품 바코드 */
	public void setPrdSgtin(String prdSgtin) {
		this.prdSgtin = prdSgtin;
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