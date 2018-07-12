package kr.pe.withwind.nims.domain.openapi;

public class Common {
	
	/** 파라메터 인증키 필수 */
	private String paramK;
	/** 파라메터 조회범위(1,2) 필수 */
	private String paramFg;
	/** 파라메터 조회페이지 필수 */
	private String paramPg;
	/** 파라메터 기준일자이후(yyyymmdd) 선택 */
	private String paramYmd;
	/** 파라메터 중점일반구분(1,2) 선택 */
	private String paramFg2;
	/** 파라메터 제품코드 선택 */
	private String paramP;
	/** 파라메터 제품명 선택 */
	private String paramPn;
	/** 파라메터 상대업체 선택 */
	private String paramC;
	/** 파라메터 기관명 선택 */
	private String paramOnm;
	/** 파라메터 기관코드 선택 */
	private String paramOcd;
	/** 파라메터 주소 선택 */
	private String paramAdr;
	/** 파라메터 상대업체명 선택 */
	private String paramBn;
	/** 파라메터 상대업체 취급자식별코드 선택 */
	private String parambc;
	/** 파라메터 바코드 선택 */
	private String paramSgtin;
	/** 파라메터 사업자등록번호 선택 */
	private String paramBi;
	/** 파라메터 요양기관번호 선택 */
	private String paramHp;
	
	/** 결과코드 0,-1,8,9 */
	private String resultCode;
	/** 결과메시지 */
	private String resultMsg;
	/** 전체결과수 */
	private String totalCount;
	/** 마지막 데이터 여부 */
	private String isEndYn;
	
	
	/** 파라메터 바코드 선택 */
	public String getParamSgtin() {
		return paramSgtin;
	}
	/** 파라메터 바코드 선택 */
	public void setParamSgtin(String paramSgtin) {
		this.paramSgtin = paramSgtin;
	}
	/** 파라메터 사업자등록번호 선택 */
	public String getParamBi() {
		return paramBi;
	}
	/** 파라메터 사업자등록번호 선택 */
	public void setParamBi(String paramBi) {
		this.paramBi = paramBi;
	}
	/** 파라메터 요양기관번호 선택 */
	public String getParamHp() {
		return paramHp;
	}
	/** 파라메터 요양기관번호 선택 */
	public void setParamHp(String paramHp) {
		this.paramHp = paramHp;
	}
	/** 파라메터 상대업체명 선택 */
	public String getParamBn() {
		return paramBn;
	}
	/** 파라메터 상대업체명 선택 */
	public void setParamBn(String paramBn) {
		this.paramBn = paramBn;
	}
	/** 파라메터 상대업체 취급자식별코드 */
	public String getParambc() {
		return parambc;
	}
	/** 파라메터 상대업체 취급자식별코드 */
	public void setParambc(String parambc) {
		this.parambc = parambc;
	}
	/** 파라메터 상대업체 선택 */
	public String getParamC() {
		return paramC;
	}
	/** 파라메터  상대업체 선택 */
	public void setParamC(String paramC) {
		this.paramC = paramC;
	}
	
	/** 파라메터 기관명 선택 */
	public String getParamOnm() {
		return paramOnm;
	}
	/** 파라메터 기관명 선택 */
	public void setParamOnm(String paramOnm) {
		this.paramOnm = paramOnm;
	}
	/** 파라메터 기관코드 선택 */
	public String getParamOcd() {
		return paramOcd;
	}
	/** 파라메터 기관코드 선택 */
	public void setParamOcd(String paramOcd) {
		this.paramOcd = paramOcd;
	}
	/** 파라메터 주소 선택 */
	public String getParamAdr() {
		return paramAdr;
	}
	/** 파라메터 주소 선택 */
	public void setParamAdr(String paramAdr) {
		this.paramAdr = paramAdr;
	}
	public String getParamK() {
		return paramK;
	}
	/** 파라메터 인증키 필수 */
	public void setParamK(String paramK) {
		this.paramK = paramK;
	}
	public String getParamFg() {
		return paramFg;
	}
	/** 파라메터 조회범위(1,2) 필수 */
	public void setParamFg(String paramFg) {
		this.paramFg = paramFg;
	}
	public String getParamPg() {
		return paramPg;
	}
	/** 파라메터 조회페이지 필수 */
	public void setParamPg(String paramPg) {
		this.paramPg = paramPg;
	}
	public String getParamYmd() {
		return paramYmd;
	}
	/** 파라메터 기준일자이후(yyyymmdd) 선택 */
	public void setParamYmd(String paramYmd) {
		this.paramYmd = paramYmd;
	}
	public String getParamFg2() {
		return paramFg2;
	}
	/** 파라메터 중점일반구분(1,2) 선택 */
	public void setParamFg2(String paramFg2) {
		this.paramFg2 = paramFg2;
	}
	public String getParamP() {
		return paramP;
	}
	/** 파라메터 제품코드 선택 */
	public void setParamP(String paramP) {
		this.paramP = paramP;
	}
	public String getParamPn() {
		return paramPn;
	}
	/** 파라메터 제품명 선택 */
	public void setParamPn(String paramPn) {
		this.paramPn = paramPn;
	}
	/** 결과코드 0,-1,8,9 */
	public String getResultCode() {
		return resultCode;
	}
	/** 결과코드 0,-1,8,9 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/** 결과메시지 */
	public String getResultMsg() {
		return resultMsg;
	}
	/** 결과메시지 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	/** 전체결과수 */
	public String getTotalCount() {
		return totalCount;
	}
	/** 전체결과수 */
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	/** 마지막 데이터 여부 */
	public String getIsEndYn() {
		return isEndYn;
	}
	/** 마지막 데이터 여부 */
	public void setIsEndYn(String isEndYn) {
		this.isEndYn = isEndYn;
	}
}
