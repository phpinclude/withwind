package kr.pe.withwind.nims.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.pe.withwind.common.TreeCode;
import kr.pe.withwind.common.TreeCodeManager;
import kr.pe.withwind.nims.BsshInfoManager;
import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.BsshInfoManager.TYPE;

public class MultiPartUpload {

	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private String uid;
	
	public MultiPartUpload(String uid){
		this.uid=uid;
	}
	
	public String sendFile(File file) throws IOException{
		
		TreeCode code = TreeCodeManager.getInstance().getCodeInfo("NIMS_CODE", "IS_TEST");
		String url = TreeCodeManager.getInstance().getCodeInfo("URL_INFO", code.getCodeNm().equals("N") ? "REAL" : "TEST").getCodeDesc() + "api/upload_s.do";
		return sendFile(file,url);
	}
	
	public String sendFile(File file, String url) throws IOException{
		
		String responseBody = "";
		
		String fileName = file.getName().substring(file.getName().lastIndexOf('\\')+1);
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpEntity data = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("file", file, ContentType.DEFAULT_BINARY, fileName)
                    .addTextBody("key", uid, ContentType.DEFAULT_BINARY)
                    .build();

            HttpUriRequest request = RequestBuilder
                    .post(url)
                    .setEntity(data)
                    .build();
			
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					 int status = response.getStatusLine().getStatusCode();
		                if (status >= 200 && status < 300) {
		                    HttpEntity entity = response.getEntity();
		                    return entity != null ? EntityUtils.toString(entity) : null;
		                } else {
		                    throw new ClientProtocolException("Unexpected response status: " + status);
		                }
				}
			};

            responseBody = httpclient.execute(request, responseHandler);
		} catch (IOException e) {
			throw e;
		}
		
		return responseBody;
	}
	
	public String sendFile2() throws IOException{
		
		String authKey = "53d60c5e9233508f59a371c05f9098636c2a5c3f5d69e190c81ce483cb4bd51d"; // 개발
//		String authKey = "200ea43088f5becfc05d033ec2ac3e636d41eea0cdc9325a4522c100547dcf39"; // 운영
		String responseBody = null;
		String BOUNDARY_UNIQ_STR = "BOUNDARY_UNIQ_STR";
		String fileName = "C:\\보고문서테스트\\TSTNIMSW1SLM20180426172312_0001.xml";
		
		// 데이터부 key 값을 셋팅
		String sendStr = "--" + BOUNDARY_UNIQ_STR + "\r\n";
		sendStr += "Content-Disposition: form-data; name=\"key\"" + "\r\n" + "\r\n";
		sendStr += authKey + "\r\n";
		
		// 파일내용
		sendStr +=  "--" + BOUNDARY_UNIQ_STR + "\r\n";
		sendStr += "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + "\r\n";
		sendStr += "Content-Type: text/plain" + "\r\n" + "\r\n";
		
		// 문자열 부분을 바이트 변환하여 버퍼에 넣는다.
		byte[] sendFirstBuffer = sendStr.getBytes();
		// 파일을 읽어 파일버퍼에 넣는다.
		File sendFile = new File(fileName);
		FileInputStream fis = new FileInputStream(sendFile);
		byte[] fileBuffer = new byte[fis.available()];
		fis.read(fileBuffer);
		
		// 마지막 문자열을 바이트로 변환한다.
		String sendEndStr = "\r\n"; 
		sendEndStr += "--" + BOUNDARY_UNIQ_STR + "--"+"\r\n";
		byte[] sendEndBuffer = sendEndStr.getBytes();

		
		String url = "https://test.nims.or.kr/api/upload_s.do"; // 개발
//		String url = "https://www.nims.or.kr/api/upload_s.do"; // 운영
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", "Mozilla/5.0");
//		con.setRequestProperty("Accept-Language", "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY_UNIQ_STR);
		con.setRequestProperty("Content-Length",String.valueOf(sendFirstBuffer.length + fileBuffer.length + sendEndBuffer.length));
		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(sendFirstBuffer);
		wr.write(fileBuffer);
		wr.write(sendEndBuffer);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		logger.debug(">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<");
		logger.debug(response.toString());
		logger.debug(">>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<");
		
		return responseBody;
	}
	
	public static void main(String... args) throws IOException {
		
		
		MultiPartUpload mainClass = new MultiPartUpload("845210000000000603820395250fa950b798c9edea1b38f3cfbcf3b77e03e419");
//		String responseStr = mainClass.sendFile(new File("C:\\보고문서테스트\\MTM_Report.xlsx"));
//		logger.debug(responseStr);
		
		mainClass.sendFile2();

//        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
//
//            File file = new File(MultiPartUpload.class.getResource("/nims.xsd").getFile());
//
//            // build multipart upload request
//            HttpEntity data = MultipartEntityBuilder.create()
//                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//                    .addBinaryBody("file", file, ContentType.DEFAULT_BINARY, "TST000123PMM20180321191101_0002.xml")
//                    .addTextBody("key", "845210000000000603820395250fa950b798c9edea1b38f3cfbcf3b77e03e419", ContentType.DEFAULT_BINARY)
//                    .build();
//
//            // build http request and assign multipart upload data
//            HttpUriRequest request = RequestBuilder
//                    .post("https://test.nims.or.kr/api/upload.do")
//                    .build();
//
//            logger.debug("Executing request " + request.getRequestLine());
//
//            // Create a custom response handler
//            ResponseHandler<String> responseHandler = response -> {
//                int status = response.getStatusLine().getStatusCode();
//                if (status >= 200 && status < 300) {
//                    HttpEntity entity = response.getEntity();
//                    return entity != null ? EntityUtils.toString(entity) : null;
//                } else {
//                    throw new ClientProtocolException("Unexpected response status: " + status);
//                }
//            };
//
//            String responseBody = httpclient.execute(request, responseHandler);
//            logger.debug("----------------------------------------");
//            logger.debug(responseBody);
//        }
    }
}
