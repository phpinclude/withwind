package kr.pe.withwind.nims.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

import kr.pe.withwind.nims.NimsException;


public class MakeXml {
	
	private static MakeXml makeXml;
	private HashMap<String, Marshaller> marshallerMap;
	private HashMap<String, Unmarshaller> unmarshallerMap;
	
	private MakeXml(){
		marshallerMap = new HashMap<String, Marshaller>();
		unmarshallerMap = new HashMap<String, Unmarshaller>();
	}
	
	public static MakeXml getInstance(){
		if (makeXml == null) makeXml = new MakeXml();
		return makeXml;
	}
	
	private Marshaller getMarshller(Object obj) throws JAXBException{
		if (marshallerMap.containsKey(obj.getClass().getSimpleName())){
			return marshallerMap.get(obj.getClass().getSimpleName());
		}
		
		
		JAXBContext jcontext = JAXBContext.newInstance(obj.getClass());
		
		Marshaller m = jcontext.createMarshaller();
		
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "https://www.nims.or.kr/schema/nims https://www.nims.or.kr/resources/nims.xsd");
		
		
		
		m.setProperty("com.sun.xml.bind.characterEscapeHandler",
				new CharacterEscapeHandler() {
					@Override
					public void escape(char[] ch, int start, int length,
							boolean isAttVal, Writer writer)
							throws IOException {
						writer.write(ch, start, length);
					}
				});
		
		marshallerMap.put(obj.getClass().getSimpleName(),m);
		
		return m;
	}
	
	private <T> Unmarshaller getUnMarshller(Class<T> type) throws JAXBException, MalformedURLException, SAXException{
		if (unmarshallerMap.containsKey(type.getSimpleName())){
			return unmarshallerMap.get(type.getSimpleName());
		}
		
		JAXBContext jcontext = JAXBContext.newInstance(type);
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		Source aa = new StreamSource(ClassLoader.getSystemResourceAsStream("nims.xsd"));
		Schema schema = sf.newSchema(aa);

		Unmarshaller um = jcontext.createUnmarshaller();
		um.setSchema(schema);
		um.setEventHandler(new RegistValidationHandler());
		
		unmarshallerMap.put(type.getSimpleName(),um);
		
		return um;
	}
	
	public String getObj2Xml(Object obj) throws JAXBException, IOException{
		
		Marshaller m = getMarshller(obj);
		
	    StringWriter sw = new StringWriter();
	    sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    sw.append(System.lineSeparator());
		
		m.marshal(obj, sw);
		sw.close();
		
		return sw.toString();
	}

	@SuppressWarnings("unchecked")
	public <T> T getXml2Obj(InputStream is, Class<T> type) throws MalformedURLException, JAXBException, SAXException, NimsException {

		Unmarshaller un = getUnMarshller(type);
		Object reValue = un.unmarshal(is);
		if (un.getEventHandler() instanceof RegistValidationHandler) {
			RegistValidationHandler rvh = (RegistValidationHandler) un.getEventHandler();
			
			String errMsg = rvh.getErrMsg();
			
			if (!StringUtils.isEmpty(errMsg)) {
				throw new NimsException(errMsg);
			}
		}
		
		return (T)reValue;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getXml2Obj(Reader reader, Class<T> type) throws MalformedURLException, JAXBException, SAXException, NimsException {
		
		Unmarshaller un = getUnMarshller(type);
		Object reValue = un.unmarshal(reader);
		if (un.getEventHandler() instanceof RegistValidationHandler) {
			RegistValidationHandler rvh = (RegistValidationHandler) un.getEventHandler();
			
			String errMsg = rvh.getErrMsg();
			
			if (!StringUtils.isEmpty(errMsg)) {
				throw new NimsException(errMsg);
			}
		}
		
		return (T)reValue;
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getXml2ObjNoCheck(Reader reader, Class<T> type) throws MalformedURLException, JAXBException, SAXException, NimsException {
		
		Unmarshaller un = getUnMarshller(type);
		Object reValue = un.unmarshal(reader);
		
		return (T)reValue;
		
	}
	
}