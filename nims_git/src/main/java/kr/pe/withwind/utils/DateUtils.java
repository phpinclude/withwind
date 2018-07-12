package kr.pe.withwind.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kr.pe.withwind.nims.jetty.NimsHttp;

public class DateUtils {
	
	public static Logger logger = LogManager.getLogger(DateUtils.class);
	
	public static final String YYMMDD = "yyMMdd";
	public static final String YYYYMM = "yyyyMM";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDHHMISS = "yyyyMMddHHmmss";
	public static final String YYYYMMDDHHMISSS = "yyyyMMddHHmmssSSS";
	
	public static String convertDateStr(String param, String fromFormat, String toFormat) throws ParseException {
		fromFormat = "MM-dd HH:mm";
		SimpleDateFormat fromSdf = new SimpleDateFormat(fromFormat);
		Date date = fromSdf.parse(param);

		SimpleDateFormat toSdf = new SimpleDateFormat(toFormat);
		return toSdf.format(date);
	}
	
	public static String getYYMMDD() {
		return getFormatted(YYMMDD, new Date());
	}
	
	public static String getYYYYMMDD() {
		return getFormatted(YYYYMMDD, new Date());
	}
	
	public static String getYYYYMM() {
		return getFormatted(YYYYMM, new Date());
	}
	
	public static String getFormatted(String format) {
		return getFormatted(format, new Date());
	}
	
	public static String getYYMMDD(Date date) {
		return getFormatted(YYMMDD, date);
	}
	
	public static String getYYYYMMDD(Date date) {
		return getFormatted(YYYYMMDD, date);
	}
	
	public static String getYYYYMM(Date date) {
		return getFormatted(YYYYMM, date);
	}
	
	public static String getFormatted(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static void main(String[] arg) throws org.json.simple.parser.ParseException {
		
		String json = "{\"notification\":{\"messageId\":\"29f7d2b1-9698-5f7f-8f7e-81144d3097e0\",\"timestamp\":\"2018-06-21 16:59:20.041\"},\"delivery\":{\"phoneCarrier\":\"AT&T\",\"mnc\":180,\"destination\":\"+13055420723\",\"priceInUSD\":0.00645,\"smsType\":\"Transactional\",\"mcc\":311,\"providerResponse\":\"Message has been accepted by phone\",\"dwellTimeMs\":1027,\"dwellTimeMsUntilDeviceAck\":2568},\"status\":\"SUCCESS\"}";
		
		logger.info("JSON String : \n "+json);
		JSONObject jsonObj = null;
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(json);
		logger.info("obj \n" + obj );
		logger.info("obj istanceof Object : "+  (obj instanceof Object));
		logger.info("obj istanceof String : "+  (obj instanceof String));
		logger.info("obj istanceof JSONObject : "+  (obj instanceof JSONObject));
		jsonObj = (JSONObject) obj;
		logger.info("jsonObj istanceof Object : "+  (jsonObj instanceof Object));
		logger.info("jsonObj istanceof JSONObject : "+  (jsonObj instanceof JSONObject));
		logger.info(jsonObj.toJSONString());
	}
}