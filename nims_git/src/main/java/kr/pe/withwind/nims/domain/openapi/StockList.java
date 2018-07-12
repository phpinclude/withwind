package kr.pe.withwind.nims.domain.openapi;

public class StockList {

	private String storgeNo;
	private String storgeNm;
	private String prductCd;
	private String prductNm;
	private String mnfNo;
	private String prdValidDe;
	private String mnfSeq;
	private String minDistbInvtQy;
	private String prdMinDistbUnit;
	private String pceInvtQy;
	private String prdPceUnit;
	private String ddlnYm;

	
	/** 최소유통단위 재고수량 */
	public String getMinDistbInvtQy() {
		return minDistbInvtQy;
	}
	/** 최소유통단위 재고수량 */
	public void setMinDistbInvtQy(String minDistbInvtQy) {
		this.minDistbInvtQy = minDistbInvtQy;
	}
	/** 낱개단위 재고수량 */
	public String getPceInvtQy() {
		return pceInvtQy;
	}
	/** 낱개단위 재고수량 */
	public void setPceInvtQy(String pceInvtQy) {
		this.pceInvtQy = pceInvtQy;
	}
	/** 재고기준월 */
	public String getDdlnYm() {
		return ddlnYm;
	}
	/** 재고기준월 */
	public void setDdlnYm(String ddlnYm) {
		this.ddlnYm = ddlnYm;
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
	
	/** 제품최소유통단위 (박스,PTP 등) */
	public String getPrdMinDistbUnit() {
		return prdMinDistbUnit;
	}
	/** 제품최소유통단위 (박스,PTP 등) */
	public void setPrdMinDistbUnit(String prdMinDistbUnit) {
		this.prdMinDistbUnit = prdMinDistbUnit;
	}
	/** 제품낱개단위 */
	public String getPrdPceUnit() {
		return prdPceUnit;
	}
	/** 제품낱개단위 */
	public void setPrdPceUnit(String prdPceUnit) {
		this.prdPceUnit = prdPceUnit;
	}
	/** 저장소번호(판매자) */
	public String getStorgeNo() {
		return storgeNo;
	}
	/** 저장소번호(판매자) */
	public void setStorgeNo(String storgeNo) {
		this.storgeNo = storgeNo;
	}
	/** 저장소명(판매자) */
	public String getStorgeNm() {
		return storgeNm;
	}
	/** 저장소명(판매자) */
	public void setStorgeNm(String storgeNm) {
		this.storgeNm = storgeNm;
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
