package kr.pe.withwind.nims.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;
import kr.pe.withwind.nims.domain.CommonCode;
import kr.pe.withwind.nims.domain.ProductInfo;
import kr.pe.withwind.nims.domain.openapi.PlaceList;
import kr.pe.withwind.nims.utils.ApiCall.CALL_TYPE;
import kr.pe.withwind.utils.DerbyException;
import kr.pe.withwind.utils.DerbyManager;

public class ApiCall {
	
	private static final Logger logger = LogManager.getLogger(ApiCall.class);
	
	private static final int THREAD_CNT = 5;
	private static final int THREAD_WAIT_TIME = 2000000; // milliseconds
	
	private boolean isTest;
	private String authKey;
	
	private long totalCnt;
	
	public enum CALL_TYPE {
		/** 품목 정보 */
		PRODUCT_INFO("productinfo.do"),
		/** 품목 정보(대표코드추가) */
		PRODUCT_KD_INFO("productinfo_kd.do"),
		/** 마약류 취급자 정보 */
		BSSH_INFO("bsshinfo_st.do"),
		/** 저장소 정보 */
		PLACE_INFO("placeinfo.do"),
		/** 재고 정보 */
		STOCK_INFO("stockinfo.do"),
		/** 구입대상 정보 */
		PURCHASE_INFO("purchaseinfo.do"),
		/** 구입대상(항목추가) 정보 */
		PURCHASE_PD_INFO("purchaseinfo_pd.do"),
		/** 양수대상 정보 */
		ACCEPT_INFO("acceptinfo.do"),
		/** 양수대상(항목추가) 정보 */
		ACCEPT_PD_INFO("acceptinfo_pd.do"),
		/** 일련번호 정보 */
		SEQ_INFO("seqinfo.do"),
		/** 관할허가관청 정보 */
		OFFICE_INFO("officeinfo.do"),
		/** 상대업체구입 정보 */
		DESTP_INFO("destpinfo.do");
		
		final private String name;
		private CALL_TYPE(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
	}
	
	private int receiveCnt = 0;

	public static void main(String[] args) throws ApiException, InterruptedException, ExecutionException, DerbyException, NimsException {
		
		long stTime = System.currentTimeMillis();
		
//		53d60c5e9233508f59a371c05f9098636c2a5c3f5d69e190c81ce483cb4bd51d -- 테스트 마스터
//		786750000000000105862861350fa950b798c9edea1b38f3cfbcf3b77e03e419 -- 메트로 소프트 TST000064
//		1748b9212678daa5a393556d7700e18eec174e0c5e55b7bb30611098f0dbb39d -- 운영 대전지오영
//		ec385a835304857d1002f3a0184514996dc0d1b3655432906d364f5b1d33db21 -- 운영 (주)한독
//		4b055afdee858f1567f488096882824c139f42dca269dfb3c10d044dfc67889d -- 운영 서울대학교병원
		ApiCall mainClass = new ApiCall(true,"53d60c5e9233508f59a371c05f9098636c2a5c3f5d69e190c81ce483cb4bd51d");
		mainClass.init();

		ArrayList<JSONObject> result = null;
		
//		result = mainClass.doTaskExcute(CALL_TYPE.PRODUCT_KD_INFO,"pg=1&fg=2&p=8806433003128"); // 품목정보
//		result = mainClass.doTaskExcute(CALL_TYPE.BSSH_INFO,"pg=1&fg=1&bn=NIMS"); // 마약류취급자정보
//		result = mainClass.doTaskExcute(CALL_TYPE.PLACE_INFO,"pg=1&fg=1&bc=H00004381"); // 저장소정보
//		result = mainClass.doTaskExcute(CALL_TYPE.STOCK_INFO,"pg=1&fg=1"); // 재고정보
		result = mainClass.doTaskExcute(CALL_TYPE.PURCHASE_INFO,"pg=1&fg=1&ymd=20180501&bn=태종약품주식회사&bc=W00000370&sgtin=0000073933"); // 구입대상 정보
//		result = mainClass.doTaskExcute(CALL_TYPE.PURCHASE_PD_INFO,"pg=1&fg=1&ymd=&bn=&bc=&sgtin="); // 구입대상 정보
//		result = mainClass.doTaskExcute(CALL_TYPE.ACCEPT_PD_INFO,"pg=1&fg=1");
//		result = mainClass.doTaskExcute(CALL_TYPE.SEQ_INFO,"pg=1&fg=3&p=8806451030021&t=0108806469006414211");
//		result = mainClass.doTaskExcute(CALL_TYPE.OFFICE_INFO,"pg=1&fg=1&onm=김포시");
//		result = mainClass.doTaskExcute(CALL_TYPE.DESTP_INFO,"pg=1&fg=1");

		for (int i=0; i < result.size(); i++){
			JSONObject jObj = (JSONObject)result.get(i);
			mainClass.printJObj(jObj);
		}
		
	
		logger.debug("result size : " + result.size());
		logger.debug("소요시간(ms) : " + (System.currentTimeMillis() - stTime));
	}
	
	public ApiCall(boolean isTest, String authKey){
		this.isTest = isTest;
		this.authKey = authKey;
	}
	
	List<JSONArray> jsonArrayList;
	
	public void init(){
		jsonArrayList = new ArrayList<JSONArray>();
	}
	
	private synchronized void appendJsonArr(JSONArray arr){
		this.jsonArrayList.add(arr);
	}
	
	public ArrayList<JSONObject> doTaskExcute(CALL_TYPE callType, String params) throws ApiException, InterruptedException, ExecutionException, NimsException{
		
		
		params = setParmaValue(params, "k", authKey);
		String callUrl = "";
		 
		if (isTest) {
			callUrl = TreeCodeManager.getInstance().getCodeInfo("URL_INFO", "TEST").getCodeDesc() + "/api/" + callType.getName();
		}else {
			callUrl = TreeCodeManager.getInstance().getCodeInfo("URL_INFO", "REAL").getCodeDesc() + "/api/" + callType.getName();
		}
		
		// 첫번째 콜을 날려서 전체 페이지 수를 알아낸다.
		
		totalCnt = 0;
		long perPage = 0;
		
		
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStream os = null;
		try{

			url = new URL(callUrl);

			if (callUrl.contains("https://")) {
				conn = (HttpsURLConnection) url.openConnection();
			}else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
	   
			os = conn.getOutputStream();
			
			os.write(params.getBytes());
			os.flush();
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			
			StringBuffer sb = new StringBuffer();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
			JSONParser jp = new JSONParser();
			JSONObject rootObj = (JSONObject) jp.parse(sb.toString());
			
			JSONObject headerObj = (JSONObject) ((JSONObject) rootObj.get("response")).get("header");
			
			if (!"0".equals(String.valueOf(headerObj.get("RESULT_CODE")))){
				throw new NimsException((String) headerObj.get("RESULT_MSG"));
			}
			
			JSONObject bodyObj = (JSONObject) ((JSONObject) rootObj.get("response")).get("body");
			appendJsonArr((JSONArray) bodyObj.get("list"));
			
			if (bodyObj.get("TOTAL_COUNT") != null) totalCnt = (Long)bodyObj.get("TOTAL_COUNT");
			if (bodyObj.get("nRecord") != null) perPage = (Long)bodyObj.get("nRecord");
			
			
		}catch(NimsException e){
			throw e;
		}catch(Exception e){
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
			throw new ApiException("알수없는 에러가 발생했습니다.", e);
		}finally{
			try{
				if (br != null) br.close();
				if (os != null) os.close();
				if (conn != null) conn.disconnect();
			}catch(Exception e){
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}
		//////////////////////////////////////
		
		logger.debug("server total data count : " + totalCnt);
		logger.debug("page per count : " + perPage);
		
		if (totalCnt < 1) throw new NimsException("조회결과 값이 없습니다.");
		
		// 전체 페이지수 구하기
		long totalPage = (long) Math.ceil((double)totalCnt / (double)perPage);
		logger.debug("total page : " + totalPage);
		
		// 방금 호출한 페이지 구하기
		String pageStr = getParmaValue(params, "pg");
		long fCallPage = 1;
		if (StringUtils.isNumeric(pageStr)) fCallPage = Long.parseLong(pageStr);
		logger.debug("first call page : " + fCallPage);
		
		// 추가로 구할 페이지 수 구하기
		long addPageCnt = totalPage - fCallPage;
		
		final CountDownLatch cdl = new CountDownLatch(addPageCnt > 0 ? (int)addPageCnt : 0);
		
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_CNT);
		
		for (long i = fCallPage + 1; i <= totalPage; i++){
			params = setParmaValue(params, "pg", String.valueOf(i));
			executor.submit(new ApiCallable(callUrl, params, cdl));
		}
		
		executor.shutdown();
		
		try{
			cdl.await(THREAD_WAIT_TIME, TimeUnit.MILLISECONDS);
		}catch(InterruptedException e){
			logger.debug("정상적으로 완료하지 못하였습니다.");
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}
		
		if (!executor.isTerminated()){
			logger.debug("executor is force shutdown !!");
			executor.shutdownNow();
		}

		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		
		for (JSONArray arr : jsonArrayList){
			for (Object tmp : arr){
				JSONObject jObj = (JSONObject)tmp;
				result.add(jObj);
			}
		}
		
		Collections.sort(result, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				return (Long)o1.get("ROW_NUM") > (Long)o2.get("ROW_NUM") ? 1 : -1;
			}
		});
		
		return result;
	}
	
	public void doExcute(CALL_TYPE callType, String params) throws ApiException{
		
		try{
			params = setParmaValue(params, "k", authKey);
			String callUrl = "";
			 
			if (isTest) {
				callUrl = TreeCodeManager.getInstance().getCodeInfo("URL_INFO", "TEST").getCodeDesc() + "/api/" + callType.getName();
			}else {
				callUrl = TreeCodeManager.getInstance().getCodeInfo("URL_INFO", "REAL").getCodeDesc() + "/api/" + callType.getName();
			}
			
			JSONArray reValue = new JSONArray();
			callApi(callUrl,params,reValue);
			
			logger.debug("array length : " + reValue.size());
			logger.debug("total receive count : " + receiveCnt);
		}catch (ApiException e){
			throw e;
		}catch (Exception e){
			throw new ApiException("알수 없는 에러가 발생 하였습니다.", e);
		}
	}
	
	private void callApi(String dest, String params, JSONArray reValue) throws Exception{
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStream os = null;
		try{
			
			String pageStr = getParmaValue(params,"pg");
			int page = 1;
			if (StringUtils.isNumeric(pageStr)){
				page = Integer.parseInt(pageStr);
			}
			
			url = new URL(dest);

			if (dest.contains("https://")) {
				conn = (HttpsURLConnection) url.openConnection();
			}else {
				conn = (HttpURLConnection) url.openConnection();
			}
			
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			
			os = conn.getOutputStream();
			
			os.write(params.getBytes());
			os.flush();
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			
			StringBuffer sb = new StringBuffer();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
//			conn.disconnect();
			
			JSONParser jp = new JSONParser();
			JSONObject rootObj = (JSONObject) jp.parse(sb.toString());
			
			JSONObject headerObj = (JSONObject) ((JSONObject) rootObj.get("response")).get("header");
			
			if (!"0".equals(String.valueOf(headerObj.get("RESULT_CODE")))){
				logger.debug(headerObj.get("RESULT_MSG"));
				throw new ApiException((String) headerObj.get("RESULT_MSG"));
			}
			
			JSONObject bodyObj = (JSONObject) ((JSONObject) rootObj.get("response")).get("body");
			JSONArray listObj = (JSONArray) bodyObj.get("list");
			
			for (Object tmp : listObj){
				JSONObject jObj = (JSONObject)tmp;
//				printJObj(jObj);
				reValue.add(jObj);
				receiveCnt++;
			}
			
			if ("N".equalsIgnoreCase((String) bodyObj.get("IS_END_YN"))){
				page++;
				String nextParam = setParmaValue(params, "pg", String.valueOf(page));
				callApi(dest, nextParam, reValue);
			}
		}catch(Exception e){
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		}finally{
			if (br != null) br.close();
			if (os != null) os.close();
			if (conn != null) conn.disconnect();
		}
	}
	
	private JSONArray callApi(String dest, String params) throws ApiException{
		
		JSONArray reValue = null;
		
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStream os = null;
		try{
						
			url = new URL(dest);

			if (dest.contains("https://")) {
				conn = (HttpsURLConnection) url.openConnection();
			}else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			
			os = conn.getOutputStream();
			
			os.write(params.getBytes());
			os.flush();
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			
			StringBuffer sb = new StringBuffer();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
//			conn.disconnect();
			
			JSONParser jp = new JSONParser();
			JSONObject rootObj = (JSONObject) jp.parse(sb.toString());
			
			JSONObject headerObj = (JSONObject) ((JSONObject) rootObj.get("response")).get("header");
			
			if (!"0".equals(String.valueOf(headerObj.get("RESULT_CODE")))){
				logger.debug(headerObj.get("RESULT_MSG"));
				throw new ApiException((String) headerObj.get("RESULT_MSG"));
			}
			
			JSONObject bodyObj = (JSONObject) ((JSONObject) rootObj.get("response")).get("body");
			reValue = (JSONArray) bodyObj.get("list");
			
		}catch(ApiException e){
			throw e;
		}catch(Exception e){
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
			throw new ApiException("알수없는 에러가 발생했습니다.", e);
		}finally{
			try{
				if (br != null) br.close();
				if (os != null) os.close();
				if (conn != null) conn.disconnect();
			}catch(Exception e){
				logger.error(this.getClass().getSimpleName() + "오류!!", e);
			}
		}
		
		return reValue;
	}
	
	private void printJObj(JSONObject jObj){
		Object[] keys = jObj.keySet().toArray();
		
		logger.debug("==============");
		for (Object key : keys){
//			if (!"ROW_NUM".equals((String)key)) continue; 
			System.out.print(key + " : ");
			logger.debug(jObj.get(key) == null ? "널값" : jObj.get(key));
		}
	}
	
	private String getParmaValue(String params, String key){
		String[] paramArr = params.split("&");
		
		for (String param : paramArr){
			if (param.startsWith(key)){
				return param.substring(param.indexOf(key) + key.length() + 1);
			}
		}
		
		return null;
	}
	
	private String setParmaValue(String params, String key, String value){
		
		if (params == null) return key + "=" + value;

		boolean hasKey = false;
		
		String[] paramArr = params.split("&");
		for (int i=0; i < paramArr.length; i++){
			if (paramArr[i].startsWith(key)){
				hasKey = true;
				paramArr[i] = key + "=" + value;
				break;
			}
		}
		
		String reValue = StringUtils.join(paramArr, '&');
		
		if (!hasKey) reValue += "&"+key+"="+value;
				
		return reValue;
	}
	
	
	class ApiCallable implements Callable<JSONArray>{
		
		private String callUrl;
		private String params;
		private CountDownLatch cdl;
		
		public ApiCallable(String callUrl, String params, CountDownLatch cdl) {
			this.callUrl = callUrl;
			this.params = params;
			this.cdl = cdl;
		}

		@Override
		public JSONArray call() throws Exception {
			try{
				JSONArray reValue = callApi(callUrl,params);
				appendJsonArr(reValue);
				return reValue;
			}finally{
				if (cdl != null) cdl.countDown();
			}
		}
	}
	
	
	public static List<TreeCode> storgeSearch(String bsshCd) throws NimsException {
		
		ArrayList<TreeCode> reValue = new ArrayList<TreeCode>();
		
		CommonCode isTestCode = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE","IS_TEST");
		boolean isTest = isTestCode.getCodeNm().equals("Y") ? true : false;
		
		String urlParam = "pg=1&fg=1";
		urlParam += "&bc=" + bsshCd;

		try{
			
			String authKey = BsshInfoManager.getInstance().getBsshInfo(isTest ? TYPE.TEST : TYPE.REAL).getAuthKey();
			
			ApiCall mainClass = new ApiCall(isTest,authKey);
			mainClass.init();
			
			ArrayList<JSONObject> result = new ArrayList<JSONObject>();
			try {
				result = mainClass.doTaskExcute(CALL_TYPE.PLACE_INFO,urlParam);
			}catch(NimsException e) {}

			for (JSONObject jObj : result) {
				TreeCode temp = new TreeCode();
				temp.setCode((String) jObj.get("STORGE_NO"));
				temp.setCodeNm((String) jObj.get("STORGE_NM"));
				reValue.add(temp);
			}
			
			if (reValue.isEmpty()) {
				TreeCode temp = new TreeCode();
				temp.setCode("S0001");
				temp.setCodeNm("기본저장소");
				reValue.add(temp);
			}
			
			TreeCode temp = new TreeCode();
			temp.setCode("S0002");
			temp.setCodeNm("테스트저장소");
			reValue.add(temp);
			
		}catch(Exception e) {
			throw new NimsException(e);
		}
		
		return reValue;
	}
	
	public static void RFIDRead(){
		// 코드를 16진수로 읽어 BigInteger를 만드로 2진수 표시 형식의 String을 만든다.
		String bin = new BigInteger("30361981880159C080000522", 16).toString(2);
		// 96자리(96 bit 자리를 맞춰준다.)
		bin = String.format("%96s", bin).replace(' ','0');
	    
	    int stIdx = 14;
	    BigInteger company = new BigInteger(bin.substring(stIdx, 24 + stIdx), 2);
	    // 업소코드 6자리를 맞춘다.
	    String comCode = String.format("%7s", company.toString()).replace(' ','0');
	    
	    stIdx += 24;
	    BigInteger product = new BigInteger(bin.substring(stIdx, 20 + stIdx), 2);
	    String prodCode = String.format("%6s", product.toString()).replace(' ','0');
	    
	    stIdx += 20;
	    BigInteger serial = new BigInteger(bin.substring(stIdx, 38 + stIdx), 2);
	    String serialCode = String.format("%12s", serial.toString()).replace(' ','0');
	    
	    logger.debug("company code : " + company.toString());
	    logger.debug("product code : " + product.toString());
	    logger.debug("serial code : " + serial.toString());
	    
	    logger.debug("comCode : " + comCode);
	    logger.debug("prodCode : " + prodCode);
	    logger.debug("serialCode : " + serialCode);
	    
	    String productCode = comCode + prodCode.substring(1);
	    
	    logger.debug("productCode code : " + productCode);
	    
	    // 검증코드를 알아낸다.
	    // 참고 사이트 http://www.idsystems.co.kr/barcode/barcode_2.htm
	    char[] charArr = new char[12];
	    
	    for (int i=0; i < productCode.length(); i++){
	    	charArr[i] = productCode.charAt(i);
	    }
	    
	    int dept1 = 0;
	    for (int i=1; i < charArr.length; i += 2){
	    	dept1 += Integer.parseInt(String.valueOf(charArr[i]));
	    }
	    
	    logger.debug("dept1 : " + dept1);
	    
	    int dept2 = dept1 * 3;
	    
	    logger.debug("dept2 : " + dept2);
	    
	    int dept3 = 0;
	    for (int i=0; i < charArr.length; i += 2){
	    	dept3 += Integer.parseInt(String.valueOf(charArr[i]));
	    }
	    
	    logger.debug("dept3 : " + dept3);
	    
	    int dept4 = dept2 + dept3;
	    
	    logger.debug("dept4 : " + dept4);
	    
	    int check = 10 - (dept4 % 10);
	    
	    logger.debug("check : " + check);
	    
	    String finalCode = productCode + String.valueOf(check);
	    
	    logger.debug("finalCode : " + finalCode);
	}

	public long getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(long totalCnt) {
		this.totalCnt = totalCnt;
	}
	
	
}
