//
// 이 파일은 JAXB(JavaTM Architecture for XML Binding) 참조 구현 2.2.8-b130911.1802 버전을 통해 생성되었습니다. 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
// 생성 날짜: 2018.03.21 시간 02:38:41 PM KST 
//


package kr.pe.withwind.nims.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.pe.withwind.nims.utils.CDATAAdapter;


/**
 * <p>line complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="line">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="USR_RPT_ID_NO" type="{https://www.nims.or.kr/schema/nims}USR_RPT_ID_NO"/>
 *         &lt;element name="USR_RPT_LN_ID_NO" type="{https://www.nims.or.kr/schema/nims}USR_RPT_LN_ID_NO"/>
 *         &lt;element name="STORGE_NO" type="{https://www.nims.or.kr/schema/nims}STORGE_NO"/>
 *         &lt;element name="MVMN_TY_CD" type="{https://www.nims.or.kr/schema/nims}MVMN_TY_CD"/>
 *         &lt;element name="PRDUCT_CD" type="{https://www.nims.or.kr/schema/nims}PRDUCT_CD"/>
 *         &lt;element name="MNF_NO" type="{https://www.nims.or.kr/schema/nims}MNF_NO"/>
 *         &lt;element name="MNF_SEQ" type="{https://www.nims.or.kr/schema/nims}MNF_SEQ"/>
 *         &lt;element name="MIN_DISTB_QY" type="{https://www.nims.or.kr/schema/nims}MIN_DISTB_QY"/>
 *         &lt;element name="PRD_MIN_DISTB_UNIT" type="{https://www.nims.or.kr/schema/nims}PRD_MIN_DISTB_UNIT"/>
 *         &lt;element name="PCE_QY" type="{https://www.nims.or.kr/schema/nims}PCE_QY"/>
 *         &lt;element name="PRD_PCE_UNIT" type="{https://www.nims.or.kr/schema/nims}PRD_PCE_UNIT"/>
 *         &lt;element name="PRDUCT_NM" type="{https://www.nims.or.kr/schema/nims}PRDUCT_NM"/>
 *         &lt;element name="PRD_SGTIN" type="{https://www.nims.or.kr/schema/nims}PRD_SGTIN"/>
 *         &lt;element name="PRD_MIN_DISTB_QY" type="{https://www.nims.or.kr/schema/nims}PRD_MIN_DISTB_QY"/>
 *         &lt;element name="PRD_TOT_PCE_QY" type="{https://www.nims.or.kr/schema/nims}PRD_TOT_PCE_QY"/>
 *         &lt;element name="PRD_VALID_DE" type="{https://www.nims.or.kr/schema/nims}PRD_VALID_DE"/>
 *         &lt;element name="MDC_SUM_QY" type="{https://www.nims.or.kr/schema/nims}MDC_SUM_QY"/>
 *         &lt;element name="MDC_ONCE_QY" type="{https://www.nims.or.kr/schema/nims}MDC_ONCE_QY"/>
 *         &lt;element name="MDC_ADE_CNT" type="{https://www.nims.or.kr/schema/nims}MDC_ADE_CNT"/>
 *         &lt;element name="MDC_TOT_DCNT" type="{https://www.nims.or.kr/schema/nims}MDC_TOT_DCNT"/>
 *         &lt;element name="MDC_AFT_DSUSE_QY" type="{https://www.nims.or.kr/schema/nims}MDC_AFT_DSUSE_QY"/>
 *         &lt;element name="MDC_AFT_DSUSE_UNIT" type="{https://www.nims.or.kr/schema/nims}MDC_AFT_DSUSE_UNIT"/>
 *         &lt;element name="FILE_CREAT_DT" type="{https://www.nims.or.kr/schema/nims}FILE_CREAT_DT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "line", propOrder = {
    "usrrptidno",
    "usrrptlnidno",
    "storgeno",
    "mvmntycd",
    "prductcd",
    "mnfno",
    "mnfseq",
    "mindistbqy",
    "prdmindistbunit",
    "pceqy",
    "prdpceunit",
    "prductnm",
    "prdsgtin",
    "prdmindistbqy",
    "prdtotpceqy",
    "prdvalidde",
    "mdcsumqy",
    "mdconceqy",
    "mdcadecnt",
    "mdctotdcnt",
    "mdcaftdsuseqy",
    "mdcaftdsuseunit",
    "filecreatdt"
})
public class Line {

	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "USR_RPT_ID_NO", required = true)
    protected String usrrptidno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "USR_RPT_LN_ID_NO", required = true)
    protected String usrrptlnidno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "STORGE_NO", required = true)
    protected String storgeno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MVMN_TY_CD", required = true)
    protected String mvmntycd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRDUCT_CD", required = true)
    protected String prductcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_NO", required = true)
    protected String mnfno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_SEQ", required = true)
    protected String mnfseq;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MIN_DISTB_QY", required = true)
    protected String mindistbqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRD_MIN_DISTB_UNIT", required = true)
    protected String prdmindistbunit;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PCE_QY", required = true)
    protected String pceqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRD_PCE_UNIT", required = true)
    protected String prdpceunit;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRDUCT_NM", required = true)
    protected String prductnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRD_SGTIN", required = true)
    protected String prdsgtin;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRD_MIN_DISTB_QY", required = true)
    protected String prdmindistbqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRD_TOT_PCE_QY", required = true)
    protected String prdtotpceqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "PRD_VALID_DE", required = true)
    protected String prdvalidde;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_SUM_QY", required = true)
    protected String mdcsumqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_ONCE_QY", required = true)
    protected String mdconceqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_ADE_CNT", required = true)
    protected String mdcadecnt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_TOT_DCNT", required = true)
    protected String mdctotdcnt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_AFT_DSUSE_QY", required = true)
    protected String mdcaftdsuseqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_AFT_DSUSE_UNIT", required = true)
    protected String mdcaftdsuseunit;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "FILE_CREAT_DT", required = true)
    protected String filecreatdt;

    /**
     * usrrptidno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSRRPTIDNO() {
        return usrrptidno;
    }

    /**
     * usrrptidno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSRRPTIDNO(String value) {
        this.usrrptidno = value;
    }

    /**
     * usrrptlnidno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSRRPTLNIDNO() {
        return usrrptlnidno;
    }

    /**
     * usrrptlnidno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSRRPTLNIDNO(String value) {
        this.usrrptlnidno = value;
    }

    /**
     * storgeno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTORGENO() {
        return storgeno;
    }

    /**
     * storgeno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTORGENO(String value) {
        this.storgeno = value;
    }

    /**
     * mvmntycd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMVMNTYCD() {
        return mvmntycd;
    }

    /**
     * mvmntycd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMVMNTYCD(String value) {
        this.mvmntycd = value;
    }

    /**
     * prductcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDUCTCD() {
        return prductcd;
    }

    /**
     * prductcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDUCTCD(String value) {
        this.prductcd = value;
    }

    /**
     * mnfno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFNO() {
        return mnfno;
    }

    /**
     * mnfno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFNO(String value) {
        this.mnfno = value;
    }

    /**
     * mnfseq 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFSEQ() {
        return mnfseq;
    }

    /**
     * mnfseq 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFSEQ(String value) {
        this.mnfseq = value;
    }

    /**
     * mindistbqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMINDISTBQY() {
        return mindistbqy;
    }

    /**
     * mindistbqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMINDISTBQY(String value) {
        this.mindistbqy = value;
    }

    /**
     * prdmindistbunit 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDMINDISTBUNIT() {
        return prdmindistbunit;
    }

    /**
     * prdmindistbunit 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDMINDISTBUNIT(String value) {
        this.prdmindistbunit = value;
    }

    /**
     * pceqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPCEQY() {
        return pceqy;
    }

    /**
     * pceqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPCEQY(String value) {
        this.pceqy = value;
    }

    /**
     * prdpceunit 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDPCEUNIT() {
        return prdpceunit;
    }

    /**
     * prdpceunit 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDPCEUNIT(String value) {
        this.prdpceunit = value;
    }

    /**
     * prductnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDUCTNM() {
        return prductnm;
    }

    /**
     * prductnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDUCTNM(String value) {
        this.prductnm = value;
    }

    /**
     * prdsgtin 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDSGTIN() {
        return prdsgtin;
    }

    /**
     * prdsgtin 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDSGTIN(String value) {
        this.prdsgtin = value;
    }

    /**
     * prdmindistbqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDMINDISTBQY() {
        return prdmindistbqy;
    }

    /**
     * prdmindistbqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDMINDISTBQY(String value) {
        this.prdmindistbqy = value;
    }

    /**
     * prdtotpceqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDTOTPCEQY() {
        return prdtotpceqy;
    }

    /**
     * prdtotpceqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDTOTPCEQY(String value) {
        this.prdtotpceqy = value;
    }

    /**
     * prdvalidde 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRDVALIDDE() {
        return prdvalidde;
    }

    /**
     * prdvalidde 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRDVALIDDE(String value) {
        this.prdvalidde = value;
    }

    /**
     * mdcsumqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCSUMQY() {
        return mdcsumqy;
    }

    /**
     * mdcsumqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCSUMQY(String value) {
        this.mdcsumqy = value;
    }

    /**
     * mdconceqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCONCEQY() {
        return mdconceqy;
    }

    /**
     * mdconceqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCONCEQY(String value) {
        this.mdconceqy = value;
    }

    /**
     * mdcadecnt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCADECNT() {
        return mdcadecnt;
    }

    /**
     * mdcadecnt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCADECNT(String value) {
        this.mdcadecnt = value;
    }

    /**
     * mdctotdcnt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCTOTDCNT() {
        return mdctotdcnt;
    }

    /**
     * mdctotdcnt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCTOTDCNT(String value) {
        this.mdctotdcnt = value;
    }

    /**
     * mdcaftdsuseqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCAFTDSUSEQY() {
        return mdcaftdsuseqy;
    }

    /**
     * mdcaftdsuseqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCAFTDSUSEQY(String value) {
        this.mdcaftdsuseqy = value;
    }

    /**
     * mdcaftdsuseunit 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCAFTDSUSEUNIT() {
        return mdcaftdsuseunit;
    }

    /**
     * mdcaftdsuseunit 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCAFTDSUSEUNIT(String value) {
        this.mdcaftdsuseunit = value;
    }

    /**
     * filecreatdt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFILECREATDT() {
        return filecreatdt;
    }

    /**
     * filecreatdt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFILECREATDT(String value) {
        this.filecreatdt = value;
    }

	@Override
	public String toString() {
		return "Line [usrrptidno=" + usrrptidno + ", usrrptlnidno=" + usrrptlnidno + ", storgeno=" + storgeno
				+ ", mvmntycd=" + mvmntycd + ", prductcd=" + prductcd + ", mnfno=" + mnfno + ", mnfseq=" + mnfseq
				+ ", mindistbqy=" + mindistbqy + ", prdmindistbunit=" + prdmindistbunit + ", pceqy=" + pceqy
				+ ", prdpceunit=" + prdpceunit + ", prductnm=" + prductnm + ", prdsgtin=" + prdsgtin
				+ ", prdmindistbqy=" + prdmindistbqy + ", prdtotpceqy=" + prdtotpceqy + ", prdvalidde=" + prdvalidde
				+ ", mdcsumqy=" + mdcsumqy + ", mdconceqy=" + mdconceqy + ", mdcadecnt=" + mdcadecnt + ", mdctotdcnt="
				+ mdctotdcnt + ", mdcaftdsuseqy=" + mdcaftdsuseqy + ", mdcaftdsuseunit=" + mdcaftdsuseunit
				+ ", filecreatdt=" + filecreatdt + "]";
	}
}
