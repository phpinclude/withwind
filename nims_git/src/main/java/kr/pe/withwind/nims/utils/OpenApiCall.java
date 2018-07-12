package kr.pe.withwind.nims.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class OpenApiCall {
	
	private static final Logger logger = LogManager.getLogger(OpenApiCall.class);
	
	public static void main(String[] arg){
		
		String dest = "https://test.nims.or.kr/api/productinfo.do";
		String params = "k=845210000000000603820395250fa950b798c9edea1b38f3cfbcf3b77e03e419&pg=1&fg=1";
		
		URL url = null;
		HttpsURLConnection conn = null;
		BufferedReader br = null;
		OutputStream os = null;
		try{
			
			url = new URL(dest);

			conn = (HttpsURLConnection) url.openConnection();
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
			
			logger.debug(sb.toString());
			
		}catch(Exception e){
			logger.error(OpenApiCall.class.getSimpleName() + "오류!!", e);
		}finally{
			try{
				if (br != null) br.close();
				if (os != null) os.close();
				if (conn != null) conn.disconnect();
			}catch(Exception e){
				logger.error(OpenApiCall.class.getSimpleName() + "오류!!", e);
			}
		}
	}
}
