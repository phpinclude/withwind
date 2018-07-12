package kr.pe.withwind.nims.utils;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.pe.withwind.nims.domain.BarcodeInfo;

public class RFIDRead {
	
	private static final Logger logger = LogManager.getLogger(RFIDRead.class);
	
	public static void main(String[] args) {
		
//		String barcode = "0108806599010329172002141070207012/1 2101435";
//		
//		BarcodeInfo info = getBarcodeInfoByBarcode(barcode.getBytes());
//		logger.debug(info);
		
		logger.debug(RFIDRead.getBarcodeInfoByRFID("303619817C05FA410A680B73"));
		
		if (true) return;
		
		
		
		long stTime = System.currentTimeMillis();
//		30361981880143C08001A5D4
//		30361981880618808000657c5e73
//		BigInteger test = new BigInteger("30361981880159C08001EE5F", 16);//30361981880159c0800004a9
//		BigInteger test = new BigInteger("30361981880159C08001D974", 16);//30361981880159c0800004a9
//		BigInteger mask1 = new BigInteger("11111111", 2);
//		BigInteger mask2 = new BigInteger("111", 2);
//		BigInteger mask3 = new BigInteger("111", 2);
//		BigInteger mask4 = new BigInteger("111111111111111111111111", 2);
//		BigInteger mask5 = new BigInteger("11111111111111111111", 2);
//		BigInteger mask6 = new BigInteger("11111111111111111111111111111111111111", 2);
//
//		logger.debug("header : " + test.shiftRight(88).and(mask1).toString(2));
//		logger.debug("filter : " + test.shiftRight(85).and(mask2).toString(2));
//		logger.debug("parti : " + test.shiftRight(82).and(mask3).toString(2));
//		logger.debug("comcode : " + test.shiftRight(58).and(mask4).toString(10));
//		logger.debug("prodcode : " + test.shiftRight(38).and(mask5).toString(10));
//		logger.debug("serial : " + test.and(mask6).toString());
//		
//		String aa = test.shiftRight(58).and(mask4).toString(10) + String.format("%6s", test.shiftRight(38).and(mask5).toString(10)).replace(' ','0').substring(1);
//		logger.debug(aa);
//		logger.debug("process time 1 -> " + (System.currentTimeMillis() - stTime));
		
		stTime = System.currentTimeMillis();
		
		String bin = new BigInteger("30361981880159C08001D974", 16).toString(2);
		
		bin = String.format("%96s", bin).replace(' ','0');
	    BigInteger company = new BigInteger(bin.substring(14, 24 + 14), 2);
	    String comCode = String.format("%7s", company.toString()).replace(' ','0');
	    BigInteger product = new BigInteger(bin.substring(38, 20 + 38), 2);
	    String prodCode = String.format("%6s", product.toString()).replace(' ','0');
	    
	    String productCode = comCode + prodCode.substring(1);
	    logger.debug(productCode);
	    logger.debug("process time 2 -> " + (System.currentTimeMillis() - stTime));

	    BigInteger serial = new BigInteger(bin.substring(58, 38 + 58), 2);
	    String serialCode = String.format("%12s", serial.toString()).replace(' ','0');
	    
	    int ch=0;
	    for (int i=0; i < productCode.length(); i++){
	    	if (i%2 == 0){
	    		ch += Integer.parseInt(productCode.substring(i,(i+1)));
	    	}else{
	    		ch += Integer.parseInt(productCode.substring(i,(i+1))) * 3;
	    	}
	    }
	    String finalCode = productCode + (10 - (ch % 10));
	    logger.debug("finalCode : " + finalCode);
	    logger.debug("serialCode : " + serialCode);
	    
//	    String aaa = "880649501371";
	    String aaa = "880667400811";
	    ch=0;
	    for (int i=0; i < aaa.length(); i++){
	    	if (i % 2 == 0){
	    		ch += Integer.parseInt(aaa.substring(i,(i+1)));
	    	}else{
	    		ch += Integer.parseInt(aaa.substring(i,(i+1))) * 3;
	    	}
	    }
	    finalCode = aaa + (10 - ((ch % 10) == 0 ? 10 : (ch % 10)));
	    logger.debug(aaa + " to finalCode : " + finalCode);
	    
	}
	
	public static BarcodeInfo getBarcodeInfoByRFID(String rfid) {
		
		
		BarcodeInfo reValue = new BarcodeInfo();
		
		String bin = new BigInteger(rfid, 16).toString(2);
		
		bin = String.format("%96s", bin).replace(' ','0');
	    BigInteger company = new BigInteger(bin.substring(14, 24 + 14), 2);
	    String comCode = String.format("%7s", company.toString()).replace(' ','0');
	    BigInteger product = new BigInteger(bin.substring(38, 20 + 38), 2);
	    String prodCode = String.format("%6s", product.toString()).replace(' ','0');
	    
	    String productCode = comCode + prodCode.substring(1);

	    BigInteger serial = new BigInteger(bin.substring(58, 38 + 58), 2);
	    String serialCode = String.format("%12s", serial.toString()).replace(' ','0');
	    
	    int ch=0;
	    for (int i=0; i < productCode.length(); i++){
	    	if (i%2 == 0){
	    		ch += Integer.parseInt(productCode.substring(i,(i+1)));
	    	}else{
	    		ch += Integer.parseInt(productCode.substring(i,(i+1))) * 3;
	    	}
	    }
	    
	    String finalCode = productCode + (10 - ((ch % 10) == 0 ? 10 : (ch % 10)));
	    
	    reValue.setBarcodeStr(rfid);
	    reValue.setMnfSeq(serial.toString());
	    reValue.setPrdCode(finalCode);
	    
		return reValue;
	}
	
	public static BarcodeInfo getBarcodeInfoByBarcode(byte[] rawData) {
		
		String rawStr = new String(rawData);
		
		BarcodeInfo reValue = new BarcodeInfo();
		
		String cuTcode = "";
		rawStr = "";
		
		for (int i=0; i < rawData.length; i++) {
			if (cuTcode.length() < 2) {
				cuTcode += String.valueOf((char)rawData[i]);
				continue;
			}
			
			if (cuTcode.equals("01")) {
				i = setPrdCode(reValue, rawData,i);
				rawStr += "01"+reValue.getPrdCode();
				cuTcode = "";
			}else if (cuTcode.equals("17")) {
				i = setPrdValidDe(reValue, rawData,i);
				rawStr += "17"+reValue.getPrdValidDe();
				cuTcode = "";
			}else if (cuTcode.equals("10")) {
				i = setMnfNo(reValue, rawData,i);
				rawStr += "10"+reValue.getMnfNo();
				cuTcode = "";
			}else if (cuTcode.equals("21")) {
				i = setMnfSeq(reValue, rawData,i);
				rawStr += "21"+reValue.getMnfSeq();
				cuTcode = "";
			}
		}
		
		reValue.setBarcodeStr(rawStr);
		
		return reValue;
	}

	private static int setMnfSeq(BarcodeInfo barcodeInfo, byte[] rawData, int stIdx) {
		int i = stIdx;
		
		for (;i < rawData.length; i++) {
			if (rawData[i] == 29 || rawData[i] == 32) break;
		}
		
		barcodeInfo.setMnfSeq(new String(rawData, stIdx, i - stIdx));
		return i;
	}

	private static int setMnfNo(BarcodeInfo barcodeInfo, byte[] rawData, int stIdx) {
		
		int i = stIdx;
		
		for (; i < rawData.length - 1; i++) {
			if (rawData[i] == 29 || rawData[i] == 32) break;
		}
		
		barcodeInfo.setMnfNo(new String(rawData, stIdx, i - stIdx));
		return i;
	}

	private static int setPrdValidDe(BarcodeInfo barcodeInfo, byte[] rawData, int stIdx) {
		barcodeInfo.setPrdValidDe(new String(rawData, stIdx, 6));
		return stIdx + 5;
	}

	private static int setPrdCode(BarcodeInfo barcodeInfo, byte[] rawData, int stIdx) {
		barcodeInfo.setPrdCode(new String(rawData, stIdx+1, 13));
		return stIdx + 13;
	}
}