package kr.pe.withwind.nims.utils;

import java.util.HashMap;
import java.util.Map;

public class VoMapByRptType {
	
	// 채널코드
	public static final String CHANNEL_API = "API";
	public static final String CHANNEL_ESB = "ESB";
	public static final String CHANNEL_WEB = "UPL";

	// 파일 확장자
	public static final String EXT_ZIP = ".zip";
	public static final String EXT_XML = ".xml";
	public static final String EXT_CSV = ".csv";

	// 보고서 처리결과 코드
	public static final String R0000 = "0000"; // 성공
	public static final String R1101 = "1101"; // 파일읽기실패(경로 및 파일 정보 에러)
	public static final String R1102 = "1102"; // SQL Exception
	public static final String R1301 = "1301"; // 파싱필드에러

	// 보고 유형 코드
	public static final String RPT_TY_CD_NEW = "0"; // 신규
	public static final String RPT_TY_CD_CANCEL = "1"; // 취소
	public static final String RPT_TY_CD_MODIFY = "2"; // 변경
	
	// 보고 단계 코드
	public static final String STEP_CHCK_FLNM = "010";	// 파일명체크
	public static final String STEP_RECV_FILE = "020";	// 파일수신
	public static final String STEP_PARSE_FILE = "030";	// 파일내용체크
	public static final String STEP_INSRT_TMP = "040";	// 임시테이블입력
	public static final String STEP_PROC_CALL = "050";	// 프로시저 호출
	
	public HashMap<String,String[]> headerInfo = new HashMap<String,String[]>();
	public HashMap<String,String[]> lineInfo = new HashMap<String,String[]>();
	
	public VoMapByRptType() {
		headerInfo.put("PCM", csvHeaderPCM);
		lineInfo.put("PCM", csvLinePCM);
		
		headerInfo.put("SLM", csvHeaderSLM);
		lineInfo.put("SLM", csvLineSLM);
		
		headerInfo.put("TNT", csvHeaderTNT);
		lineInfo.put("TNT", csvLineTNT);
		
		headerInfo.put("TNI", csvHeaderTNI);
		lineInfo.put("TNI", csvLineTNI);
		
		headerInfo.put("EPM", csvHeaderEPM);
		lineInfo.put("EPM", csvLineEPM);
		
		headerInfo.put("IPM", csvHeaderIPM);
		lineInfo.put("IPM", csvLineIPM);
		
		headerInfo.put("PMM", csvHeaderPMM);
		lineInfo.put("PMM", csvLinePMM);
		
		headerInfo.put("MCM", csvHeaderMCM);
		lineInfo.put("MCM", csvLineMCM);
		
		headerInfo.put("PDM", csvHeaderPDM);
		lineInfo.put("PDM", csvLinePDM);
		
		headerInfo.put("MTM", csvHeaderMTM);
		lineInfo.put("MTM", csvLineMTM);
		
		headerInfo.put("UEM", csvHeaderUEM);
		lineInfo.put("UEM", csvLineUEM);
		
		headerInfo.put("AAR", csvHeaderAAR);
		lineInfo.put("AAR", csvLineAAR);
		
		headerInfo.put("CNT", csvHeaderCNT);
		lineInfo.put("CNT", csvLineCNT);
		
		headerInfo.put("CNI", csvHeaderCNI);
		lineInfo.put("CNI", csvLineCNI);
		
		headerInfo.put("ETT", csvHeaderETT);
		lineInfo.put("ETT", csvLineETT);
		
		headerInfo.put("ETI", csvHeaderETI);
		lineInfo.put("ETI", csvLineETI);
		
		headerInfo.put("SMM", csvHeaderSMM);
		lineInfo.put("SMM", csvLineSMM);
	}
	
	public Map<String,String[]> getHeaderInfo(){
		return this.headerInfo;
	}
	public Map<String,String[]> getLineInfo(){
		return this.lineInfo;
	}
	
	public final static String[] csvHeaderPCM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO", "ATCHFILECO",
			"REGISTERID", "FILECREATDT" };
	public final static String[] csvLinePCM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// 판매 : SLM
	public final static String[] csvHeaderSLM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO", "ATCHFILECO",
			"REGISTERID", "FILECREATDT" };
	public final static String[] csvLineSLM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// 양도 :TNT
	public final static String[] csvHeaderTNT = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO", "TRNSFRSECD",
			"ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineTNT = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// 양수 : TNI
	public final static String[] csvHeaderTNI = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO", "TRNSFRSECD",
			"ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineTNI = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// 수출 : EPM
	public final static String[] csvHeaderEPM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "EXIMCOUNTRYCD", "EXIMOSEADELNGNM", "EXIMCONFMNO",
			"EXIMNOTIFYCERTNO", "EXIMOSEADELNGSIGN", "EXIMENTRDE", "EXIMIPPSPOTDE", "EXIMIODE",
			"EXIMUCAMT", "EXIMAMT", "ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineEPM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// IPM : 수입
	public final static String[] csvHeaderIPM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RPTSEDTLCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "EXIMCOUNTRYCD", "EXIMOSEADELNGNM", "EXIMCONFMNO",
			"EXIMNOTIFYCERTNO", "EXIMOSEADELNGSIGN", "EXIMENTRDE", "EXIMIPPSPOTDE", "EXIMIODE",
			"EXIMUCAMT", "EXIMAMT", "ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineIPM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// PMM : 조제
	public final static String[] csvHeaderPMM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "MDCPATSECD", "MDCPATIDNOTYCD", "MDCPATIDNO", "MDCPATNM",
			"MDCHPTLNO", "MDCINSTTNM", "MDCLCNSASORTCD", "MDCLCNSNO", "MDCPRSCDOCNM", "MDCPRSCORDNO",
			"MDCDISSCODE", "MDCANIKINDSECD", "MDCANICNT", "MDCANIKINDNM", "MDCANIDISSNM", "ATCHFILECO",
			"REGISTERID", "FILECREATDT" };
	public final static String[] csvLinePMM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT", "PRDUCTNM", "PRDSGTIN",
			"PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "MDCSUMQY", "MDCONCEQY", "MDCADECNT",
			"MDCTOTDCNT", "MDCAFTDSUSEQY", "MDCAFTDSUSEUNIT", "FILECREATDT" };

	// MCM : 투약
	public final static String[] csvHeaderMCM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "MDCPATSECD", "MDCPATIDNOTYCD", "MDCPATIDNO", "MDCPATNM",
			"MDCHPTLNO", "MDCINSTTNM", "MDCLCNSASORTCD", "MDCLCNSNO", "MDCPRSCDOCNM", "MDCPRSCORDNO",
			"MDCDISSCODE", "MDCANIKINDSECD", "MDCANICNT", "MDCANIKINDNM", "MDCANIDISSNM", "ATCHFILECO",
			"REGISTERID", "FILECREATDT" };
	public final static String[] csvLineMCM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT", "PRDUCTNM", "PRDSGTIN",
			"PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "MDCSUMQY", "MDCONCEQY", "MDCADECNT",
			"MDCTOTDCNT", "MDCAFTDSUSEQY", "MDCAFTDSUSEUNIT", "FILECREATDT" };

	// PDM : 제조
	public final static String[] csvHeaderPDM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "NONNRCDMTRALAT", "MNFSTRTDE", "MNFENDDE", "MNFCNSGNSECD",
			"MNFTOTQY", "MNFPRDUCAMT", "MNFPRDAMT", "MNFYIELDRATE", "ATCHFILECO", "REGISTERID",
			"FILECREATDT" };
	public final static String[] csvLinePDM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// MTM : 원료사용
	public final static String[] csvHeaderMTM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "MNFTOTQY", "ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineMTM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT", "PRDUCTNM",
			"PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// UEM : 사용
	public final static String[] csvHeaderUEM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "USESECD", "USEDE", "ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineUEM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT", "PRDUCTNM", "PRDSGTIN",
			"PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// AAR : 폐기
	public final static String[] csvHeaderAAR = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "DSUSESECD", "DSUSEPRVCD", "DSUSEMTHCD", "DSUSELOC", "DSUSEDE",
			"DSUSEINSTTCD", "ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineAAR = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// CNT : 위수탁출고
	public final static String[] csvHeaderCNT = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RPTSEDTLCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM",
			"CHRGTELNO", "CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO",
			"ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineCNT = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// CNI : 위수탁입고
	public final static String[] csvHeaderCNI = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RPTSEDTLCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM",
			"CHRGTELNO", "CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO",
			"ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineCNI = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// ETT : 기타출고
	public final static String[] csvHeaderETT = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RPTSEDTLCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM",
			"CHRGTELNO", "CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO",
			"ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineETT = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// ETI : 기타입고
	public final static String[] csvHeaderETI = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RPTSEDTLCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM",
			"CHRGTELNO", "CHRGMPNO", "RNDDTLRPTCNT", "OPPBSSHNM", "OPPBSSHCD", "OPPSTORGENO",
			"ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineETI = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

	// SMM : 저장소이동
	public final static String[] csvHeaderSMM = { "HDRDE", "BSSHCD", "RPTSECD", "USRRPTIDNO",
			"REFUSRRPTIDNO", "RPTTYCD", "RMK", "RPTRNM", "RPTRENTRPSNM", "CHRGNM", "CHRGTELNO",
			"CHRGMPNO", "RNDDTLRPTCNT", "OPPSTORGENO", "ATCHFILECO", "REGISTERID", "FILECREATDT" };
	public final static String[] csvLineSMM = { "USRRPTIDNO", "USRRPTLNIDNO", "STORGENO", "MVMNTYCD",
			"PRDUCTCD", "MNFNO", "MNFSEQ", "MINDISTBQY", "PRDMINDISTBUNIT", "PCEQY", "PRDPCEUNIT",
			"PRDUCTNM", "PRDSGTIN", "PRDMINDISTBQY", "PRDTOTPCEQY", "PRDVALIDDE", "FILECREATDT" };

//	// 구입 : PCM
//	public final static String[] csvHeaderPCM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO", "ATCH_FILE_CO",
//			"REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLinePCM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// 판매 : SLM
//	public final static String[] csvHeaderSLM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO", "ATCH_FILE_CO",
//			"REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineSLM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// 양도 :TNT
//	public final static String[] csvHeaderTNT = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO", "TRNSFR_SE_CD",
//			"ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineTNT = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// 양수 : TNI
//	public final static String[] csvHeaderTNI = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO", "TRNSFR_SE_CD",
//			"ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineTNI = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// 수출 : EPM
//	public final static String[] csvHeaderEPM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "EXIM_COUNTRY_CD", "EXIM_OSEA_DELNG_NM", "EXIM_CONFM_NO",
//			"EXIM_NOTIFY_CERT_NO", "EXIM_OSEA_DELNG_SIGN", "EXIM_ENTR_DE", "EXIM_IPP_SPOT_DE", "EXIM_IO_DE",
//			"EXIM_UC_AMT", "EXIM_AMT", "ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineEPM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// IPM : 수입
//	public final static String[] csvHeaderIPM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RPT_SE_DTL_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "EXIM_COUNTRY_CD", "EXIM_OSEA_DELNG_NM", "EXIM_CONFM_NO",
//			"EXIM_NOTIFY_CERT_NO", "EXIM_OSEA_DELNG_SIGN", "EXIM_ENTR_DE", "EXIM_IPP_SPOT_DE", "EXIM_IO_DE",
//			"EXIM_UC_AMT", "EXIM_AMT", "ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineIPM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// PMM : 조제
//	public final static String[] csvHeaderPMM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "MDC_PAT_SE_CD", "MDC_PAT_ID_NO_TY_CD", "MDC_PAT_ID_NO", "MDC_PAT_NM",
//			"MDC_HPTL_NO", "MDC_INSTT_NM", "MDC_LCNS_ASORT_CD", "MDC_LCNS_NO", "MDC_PRSC_DOC_NM", "MDC_PRSC_ORD_NO",
//			"MDC_DISS_CODE", "MDC_ANI_KIND_SE_CD", "MDC_ANI_CNT", "MDC_ANI_KIND_NM", "MDC_ANI_DISS_NM", "ATCH_FILE_CO",
//			"REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLinePMM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT", "PRDUCT_NM", "PRD_SGTIN",
//			"PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "MDC_SUM_QY", "MDC_ONCE_QY", "MDC_ADE_CNT",
//			"MDC_TOT_DCNT", "MDC_AFT_DSUSE_QY", "MDC_AFT_DSUSE_UNIT", "FILE_CREAT_DT" };
//
//	// MCM : 투약
//	public final static String[] csvHeaderMCM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "MDC_PAT_SE_CD", "MDC_PAT_ID_NO_TY_CD", "MDC_PAT_ID_NO", "MDC_PAT_NM",
//			"MDC_HPTL_NO", "MDC_INSTT_NM", "MDC_LCNS_ASORT_CD", "MDC_LCNS_NO", "MDC_PRSC_DOC_NM", "MDC_PRSC_ORD_NO",
//			"MDC_DISS_CODE", "MDC_ANI_KIND_SE_CD", "MDC_ANI_CNT", "MDC_ANI_KIND_NM", "MDC_ANI_DISS_NM", "ATCH_FILE_CO",
//			"REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineMCM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT", "PRDUCT_NM", "PRD_SGTIN",
//			"PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "MDC_SUM_QY", "MDC_ONCE_QY", "MDC_ADE_CNT",
//			"MDC_TOT_DCNT", "MDC_AFT_DSUSE_QY", "MDC_AFT_DSUSE_UNIT", "FILE_CREAT_DT" };
//
//	// PDM : 제조
//	public final static String[] csvHeaderPDM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "NON_NRCD_MTRAL_AT", "MNF_STRT_DE", "MNF_END_DE", "MNF_CNSGN_SE_CD",
//			"MNF_TOT_QY", "MNF_PRD_UC_AMT", "MNF_PRD_AMT", "MNF_YIELD_RATE", "ATCH_FILE_CO", "REGISTER_ID",
//			"FILE_CREAT_DT" };
//	public final static String[] csvLinePDM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// MTM : 원료사용
//	public final static String[] csvHeaderMTM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "MNF_TOT_QY", "ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineMTM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT", "PRDUCT_NM",
//			"PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// UEM : 사용
//	public final static String[] csvHeaderUEM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "USE_SE_CD", "USE_DE", "ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineUEM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT", "PRDUCT_NM", "PRD_SGTIN",
//			"PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// AAR : 폐기
//	public final static String[] csvHeaderAAR = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "DSUSE_SE_CD", "DSUSE_PRV_CD", "DSUSE_MTH_CD", "DSUSE_LOC", "DSUSE_DE",
//			"DSUSE_INSTT_CD", "ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineAAR = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// CNT : 위수탁출고
//	public final static String[] csvHeaderCNT = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RPT_SE_DTL_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM",
//			"CHRG_TEL_NO", "CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO",
//			"ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineCNT = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// CNI : 위수탁입고
//	public final static String[] csvHeaderCNI = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RPT_SE_DTL_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM",
//			"CHRG_TEL_NO", "CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO",
//			"ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineCNI = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// ETT : 기타출고
//	public final static String[] csvHeaderETT = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RPT_SE_DTL_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM",
//			"CHRG_TEL_NO", "CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO",
//			"ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineETT = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// ETI : 기타입고
//	public final static String[] csvHeaderETI = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RPT_SE_DTL_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM",
//			"CHRG_TEL_NO", "CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_BSSH_NM", "OPP_BSSH_CD", "OPP_STORGE_NO",
//			"ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineETI = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
//
//	// SMM : 저장소이동
//	public final static String[] csvHeaderSMM = { "HDR_DE", "BSSH_CD", "RPT_SE_CD", "USR_RPT_ID_NO",
//			"REF_USR_RPT_ID_NO", "RPT_TY_CD", "RMK", "RPTR_NM", "RPTR_ENTRPS_NM", "CHRG_NM", "CHRG_TEL_NO",
//			"CHRG_MP_NO", "RND_DTL_RPT_CNT", "OPP_STORGE_NO", "ATCH_FILE_CO", "REGISTER_ID", "FILE_CREAT_DT" };
//	public final static String[] csvLineSMM = { "USR_RPT_ID_NO", "USR_RPT_LN_ID_NO", "STORGE_NO", "MVMN_TY_CD",
//			"PRDUCT_CD", "MNF_NO", "MNF_SEQ", "MIN_DISTB_QY", "PRD_MIN_DISTB_UNIT", "PCE_QY", "PRD_PCE_UNIT",
//			"PRDUCT_NM", "PRD_SGTIN", "PRD_MIN_DISTB_QY", "PRD_TOT_PCE_QY", "PRD_VALID_DE", "FILE_CREAT_DT" };
}
