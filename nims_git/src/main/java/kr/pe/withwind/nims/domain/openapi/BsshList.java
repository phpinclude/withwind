package kr.pe.withwind.nims.domain.openapi;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BsshList {
	/** 마약류취급자식별번호 */
	private StringProperty bsshCd = new SimpleStringProperty();
	/** 마약류취급업체명 */
	private StringProperty bsshNm = new SimpleStringProperty();
	/** 업종코드 */
	private StringProperty indutyCd = new SimpleStringProperty();
	/** 업종명 */
	private StringProperty indutyNm = new SimpleStringProperty();
	/** 의료업자구분 */
	private StringProperty hdntCd = new SimpleStringProperty();
	/** 의료업자구분명 */
	private StringProperty hdntNm = new SimpleStringProperty();
	/** 사업자등록번호 */
	private StringProperty bizrno = new SimpleStringProperty();
	/** 대표자명 */
	private StringProperty rprsntvNm = new SimpleStringProperty();
	/** 담당자명 */
	private StringProperty chrgNm = new SimpleStringProperty();
	/** 요양기관기호 */
	private StringProperty hptlNo = new SimpleStringProperty();
	/** 전화번호 */
	private StringProperty telNo = new SimpleStringProperty();
	
	
	public final StringProperty bsshCdProperty() {
		return this.bsshCd;
	}
	
	public final String getBsshCd() {
		return this.bsshCdProperty().get();
	}
	
	public final void setBsshCd(final String bsshCd) {
		this.bsshCdProperty().set(bsshCd);
	}
	
	public final StringProperty bsshNmProperty() {
		return this.bsshNm;
	}
	
	public final String getBsshNm() {
		return this.bsshNmProperty().get();
	}
	
	public final void setBsshNm(final String bsshNm) {
		this.bsshNmProperty().set(bsshNm);
	}
	
	public final StringProperty indutyCdProperty() {
		return this.indutyCd;
	}
	
	public final String getIndutyCd() {
		return this.indutyCdProperty().get();
	}
	
	public final void setIndutyCd(final String indutyCd) {
		this.indutyCdProperty().set(indutyCd);
	}
	
	public final StringProperty indutyNmProperty() {
		return this.indutyNm;
	}
	
	public final String getIndutyNm() {
		return this.indutyNmProperty().get();
	}
	
	public final void setIndutyNm(final String indutyNm) {
		this.indutyNmProperty().set(indutyNm);
	}
	
	public final StringProperty hdntCdProperty() {
		return this.hdntCd;
	}
	
	public final String getHdntCd() {
		return this.hdntCdProperty().get();
	}
	
	public final void setHdntCd(final String hdntCd) {
		this.hdntCdProperty().set(hdntCd);
	}
	
	public final StringProperty hdntNmProperty() {
		return this.hdntNm;
	}
	
	public final String getHdntNm() {
		return this.hdntNmProperty().get();
	}
	
	public final void setHdntNm(final String hdntNm) {
		this.hdntNmProperty().set(hdntNm);
	}
	
	public final StringProperty bizrnoProperty() {
		return this.bizrno;
	}
	
	public final String getBizrno() {
		return this.bizrnoProperty().get();
	}
	
	public final void setBizrno(final String bizrno) {
		this.bizrnoProperty().set(bizrno);
	}
	
	public final StringProperty rprsntvNmProperty() {
		return this.rprsntvNm;
	}
	
	public final String getRprsntvNm() {
		return this.rprsntvNmProperty().get();
	}
	
	public final void setRprsntvNm(final String rprsntvNm) {
		this.rprsntvNmProperty().set(rprsntvNm);
	}
	
	public final StringProperty chrgNmProperty() {
		return this.chrgNm;
	}
	
	public final String getChrgNm() {
		return this.chrgNmProperty().get();
	}
	
	public final void setChrgNm(final String chrgNm) {
		this.chrgNmProperty().set(chrgNm);
	}
	
	public final StringProperty hptlNoProperty() {
		return this.hptlNo;
	}
	
	public final String getHptlNo() {
		return this.hptlNoProperty().get();
	}
	
	public final void setHptlNo(final String hptlNo) {
		this.hptlNoProperty().set(hptlNo);
	}

	public final StringProperty telNoProperty() {
		return this.telNo;
	}
	

	public final String getTelNo() {
		return this.telNoProperty().get();
	}
	
	public final void setTelNo(final String telNo) {
		this.telNoProperty().set(telNo);
	}
	
}
