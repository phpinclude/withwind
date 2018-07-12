package kr.pe.withwind.nims.domain.openapi;

public class AcceptList {
	
	private String hdrDe;
	private String bsshCd;
	private String rptrEntrpsNm;
	private String prductCd;
	private String prductNm;
	private String mnfNo;
	private String mnfSeq;
	private String minDistbQy;
	private String prdMinDistbUnit;
	private String pceQy;
	private String prdPceUnit;
	private String storgeNo;
	private String storgeNm;
	private String hptlNo;
	private String prdSgtin;
	private String prdValidDe;
	private String tniRptAt;
	private String usrRptIdNo;
	private String chrgNm;
	private String chrgTelNo;
	
	/** 요양기관번호 */
	public String getHptlNo() {
		return hptlNo;
	}
	/** 요양기관번호 */
	public void setHptlNo(String hptlNo) {
		this.hptlNo = hptlNo;
	}
	/** 양수보고존재여부 */
	public String getTniRptAt() {
		return tniRptAt;
	}
	/** 양수보고존재여부 */
	public void setTniRptAt(String tniRptAt) {
		this.tniRptAt = tniRptAt;
	}
	/** 취급일자 */
	public String getHdrDe() {
		return hdrDe;
	}
	/** 취급일자 */
	public void setHdrDe(String hdrDe) {
		this.hdrDe = hdrDe;
	}
	/** 판매업체식별코드(마약류취급자식별코드) */
	public String getBsshCd() {
		return bsshCd;
	}
	/** 판매업체식별코드(마약류취급자식별코드) */
	public void setBsshCd(String bsshCd) {
		this.bsshCd = bsshCd;
	}
	/** 판매업체명 */
	public String getRptrEntrpsNm() {
		return rptrEntrpsNm;
	}
	/** 판매업체명 */
	public void setRptrEntrpsNm(String rptrEntrpsNm) {
		this.rptrEntrpsNm = rptrEntrpsNm;
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
	/** 최소유통단위수량 - 판매자가 나에게 최소유통단위 기준으로 몇개를 팔았는가(마약류는 1, 향정을 n개) */
	public String getMinDistbQy() {
		return minDistbQy;
	}
	/** 최소유통단위수량 - 판매자가 나에게 최소유통단위 기준으로 몇개를 팔았는가(마약류는 1, 향정을 n개) */
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
	/** 낱개단위 수량 - 판매자가 나에게 낱개단위로 몇개를 팔았는가?(원칙적으로 낱개단위 유통은 안됨으로 0) */
	public String getPceQy() {
		return pceQy;
	}
	/** 낱개단위 수량 - 판매자가 나에게 낱개단위로 몇개를 팔았는가?(원칙적으로 낱개단위 유통은 안됨으로 0) */
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
	/** 사용자보고 식별번호(판매자) */
	public String getUsrRptIdNo() {
		return usrRptIdNo;
	}
	/** 사용자보고 식별번호(판매자) */
	public void setUsrRptIdNo(String usrRptIdNo) {
		this.usrRptIdNo = usrRptIdNo;
	}
	/** 담당자명(판매자) */
	public String getChrgNm() {
		return chrgNm;
	}
	/** 담당자명(판매자) */
	public void setChrgNm(String chrgNm) {
		this.chrgNm = chrgNm;
	}
	/** 담당자전화번호(판매자) */
	public String getChrgTelNo() {
		return chrgTelNo;
	}
	/** 담당자전화번호(판매자) */
	public void setChrgTelNo(String chrgTelNo) {
		this.chrgTelNo = chrgTelNo;
	}
}
