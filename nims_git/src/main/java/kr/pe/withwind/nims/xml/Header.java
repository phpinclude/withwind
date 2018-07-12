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
 * <p>header complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType name="header">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HDR_DE" type="{https://www.nims.or.kr/schema/nims}HDR_DE"/>
 *         &lt;element name="BSSH_CD" type="{https://www.nims.or.kr/schema/nims}BSSH_CD"/>
 *         &lt;element name="RPT_SE_CD" type="{https://www.nims.or.kr/schema/nims}RPT_SE_CD"/>
 *         &lt;element name="USR_RPT_ID_NO" type="{https://www.nims.or.kr/schema/nims}USR_RPT_ID_NO"/>
 *         &lt;element name="REF_USR_RPT_ID_NO" type="{https://www.nims.or.kr/schema/nims}REF_USR_RPT_ID_NO"/>
 *         &lt;element name="RPT_TY_CD" type="{https://www.nims.or.kr/schema/nims}RPT_TY_CD"/>
 *         &lt;element name="RPT_SE_DTL_CD" type="{https://www.nims.or.kr/schema/nims}RPT_SE_DTL_CD"/>
 *         &lt;element name="RMK" type="{https://www.nims.or.kr/schema/nims}RMK"/>
 *         &lt;element name="RPTR_NM" type="{https://www.nims.or.kr/schema/nims}RPTR_NM"/>
 *         &lt;element name="RPTR_ENTRPS_NM" type="{https://www.nims.or.kr/schema/nims}RPTR_ENTRPS_NM"/>
 *         &lt;element name="CHRG_NM" type="{https://www.nims.or.kr/schema/nims}CHRG_NM"/>
 *         &lt;element name="CHRG_TEL_NO" type="{https://www.nims.or.kr/schema/nims}CHRG_TEL_NO"/>
 *         &lt;element name="CHRG_MP_NO" type="{https://www.nims.or.kr/schema/nims}CHRG_MP_NO"/>
 *         &lt;element name="RND_DTL_RPT_CNT" type="{https://www.nims.or.kr/schema/nims}RND_DTL_RPT_CNT"/>
 *         &lt;element name="OPP_BSSH_NM" type="{https://www.nims.or.kr/schema/nims}OPP_BSSH_NM"/>
 *         &lt;element name="OPP_BSSH_CD" type="{https://www.nims.or.kr/schema/nims}OPP_BSSH_CD"/>
 *         &lt;element name="OPP_STORGE_NO" type="{https://www.nims.or.kr/schema/nims}OPP_STORGE_NO"/>
 *         &lt;element name="NON_NRCD_MTRAL_AT" type="{https://www.nims.or.kr/schema/nims}NON_NRCD_MTRAL_AT"/>
 *         &lt;element name="MNF_STRT_DE" type="{https://www.nims.or.kr/schema/nims}MNF_STRT_DE"/>
 *         &lt;element name="MNF_END_DE" type="{https://www.nims.or.kr/schema/nims}MNF_END_DE"/>
 *         &lt;element name="MNF_CNSGN_SE_CD" type="{https://www.nims.or.kr/schema/nims}MNF_CNSGN_SE_CD"/>
 *         &lt;element name="MNF_TOT_QY" type="{https://www.nims.or.kr/schema/nims}MNF_TOT_QY"/>
 *         &lt;element name="MNF_PRD_UC_AMT" type="{https://www.nims.or.kr/schema/nims}MNF_PRD_UC_AMT"/>
 *         &lt;element name="MNF_PRD_AMT" type="{https://www.nims.or.kr/schema/nims}MNF_PRD_AMT"/>
 *         &lt;element name="MNF_YIELD_RATE" type="{https://www.nims.or.kr/schema/nims}MNF_YIELD_RATE"/>
 *         &lt;element name="EXIM_COUNTRY_CD" type="{https://www.nims.or.kr/schema/nims}EXIM_COUNTRY_CD"/>
 *         &lt;element name="EXIM_OSEA_DELNG_NM" type="{https://www.nims.or.kr/schema/nims}EXIM_OSEA_DELNG_NM"/>
 *         &lt;element name="EXIM_CONFM_NO" type="{https://www.nims.or.kr/schema/nims}EXIM_CONFM_NO"/>
 *         &lt;element name="EXIM_NOTIFY_CERT_NO" type="{https://www.nims.or.kr/schema/nims}EXIM_NOTIFY_CERT_NO"/>
 *         &lt;element name="EXIM_OSEA_DELNG_SIGN" type="{https://www.nims.or.kr/schema/nims}EXIM_OSEA_DELNG_SIGN"/>
 *         &lt;element name="EXIM_ENTR_DE" type="{https://www.nims.or.kr/schema/nims}EXIM_ENTR_DE"/>
 *         &lt;element name="EXIM_IPP_SPOT_DE" type="{https://www.nims.or.kr/schema/nims}EXIM_IPP_SPOT_DE"/>
 *         &lt;element name="EXIM_IO_DE" type="{https://www.nims.or.kr/schema/nims}EXIM_IO_DE"/>
 *         &lt;element name="EXIM_UC_AMT" type="{https://www.nims.or.kr/schema/nims}EXIM_UC_AMT"/>
 *         &lt;element name="EXIM_AMT" type="{https://www.nims.or.kr/schema/nims}EXIM_AMT"/>
 *         &lt;element name="MDC_PAT_SE_CD" type="{https://www.nims.or.kr/schema/nims}MDC_PAT_SE_CD"/>
 *         &lt;element name="MDC_PAT_ID_NO_TY_CD" type="{https://www.nims.or.kr/schema/nims}MDC_PAT_ID_NO_TY_CD"/>
 *         &lt;element name="MDC_PAT_ID_NO" type="{https://www.nims.or.kr/schema/nims}MDC_PAT_ID_NO"/>
 *         &lt;element name="MDC_PAT_NM" type="{https://www.nims.or.kr/schema/nims}MDC_PAT_NM"/>
 *         &lt;element name="MDC_HPTL_NO" type="{https://www.nims.or.kr/schema/nims}MDC_HPTL_NO"/>
 *         &lt;element name="MDC_INSTT_NM" type="{https://www.nims.or.kr/schema/nims}MDC_INSTT_NM"/>
 *         &lt;element name="MDC_LCNS_ASORT_CD" type="{https://www.nims.or.kr/schema/nims}MDC_LCNS_ASORT_CD"/>
 *         &lt;element name="MDC_LCNS_NO" type="{https://www.nims.or.kr/schema/nims}MDC_LCNS_NO"/>
 *         &lt;element name="MDC_PRSC_DOC_NM" type="{https://www.nims.or.kr/schema/nims}MDC_PRSC_DOC_NM"/>
 *         &lt;element name="MDC_PRSC_ORD_NO" type="{https://www.nims.or.kr/schema/nims}MDC_PRSC_ORD_NO"/>
 *         &lt;element name="MDC_DISS_CODE" type="{https://www.nims.or.kr/schema/nims}MDC_DISS_CODE"/>
 *         &lt;element name="MDC_ANI_KIND_SE_CD" type="{https://www.nims.or.kr/schema/nims}MDC_ANI_KIND_SE_CD"/>
 *         &lt;element name="MDC_ANI_CNT" type="{https://www.nims.or.kr/schema/nims}MDC_ANI_CNT"/>
 *         &lt;element name="MDC_ANI_KIND_NM" type="{https://www.nims.or.kr/schema/nims}MDC_ANI_KIND_NM"/>
 *         &lt;element name="MDC_ANI_DISS_NM" type="{https://www.nims.or.kr/schema/nims}MDC_ANI_DISS_NM"/>
 *         &lt;element name="USE_SE_CD" type="{https://www.nims.or.kr/schema/nims}USE_SE_CD"/>
 *         &lt;element name="USE_DE" type="{https://www.nims.or.kr/schema/nims}USE_DE"/>
 *         &lt;element name="TRNSFR_SE_CD" type="{https://www.nims.or.kr/schema/nims}TRNSFR_SE_CD"/>
 *         &lt;element name="DSUSE_SE_CD" type="{https://www.nims.or.kr/schema/nims}DSUSE_SE_CD"/>
 *         &lt;element name="DSUSE_PRV_CD" type="{https://www.nims.or.kr/schema/nims}DSUSE_PRV_CD"/>
 *         &lt;element name="DSUSE_MTH_CD" type="{https://www.nims.or.kr/schema/nims}DSUSE_MTH_CD"/>
 *         &lt;element name="DSUSE_LOC" type="{https://www.nims.or.kr/schema/nims}DSUSE_LOC"/>
 *         &lt;element name="DSUSE_DE" type="{https://www.nims.or.kr/schema/nims}DSUSE_DE"/>
 *         &lt;element name="DSUSE_INSTT_CD" type="{https://www.nims.or.kr/schema/nims}DSUSE_INSTT_CD"/>
 *         &lt;element name="ATCH_FILE_CO" type="{https://www.nims.or.kr/schema/nims}ATCH_FILE_CO"/>
 *         &lt;element name="REGISTER_ID" type="{https://www.nims.or.kr/schema/nims}REGISTER_ID"/>
 *         &lt;element name="FILE_CREAT_DT" type="{https://www.nims.or.kr/schema/nims}FILE_CREAT_DT"/>
 *         &lt;element name="LINES" type="{https://www.nims.or.kr/schema/nims}lines" minOccurs="0"/>
 *         &lt;element name="ATCH_FILES" type="{https://www.nims.or.kr/schema/nims}atchFiles" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "header", propOrder = {
    "hdrde",
    "bsshcd",
    "rptsecd",
    "usrrptidno",
    "refusrrptidno",
    "rpttycd",
    "rptsedtlcd",
    "rmk",
    "rptrnm",
    "rptrentrpsnm",
    "chrgnm",
    "chrgtelno",
    "chrgmpno",
    "rnddtlrptcnt",
    "oppbsshnm",
    "oppbsshcd",
    "oppstorgeno",
    "nonnrcdmtralat",
    "mnfstrtde",
    "mnfendde",
    "mnfcnsgnsecd",
    "mnftotqy",
    "mnfprducamt",
    "mnfprdamt",
    "mnfyieldrate",
    "eximcountrycd",
    "eximoseadelngnm",
    "eximconfmno",
    "eximnotifycertno",
    "eximoseadelngsign",
    "eximentrde",
    "eximippspotde",
    "eximiode",
    "eximucamt",
    "eximamt",
    "mdcpatsecd",
    "mdcpatidnotycd",
    "mdcpatidno",
    "mdcpatnm",
    "mdchptlno",
    "mdcinsttnm",
    "mdclcnsasortcd",
    "mdclcnsno",
    "mdcprscdocnm",
    "mdcprscordno",
    "mdcdisscode",
    "mdcanikindsecd",
    "mdcanicnt",
    "mdcanikindnm",
    "mdcanidissnm",
    "usesecd",
    "usede",
    "trnsfrsecd",
    "dsusesecd",
    "dsuseprvcd",
    "dsusemthcd",
    "dsuseloc",
    "dsusede",
    "dsuseinsttcd",
    "atchfileco",
    "registerid",
    "filecreatdt",
    "lines",
    "atchfiles"
})
public class Header {

	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "HDR_DE", required = true)
    protected String hdrde;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "BSSH_CD", required = true)
    protected String bsshcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RPT_SE_CD", required = true)
    protected String rptsecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "USR_RPT_ID_NO", required = true)
    protected String usrrptidno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "REF_USR_RPT_ID_NO", required = true)
    protected String refusrrptidno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RPT_TY_CD", required = true)
    protected String rpttycd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RPT_SE_DTL_CD", required = true)
    protected String rptsedtlcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RMK", required = true)
    protected String rmk;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RPTR_NM", required = true)
    protected String rptrnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RPTR_ENTRPS_NM", required = true)
    protected String rptrentrpsnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "CHRG_NM", required = true)
    protected String chrgnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "CHRG_TEL_NO", required = true)
    protected String chrgtelno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "CHRG_MP_NO", required = true)
    protected String chrgmpno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "RND_DTL_RPT_CNT", required = true)
    protected String rnddtlrptcnt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "OPP_BSSH_NM", required = true)
    protected String oppbsshnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "OPP_BSSH_CD", required = true)
    protected String oppbsshcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "OPP_STORGE_NO", required = true)
    protected String oppstorgeno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "NON_NRCD_MTRAL_AT", required = true)
    protected String nonnrcdmtralat;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_STRT_DE", required = true)
    protected String mnfstrtde;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_END_DE", required = true)
    protected String mnfendde;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_CNSGN_SE_CD", required = true)
    protected String mnfcnsgnsecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_TOT_QY", required = true)
    protected String mnftotqy;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_PRD_UC_AMT", required = true)
    protected String mnfprducamt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_PRD_AMT", required = true)
    protected String mnfprdamt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MNF_YIELD_RATE", required = true)
    protected String mnfyieldrate;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_COUNTRY_CD", required = true)
    protected String eximcountrycd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_OSEA_DELNG_NM", required = true)
    protected String eximoseadelngnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_CONFM_NO", required = true)
    protected String eximconfmno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_NOTIFY_CERT_NO", required = true)
    protected String eximnotifycertno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_OSEA_DELNG_SIGN", required = true)
    protected String eximoseadelngsign;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_ENTR_DE", required = true)
    protected String eximentrde;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_IPP_SPOT_DE", required = true)
    protected String eximippspotde;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_IO_DE", required = true)
    protected String eximiode;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_UC_AMT", required = true)
    protected String eximucamt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "EXIM_AMT", required = true)
    protected String eximamt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_PAT_SE_CD", required = true)
    protected String mdcpatsecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_PAT_ID_NO_TY_CD", required = true)
    protected String mdcpatidnotycd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_PAT_ID_NO", required = true)
    protected String mdcpatidno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_PAT_NM", required = true)
    protected String mdcpatnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_HPTL_NO", required = true)
    protected String mdchptlno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_INSTT_NM", required = true)
    protected String mdcinsttnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_LCNS_ASORT_CD", required = true)
    protected String mdclcnsasortcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_LCNS_NO", required = true)
    protected String mdclcnsno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_PRSC_DOC_NM", required = true)
    protected String mdcprscdocnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_PRSC_ORD_NO", required = true)
    protected String mdcprscordno;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_DISS_CODE", required = true)
    protected String mdcdisscode;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_ANI_KIND_SE_CD", required = true)
    protected String mdcanikindsecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_ANI_CNT", required = true)
    protected String mdcanicnt;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_ANI_KIND_NM", required = true)
    protected String mdcanikindnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "MDC_ANI_DISS_NM", required = true)
    protected String mdcanidissnm;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "USE_SE_CD", required = true)
    protected String usesecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "USE_DE", required = true)
    protected String usede;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "TRNSFR_SE_CD", required = true)
    protected String trnsfrsecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "DSUSE_SE_CD", required = true)
    protected String dsusesecd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "DSUSE_PRV_CD", required = true)
    protected String dsuseprvcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "DSUSE_MTH_CD", required = true)
    protected String dsusemthcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "DSUSE_LOC", required = true)
    protected String dsuseloc;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "DSUSE_DE", required = true)
    protected String dsusede;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "DSUSE_INSTT_CD", required = true)
    protected String dsuseinsttcd;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "ATCH_FILE_CO", required = true)
    protected String atchfileco;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "REGISTER_ID", required = true)
    protected String registerid;
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
    @XmlElement(name = "FILE_CREAT_DT", required = true)
    protected String filecreatdt;
	@XmlElement(name = "LINES")
    protected Lines lines;
    @XmlElement(name = "ATCH_FILES")
    protected AtchFiles atchfiles;

    /**
     * hdrde 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHDRDE() {
        return hdrde;
    }

    /**
     * hdrde 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHDRDE(String value) {
        this.hdrde = value;
    }

    /**
     * bsshcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSSHCD() {
        return bsshcd;
    }

    /**
     * bsshcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSSHCD(String value) {
        this.bsshcd = value;
    }

    /**
     * rptsecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPTSECD() {
        return rptsecd;
    }

    /**
     * rptsecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPTSECD(String value) {
        this.rptsecd = value;
    }

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
     * refusrrptidno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREFUSRRPTIDNO() {
        return refusrrptidno;
    }

    /**
     * refusrrptidno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREFUSRRPTIDNO(String value) {
        this.refusrrptidno = value;
    }

    /**
     * rpttycd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPTTYCD() {
        return rpttycd;
    }

    /**
     * rpttycd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPTTYCD(String value) {
        this.rpttycd = value;
    }

    /**
     * rptsedtlcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPTSEDTLCD() {
        return rptsedtlcd;
    }

    /**
     * rptsedtlcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPTSEDTLCD(String value) {
        this.rptsedtlcd = value;
    }

    /**
     * rmk 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRMK() {
        return rmk;
    }

    /**
     * rmk 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRMK(String value) {
        this.rmk = value;
    }

    /**
     * rptrnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPTRNM() {
        return rptrnm;
    }

    /**
     * rptrnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPTRNM(String value) {
        this.rptrnm = value;
    }

    /**
     * rptrentrpsnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPTRENTRPSNM() {
        return rptrentrpsnm;
    }

    /**
     * rptrentrpsnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPTRENTRPSNM(String value) {
        this.rptrentrpsnm = value;
    }

    /**
     * chrgnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHRGNM() {
        return chrgnm;
    }

    /**
     * chrgnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHRGNM(String value) {
        this.chrgnm = value;
    }

    /**
     * chrgtelno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHRGTELNO() {
        return chrgtelno;
    }

    /**
     * chrgtelno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHRGTELNO(String value) {
        this.chrgtelno = value;
    }

    /**
     * chrgmpno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHRGMPNO() {
        return chrgmpno;
    }

    /**
     * chrgmpno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHRGMPNO(String value) {
        this.chrgmpno = value;
    }

    /**
     * rnddtlrptcnt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRNDDTLRPTCNT() {
        return rnddtlrptcnt;
    }

    /**
     * rnddtlrptcnt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRNDDTLRPTCNT(String value) {
        this.rnddtlrptcnt = value;
    }

    /**
     * oppbsshnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPPBSSHNM() {
        return oppbsshnm;
    }

    /**
     * oppbsshnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPPBSSHNM(String value) {
        this.oppbsshnm = value;
    }

    /**
     * oppbsshcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPPBSSHCD() {
        return oppbsshcd;
    }

    /**
     * oppbsshcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPPBSSHCD(String value) {
        this.oppbsshcd = value;
    }

    /**
     * oppstorgeno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOPPSTORGENO() {
        return oppstorgeno;
    }

    /**
     * oppstorgeno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOPPSTORGENO(String value) {
        this.oppstorgeno = value;
    }

    /**
     * nonnrcdmtralat 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNONNRCDMTRALAT() {
        return nonnrcdmtralat;
    }

    /**
     * nonnrcdmtralat 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNONNRCDMTRALAT(String value) {
        this.nonnrcdmtralat = value;
    }

    /**
     * mnfstrtde 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFSTRTDE() {
        return mnfstrtde;
    }

    /**
     * mnfstrtde 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFSTRTDE(String value) {
        this.mnfstrtde = value;
    }

    /**
     * mnfendde 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFENDDE() {
        return mnfendde;
    }

    /**
     * mnfendde 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFENDDE(String value) {
        this.mnfendde = value;
    }

    /**
     * mnfcnsgnsecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFCNSGNSECD() {
        return mnfcnsgnsecd;
    }

    /**
     * mnfcnsgnsecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFCNSGNSECD(String value) {
        this.mnfcnsgnsecd = value;
    }

    /**
     * mnftotqy 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFTOTQY() {
        return mnftotqy;
    }

    /**
     * mnftotqy 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFTOTQY(String value) {
        this.mnftotqy = value;
    }

    /**
     * mnfprducamt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFPRDUCAMT() {
        return mnfprducamt;
    }

    /**
     * mnfprducamt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFPRDUCAMT(String value) {
        this.mnfprducamt = value;
    }

    /**
     * mnfprdamt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFPRDAMT() {
        return mnfprdamt;
    }

    /**
     * mnfprdamt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFPRDAMT(String value) {
        this.mnfprdamt = value;
    }

    /**
     * mnfyieldrate 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMNFYIELDRATE() {
        return mnfyieldrate;
    }

    /**
     * mnfyieldrate 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMNFYIELDRATE(String value) {
        this.mnfyieldrate = value;
    }

    /**
     * eximcountrycd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMCOUNTRYCD() {
        return eximcountrycd;
    }

    /**
     * eximcountrycd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMCOUNTRYCD(String value) {
        this.eximcountrycd = value;
    }

    /**
     * eximoseadelngnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMOSEADELNGNM() {
        return eximoseadelngnm;
    }

    /**
     * eximoseadelngnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMOSEADELNGNM(String value) {
        this.eximoseadelngnm = value;
    }

    /**
     * eximconfmno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMCONFMNO() {
        return eximconfmno;
    }

    /**
     * eximconfmno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMCONFMNO(String value) {
        this.eximconfmno = value;
    }

    /**
     * eximnotifycertno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMNOTIFYCERTNO() {
        return eximnotifycertno;
    }

    /**
     * eximnotifycertno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMNOTIFYCERTNO(String value) {
        this.eximnotifycertno = value;
    }

    /**
     * eximoseadelngsign 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMOSEADELNGSIGN() {
        return eximoseadelngsign;
    }

    /**
     * eximoseadelngsign 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMOSEADELNGSIGN(String value) {
        this.eximoseadelngsign = value;
    }

    /**
     * eximentrde 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMENTRDE() {
        return eximentrde;
    }

    /**
     * eximentrde 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMENTRDE(String value) {
        this.eximentrde = value;
    }

    /**
     * eximippspotde 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMIPPSPOTDE() {
        return eximippspotde;
    }

    /**
     * eximippspotde 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMIPPSPOTDE(String value) {
        this.eximippspotde = value;
    }

    /**
     * eximiode 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMIODE() {
        return eximiode;
    }

    /**
     * eximiode 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMIODE(String value) {
        this.eximiode = value;
    }

    /**
     * eximucamt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMUCAMT() {
        return eximucamt;
    }

    /**
     * eximucamt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMUCAMT(String value) {
        this.eximucamt = value;
    }

    /**
     * eximamt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXIMAMT() {
        return eximamt;
    }

    /**
     * eximamt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXIMAMT(String value) {
        this.eximamt = value;
    }

    /**
     * mdcpatsecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCPATSECD() {
        return mdcpatsecd;
    }

    /**
     * mdcpatsecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCPATSECD(String value) {
        this.mdcpatsecd = value;
    }

    /**
     * mdcpatidnotycd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCPATIDNOTYCD() {
        return mdcpatidnotycd;
    }

    /**
     * mdcpatidnotycd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCPATIDNOTYCD(String value) {
        this.mdcpatidnotycd = value;
    }

    /**
     * mdcpatidno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCPATIDNO() {
        return mdcpatidno;
    }

    /**
     * mdcpatidno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCPATIDNO(String value) {
        this.mdcpatidno = value;
    }

    /**
     * mdcpatnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCPATNM() {
        return mdcpatnm;
    }

    /**
     * mdcpatnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCPATNM(String value) {
        this.mdcpatnm = value;
    }

    /**
     * mdchptlno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCHPTLNO() {
        return mdchptlno;
    }

    /**
     * mdchptlno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCHPTLNO(String value) {
        this.mdchptlno = value;
    }

    /**
     * mdcinsttnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCINSTTNM() {
        return mdcinsttnm;
    }

    /**
     * mdcinsttnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCINSTTNM(String value) {
        this.mdcinsttnm = value;
    }

    /**
     * mdclcnsasortcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCLCNSASORTCD() {
        return mdclcnsasortcd;
    }

    /**
     * mdclcnsasortcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCLCNSASORTCD(String value) {
        this.mdclcnsasortcd = value;
    }

    /**
     * mdclcnsno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCLCNSNO() {
        return mdclcnsno;
    }

    /**
     * mdclcnsno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCLCNSNO(String value) {
        this.mdclcnsno = value;
    }

    /**
     * mdcprscdocnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCPRSCDOCNM() {
        return mdcprscdocnm;
    }

    /**
     * mdcprscdocnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCPRSCDOCNM(String value) {
        this.mdcprscdocnm = value;
    }

    /**
     * mdcprscordno 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCPRSCORDNO() {
        return mdcprscordno;
    }

    /**
     * mdcprscordno 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCPRSCORDNO(String value) {
        this.mdcprscordno = value;
    }

    /**
     * mdcdisscode 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCDISSCODE() {
        return mdcdisscode;
    }

    /**
     * mdcdisscode 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCDISSCODE(String value) {
        this.mdcdisscode = value;
    }

    /**
     * mdcanikindsecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCANIKINDSECD() {
        return mdcanikindsecd;
    }

    /**
     * mdcanikindsecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCANIKINDSECD(String value) {
        this.mdcanikindsecd = value;
    }

    /**
     * mdcanicnt 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCANICNT() {
        return mdcanicnt;
    }

    /**
     * mdcanicnt 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCANICNT(String value) {
        this.mdcanicnt = value;
    }

    /**
     * mdcanikindnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCANIKINDNM() {
        return mdcanikindnm;
    }

    /**
     * mdcanikindnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCANIKINDNM(String value) {
        this.mdcanikindnm = value;
    }

    /**
     * mdcanidissnm 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMDCANIDISSNM() {
        return mdcanidissnm;
    }

    /**
     * mdcanidissnm 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMDCANIDISSNM(String value) {
        this.mdcanidissnm = value;
    }

    /**
     * usesecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSESECD() {
        return usesecd;
    }

    /**
     * usesecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSESECD(String value) {
        this.usesecd = value;
    }

    /**
     * usede 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSEDE() {
        return usede;
    }

    /**
     * usede 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSEDE(String value) {
        this.usede = value;
    }

    /**
     * trnsfrsecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRNSFRSECD() {
        return trnsfrsecd;
    }

    /**
     * trnsfrsecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRNSFRSECD(String value) {
        this.trnsfrsecd = value;
    }

    /**
     * dsusesecd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSUSESECD() {
        return dsusesecd;
    }

    /**
     * dsusesecd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSUSESECD(String value) {
        this.dsusesecd = value;
    }

    /**
     * dsuseprvcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSUSEPRVCD() {
        return dsuseprvcd;
    }

    /**
     * dsuseprvcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSUSEPRVCD(String value) {
        this.dsuseprvcd = value;
    }

    /**
     * dsusemthcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSUSEMTHCD() {
        return dsusemthcd;
    }

    /**
     * dsusemthcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSUSEMTHCD(String value) {
        this.dsusemthcd = value;
    }

    /**
     * dsuseloc 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSUSELOC() {
        return dsuseloc;
    }

    /**
     * dsuseloc 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSUSELOC(String value) {
        this.dsuseloc = value;
    }

    /**
     * dsusede 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSUSEDE() {
        return dsusede;
    }

    /**
     * dsusede 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSUSEDE(String value) {
        this.dsusede = value;
    }

    /**
     * dsuseinsttcd 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSUSEINSTTCD() {
        return dsuseinsttcd;
    }

    /**
     * dsuseinsttcd 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSUSEINSTTCD(String value) {
        this.dsuseinsttcd = value;
    }

    /**
     * atchfileco 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATCHFILECO() {
        return atchfileco;
    }

    /**
     * atchfileco 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATCHFILECO(String value) {
        this.atchfileco = value;
    }

    /**
     * registerid 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGISTERID() {
        return registerid;
    }

    /**
     * registerid 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGISTERID(String value) {
        this.registerid = value;
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

    /**
     * lines 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link Lines }
     *     
     */
    public Lines getLINES() {
        return lines;
    }

    /**
     * lines 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link Lines }
     *     
     */
    public void setLINES(Lines value) {
        this.lines = value;
    }

    /**
     * atchfiles 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link AtchFiles }
     *     
     */
    public AtchFiles getATCHFILES() {
        return atchfiles;
    }

    /**
     * atchfiles 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link AtchFiles }
     *     
     */
    public void setATCHFILES(AtchFiles value) {
        this.atchfiles = value;
    }

}
