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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="REPORT_SET" type="{https://www.nims.or.kr/schema/nims}reportSet"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reportset"
})
@XmlRootElement(name = "pdm_regist")
public class PdmRegist implements Regist {

    @XmlElement(name = "REPORT_SET", required = true)
    protected ReportSet reportset;

    /**
     * reportset 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link ReportSet }
     *     
     */
    public ReportSet getREPORTSET() {
        return reportset;
    }

    /**
     * reportset 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportSet }
     *     
     */
    public void setREPORTSET(ReportSet value) {
        this.reportset = value;
    }

}
