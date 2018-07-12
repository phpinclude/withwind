package kr.pe.withwind.nims.domain.openapi;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaceList {

	private StringProperty bsshCd = new SimpleStringProperty();
	private StringProperty bsshNm = new SimpleStringProperty();
	private StringProperty storgeNo = new SimpleStringProperty();
	private StringProperty storgeNm = new SimpleStringProperty();
	private StringProperty bassAdres = new SimpleStringProperty();
	private StringProperty bassDtlAdres = new SimpleStringProperty();
	
	public StringProperty bsshCdProperty() {
		return this.bsshCd;
	}
	
	public String getBsshCd() {
		return this.bsshCdProperty().get();
	}
	
	public void setBsshCd(final String bsshCd) {
		this.bsshCdProperty().set(bsshCd);
	}
	
	public StringProperty bsshNmProperty() {
		return this.bsshNm;
	}
	
	public String getBsshNm() {
		return this.bsshNmProperty().get();
	}
	
	public void setBsshNm(final String bsshNm) {
		this.bsshNmProperty().set(bsshNm);
	}
	
	public StringProperty storgeNoProperty() {
		return this.storgeNo;
	}
	
	public String getStorgeNo() {
		return this.storgeNoProperty().get();
	}
	
	public void setStorgeNo(final String storgeNo) {
		this.storgeNoProperty().set(storgeNo);
	}
	
	public StringProperty storgeNmProperty() {
		return this.storgeNm;
	}
	
	public String getStorgeNm() {
		return this.storgeNmProperty().get();
	}
	
	public void setStorgeNm(final String storgeNm) {
		this.storgeNmProperty().set(storgeNm);
	}
	
	public StringProperty bassAdresProperty() {
		return this.bassAdres;
	}
	
	public String getBassAdres() {
		return this.bassAdresProperty().get();
	}
	
	public void setBassAdres(final String bassAdres) {
		this.bassAdresProperty().set(bassAdres);
	}
	
	public StringProperty bassDtlAdresProperty() {
		return this.bassDtlAdres;
	}
	
	public String getBassDtlAdres() {
		return this.bassDtlAdresProperty().get();
	}
	
	public void setBassDtlAdres(final String bassDtlAdres) {
		this.bassDtlAdresProperty().set(bassDtlAdres);
	}

	@Override
	public String toString() {
		return getStorgeNm() + "("+getStorgeNo()+")";
	}
}