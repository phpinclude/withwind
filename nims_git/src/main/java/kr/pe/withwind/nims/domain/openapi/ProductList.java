package kr.pe.withwind.nims.domain.openapi;

public class ProductList {
	
	private String prductCd;
	private String prductNm;
	private String nrcdSeNm;
	private String prtmSeNm;
	private String prdMinDistbQy;
	private String stdPackngStleNm;
	private String pceCo;
	private String pceCoUnitNm;
	private String rgsDt;
	private String updDt;
	
	/** 마약/향정 구분명 */
	public String getNrcdSeNm() {
		return nrcdSeNm;
	}
	/** 마약/향정 구분명 */
	public void setNrcdSeNm(String nrcdSeNm) {
		this.nrcdSeNm = nrcdSeNm;
	}
	/** 중점/일반 구분 */
	public String getPrtmSeNm() {
		return prtmSeNm;
	}
	/** 중점/일반 구분 */
	public void setPrtmSeNm(String prtmSeNm) {
		this.prtmSeNm = prtmSeNm;
	}
	/** 제품최소유통단위 수량 */
	public String getPrdMinDistbQy() {
		return prdMinDistbQy;
	}
	/** 제품최소유통단위 수량 */
	public void setPrdMinDistbQy(String prdMinDistbQy) {
		this.prdMinDistbQy = prdMinDistbQy;
	}
	/** 제품최소 유통단위 */
	public String getStdPackngStleNm() {
		return stdPackngStleNm;
	}
	/** 제품최소 유통단위 */
	public void setStdPackngStleNm(String stdPackngStleNm) {
		this.stdPackngStleNm = stdPackngStleNm;
	}
	/** 제품낱개단위 수량 */
	public String getPceCo() {
		return pceCo;
	}
	/** 제품낱개단위 수량 */
	public void setPceCo(String pceCo) {
		this.pceCo = pceCo;
	}
	/** 제품낱개단위 */
	public String getPceCoUnitNm() {
		return pceCoUnitNm;
	}
	/** 제품낱개단위 */
	public void setPceCoUnitNm(String pceCoUnitNm) {
		this.pceCoUnitNm = pceCoUnitNm;
	}
	/** 등록일 */
	public String getRgsDt() {
		return rgsDt;
	}
	/** 등록일 */
	public void setRgsDt(String rgsDt) {
		this.rgsDt = rgsDt;
	}
	/** 변경일 */
	public String getUpdDt() {
		return updDt;
	}
	/** 변경일 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
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
}
