package kr.pe.withwind.nims.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.xls.ExlMake;
import kr.pe.withwind.nims.xls.ExlRead;
import kr.pe.withwind.nims.xml.EtiRegist;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.PcmRegist;
import kr.pe.withwind.nims.xml.ReportSet;
import kr.pe.withwind.nims.xml.SlmRegist;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class XmlTestMain {
	
	private static final Logger logger = LogManager.getLogger(XmlTestMain.class);
	
	public File getXMLFile(String xlsFileName, String XMLFileName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalAccessException, InvocationTargetException, JAXBException, InstantiationException, NimsException{
		long stTime = System.currentTimeMillis();
		logger.debug("엑셀 to xml 변환 시작");
		FileInputStream fis = new FileInputStream(xlsFileName);
		ExlRead er = new ExlRead();
		SlmRegist slmRegist = (SlmRegist) er.getExl2Obj(fis,SlmRegist.class);
		
		String xmlStr1 = MakeXml.getInstance().getObj2Xml(slmRegist);

		File sendFile = new File(XMLFileName);
		FileOutputStream fos = new FileOutputStream(sendFile);
		
		fos.write(xmlStr1.getBytes());
		fos.flush();
		fos.close();
		logger.debug("엑셀 to xml 변환 끝");
		logger.debug("소요시간 : " + (System.currentTimeMillis() - stTime));
		
		return sendFile;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T xml2Obj(String xlsFileName, Class<T> cls) throws FileNotFoundException, MalformedURLException, JAXBException, SAXException, NimsException{
		long stTime = System.currentTimeMillis();
		File sendFile = new File(xlsFileName);
		
		logger.debug("xml to object 변환 시작");
		FileInputStream fis2 = new FileInputStream(sendFile);
		Object reObj = MakeXml.getInstance().getXml2Obj(fis2, cls);
		logger.debug("xml to object 변환 끝");
		logger.debug("소요시간 : " + (System.currentTimeMillis() - stTime));
		
		return (T)reObj;
		
	}
	
	public String sendFile(String authKey, File xmlFile) throws IOException{
		long stTime = System.currentTimeMillis();
		logger.debug("파일 전송 시작");
		
		MultiPartUpload mpu = new MultiPartUpload(authKey);
		String sendResult = mpu.sendFile(xmlFile);
		
		logger.debug("파일 전송 끝");
		logger.debug("소요시간 : " + (System.currentTimeMillis() - stTime));
		
		return sendResult;
	}

	public static void main(String[] arg) throws JAXBException, XMLStreamException, IOException, SAXException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, NimsException{
		
		
//		Header test = new Header();
//		
//		try {
//
//			Class c = Class.forName("kr.co.jw.nims.xml.Header");
//
//			Method m[] = c.getDeclaredMethods();
//
//			for (int i = 0; i < m.length; i++)
//
//				logger.debug(m[i].getName());
//
//		} catch (Throwable e) {
//
//			System.err.println(e);
//
//		}
//
//		ExlMake em = new ExlMake();
//		logger.debug(em.doMake(APIConstants.REPORT_TYPE_SLM, "xls_file"));

		
		XmlTestMain mainClass = new XmlTestMain();
		
//		File xmlFile = mainClass.getXMLFile("C:/마약류관련문서/판매보고테스트.xlsx", "c:/sendTemp/TSTNIMSW1SLM20171220190047_0100.xml");
		
		PcmRegist sr = mainClass.xml2Obj("C:\\새 폴더\\TST000139PCM20180417175955_4196.xml",PcmRegist.class);
		logger.debug("라인수 : " + sr.getREPORTSET().getHEADER().get(0).getLINES().getLINE().size());

//		String sendResult = mainClass.sendFile("53d60c5e9233508f59a371c05f9098636c2a5c3f5d69e190c81ce483cb4bd51d", xmlFile);
//		logger.debug(sendResult);
	}
}
