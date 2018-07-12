package kr.pe.withwind.nims.domain.openapi;

public class OfficeList {
	
	private String ofCd;
	private String ofNm;
	private String upOfNm;
	private String topOfNm;
	private String bassAdres;
	private String bassDtlAdres;
	
	/** 기관코드 */
	public String getOfCd() {
		return ofCd;
	}
	/** 기관코드 */
	public void setOfCd(String ofCd) {
		this.ofCd = ofCd;
	}
	/** 기관명 */
	public String getOfNm() {
		return ofNm;
	}
	/** 기관명 */
	public void setOfNm(String ofNm) {
		this.ofNm = ofNm;
	}
	/** 상위 기관명 */
	public String getUpOfNm() {
		return upOfNm;
	}
	/** 상위 기관명 */
	public void setUpOfNm(String upOfNm) {
		this.upOfNm = upOfNm;
	}
	/** 최상위 기관명 */
	public String getTopOfNm() {
		return topOfNm;
	}
	/** 최상위 기관명 */
	public void setTopOfNm(String topOfNm) {
		this.topOfNm = topOfNm;
	}
	/** 기본주소 */
	public String getBassAdres() {
		return bassAdres;
	}
	/** 기본주소 */
	public void setBassAdres(String bassAdres) {
		this.bassAdres = bassAdres;
	}
	/** 상세주소 */
	public String getBassDtlAdres() {
		return bassDtlAdres;
	}
	/** 상세주소 */
	public void setBassDtlAdres(String bassDtlAdres) {
		this.bassDtlAdres = bassDtlAdres;
	}
	
}