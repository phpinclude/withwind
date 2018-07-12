package kr.pe.withwind.nims.xls;

public class XlsVoMap {
	
	private String voField;
	private String xlsLabel;
	private String voType;
	private int xlsOrd;

	public XlsVoMap(String voType,String voField,String xlsLabel, int xlsOrd) {
		super();
		this.voField = voField;
		this.xlsLabel = xlsLabel;
		this.voType = voType;
		this.xlsOrd = xlsOrd;
	}
	/** vo set 함수명 */
	public String getVoField() {
		return voField;
	}
	/** vo set 함수명 */
	public void setVoField(String voField) {
		this.voField = voField;
	}
	/** 엑셀표현 라벨 */
	public String getXlsLabel() {
		return xlsLabel;
	}
	/** 엑셀표현 라벨 */
	public void setXlsLabel(String xlsLabel) {
		this.xlsLabel = xlsLabel;
	}
	/** 헤더 인지 라인인지 (H or L) */
	public String getVoType() {
		return voType;
	}
	/** 헤더 인지 라인인지 (H or L) */
	public void setVoType(String voType) {
		this.voType = voType;
	}
	/** 엑셀 셀 순서 */
	public int getXlsOrd() {
		return xlsOrd;
	}
	/** 엑셀 셀 순서 */
	public void setXlsOrd(int xlsOrd) {
		this.xlsOrd = xlsOrd;
	}
	
	
}