package kr.pe.withwind.nims.domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class XlsMapping {
	private StringProperty rptType = new SimpleStringProperty();
	private StringProperty fieldNm = new SimpleStringProperty();
	private StringProperty hlType = new SimpleStringProperty();
	private StringProperty xlsLabel = new SimpleStringProperty();
	private IntegerProperty colIdx = new SimpleIntegerProperty();
	private StringProperty viewYn = new SimpleStringProperty();
	private StringProperty refType = new SimpleStringProperty();
	private StringProperty refDefault = new SimpleStringProperty();
	public StringProperty rptTypeProperty() {
		return this.rptType;
	}
	
	public String getRptType() {
		return this.rptTypeProperty().get();
	}
	
	public void setRptType(final String rptType) {
		this.rptTypeProperty().set(rptType);
	}
	
	public StringProperty fieldNmProperty() {
		return this.fieldNm;
	}
	
	public String getFieldNm() {
		return this.fieldNmProperty().get();
	}
	
	public void setFieldNm(final String fieldNm) {
		this.fieldNmProperty().set(fieldNm);
	}
	
	public StringProperty hlTypeProperty() {
		return this.hlType;
	}
	
	public String getHlType() {
		return this.hlTypeProperty().get();
	}
	
	public void setHlType(final String hlType) {
		this.hlTypeProperty().set(hlType);
	}
	
	public StringProperty xlsLabelProperty() {
		return this.xlsLabel;
	}
	
	public String getXlsLabel() {
		return this.xlsLabelProperty().get();
	}
	
	public void setXlsLabel(final String xlsLabel) {
		this.xlsLabelProperty().set(xlsLabel);
	}
	
	public IntegerProperty colIdxProperty() {
		return this.colIdx;
	}
	
	public int getColIdx() {
		return this.colIdxProperty().get();
	}
	
	public void setColIdx(final int colIdx) {
		this.colIdxProperty().set(colIdx);
	}
	
	public StringProperty viewYnProperty() {
		return this.viewYn;
	}
	
	public String getViewYn() {
		return this.viewYnProperty().get();
	}
	
	public void setViewYn(final String viewYn) {
		this.viewYnProperty().set(viewYn);
	}
	
	public StringProperty refTypeProperty() {
		return this.refType;
	}
	
	public String getRefType() {
		return this.refTypeProperty().get();
	}
	
	public void setRefType(final String refType) {
		this.refTypeProperty().set(refType);
	}
	
	public StringProperty refDefaultProperty() {
		return this.refDefault;
	}
	
	public String getRefDefault() {
		return this.refDefaultProperty().get();
	}
	
	public void setRefDefault(final String refDefault) {
		this.refDefaultProperty().set(refDefault);
	}

	@Override
	public String toString() {
		return "XlsMapping [rptType=" + rptType + ", fieldNm=" + fieldNm + ", hlType=" + hlType + ", xlsLabel="
				+ xlsLabel + ", colIdx=" + colIdx + ", viewYn=" + viewYn + ", refType=" + refType + ", refDefault="
				+ refDefault + "]";
	}
	
	
	
	
}
