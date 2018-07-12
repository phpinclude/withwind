package kr.pe.withwind.nims.domain;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CommonCode implements Serializable {

	private static final long serialVersionUID = 1914767475448012918L;

	private StringProperty pCode = new SimpleStringProperty();
	private StringProperty code = new SimpleStringProperty();
	private StringProperty codeType = new SimpleStringProperty();
	private StringProperty codeNm = new SimpleStringProperty();
	private IntegerProperty ordNo = new SimpleIntegerProperty();
	private StringProperty useYn = new SimpleStringProperty();
	private StringProperty viewYn = new SimpleStringProperty();
	private StringProperty codeDesc = new SimpleStringProperty();
	
	
	public final StringProperty pCodeProperty() {
		return this.pCode;
	}
	
	public final String getPCode() {
		return this.pCodeProperty().get();
	}
	
	public final void setPCode(final String pCode) {
		this.pCodeProperty().set(pCode);
	}
	
	public final String getpCode() {
		return this.pCodeProperty().get();
	}
	
	public final void setpCode(final String pCode) {
		this.pCodeProperty().set(pCode);
	}
	
	public final StringProperty codeProperty() {
		return this.code;
	}
	
	public final String getCode() {
		return this.codeProperty().get();
	}
	
	public final void setCode(final String code) {
		this.codeProperty().set(code);
	}
	
	public final StringProperty codeTypeProperty() {
		return this.codeType;
	}
	
	public final String getCodeType() {
		return this.codeTypeProperty().get();
	}
	
	public final void setCodeType(final String codeType) {
		this.codeTypeProperty().set(codeType);
	}
	
	public final StringProperty codeNmProperty() {
		return this.codeNm;
	}
	
	public final String getCodeNm() {
		return this.codeNmProperty().get();
	}
	
	public final void setCodeNm(final String codeNm) {
		this.codeNmProperty().set(codeNm);
	}
	
	public final IntegerProperty ordNoProperty() {
		return this.ordNo;
	}
	
	public final int getOrdNo() {
		return this.ordNoProperty().get();
	}
	
	public final void setOrdNo(final int ordNo) {
		this.ordNoProperty().set(ordNo);
	}
	
	public final StringProperty useYnProperty() {
		return this.useYn;
	}
	
	public final String getUseYn() {
		return this.useYnProperty().get();
	}
	
	public final void setUseYn(final String useYn) {
		this.useYnProperty().set(useYn);
	}

	public final StringProperty viewYnProperty() {
		return this.viewYn;
	}
	

	public final String getViewYn() {
		return this.viewYnProperty().get();
	}
	

	public final void setViewYn(final String viewYn) {
		this.viewYnProperty().set(viewYn);
	}
	

	public final StringProperty codeDescProperty() {
		return this.codeDesc;
	}
	

	public final String getCodeDesc() {
		return this.codeDescProperty().get();
	}
	

	public final void setCodeDesc(final String codeDesc) {
		this.codeDescProperty().set(codeDesc);
	}
	
	@Override
	public String toString() {
		return getCodeNm() + " " + getCode();
	}
	
}
