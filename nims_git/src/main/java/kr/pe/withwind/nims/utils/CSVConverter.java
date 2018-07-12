package kr.pe.withwind.nims.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;


public class CSVConverter {
	
	private static final Logger logger = LogManager.getLogger(CSVConverter.class);

	private String classNm = null;
	
	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}
	
	public String setClassNm() {
		return classNm;
	}

	public static void main(String[] args) throws IOException {
		try {
			CSVConverter c = new CSVConverter();
			String value = c.parseCSV("C:\\csv\\TST000287MCM20180509134906_0001.csv");
			logger.debug(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(CSVConverter.class.getSimpleName() + "오류!!", e);
		}
	}

	/**
	 * XML파일 파싱하여 객체로 반환
	 * - XML 파일을 XML Object에 매핑 & XSD Validation 수행(unmarshalling)
	 *  
	 * @param path 파싱대상 파일
	 * @return
	 * @throws IOException 파싱할 파일에 접근 실패 시 발생
	 * @throws EgovBizException XML 파싱 오류 발생 시
	 */
	public void parseXML(Path path) throws IOException {
		try (InputStream source = Files.newInputStream(path)) {
			parseXML(source);
		}
	}

	/**
	 * XML파일 파싱하여 객체로 반환
	 * - XML 파일을 XML Object에 매핑 & XSD Validation 수행(unmarshalling)
	 * 
	 * @param source 파싱대상 Source
	 * @return
	 * @throws IOException 파싱할 Source 처리 실패 시 발생
	 * @throws EgovBizException XML 파싱 오류 발생 시
	 */
	public void parseXML(InputStream source) throws IOException {

		XMLStreamReader reader = null;

		try {

			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
			reader = factory.createXMLStreamReader(source);

			UnmarshallerLocationListener listener = new UnmarshallerLocationListener(reader);

			SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = sf.newSchema(new URL("https://www.nims.or.kr/resources/nims.xsd"));

			logger.debug(classNm);
			JAXBContext jc = JAXBContext.newInstance(Class.forName("kr.pe.withwind.nims.xml." + classNm));
			Unmarshaller unmarshaller = jc.createUnmarshaller();

			unmarshaller.setListener(listener);
			unmarshaller.setSchema(schema);

			unmarshaller.unmarshal(reader);

		} catch (Exception e) {
			// XML 파싱 오류 시, 스키마 오류목록 초기화
			logger.error(this.getClass().getSimpleName() + "오류!!", e);
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (XMLStreamException e) {
			}
		}
	}

	/**
	 * CSV파일 파싱하여 객체로 반환
	 * 
	 * 1. CSV파싱하여 XML Object에 매핑
	 * 2. XML Object 객체를 XML로 변환(marshalling)
	 * 3. XSD Validation 수행(unmarshalling)
	 * 
	 * @param path 대상파일
	 * @return
	 * @throws Exception 
	 * @throws EgovBizException 
	 */
	public String parseCSV(String fileName) throws Exception {
		
		Path path = Paths.get(fileName);
		
		String rptType = path.getFileName().toString().substring(9, 12);
		String className = rptType.charAt(0) + rptType.toLowerCase().substring(1) + "Regist";
		setClassNm(className);
		
		try (InputStream source = Files.newInputStream(path)) {
			return parseCSV(source, path.getFileName().toString());
		}
	}

	/**
	 * 	 * CSV파일 파싱하여 객체로 반환
	 * 
	 * 1. CSV파싱하여 XML Object에 매핑
	 * 2. XML Object 객체를 XML로 변환(marshalling)
	 * 3. XSD Validation 수행(unmarshalling)
	 * 
	 * @param stmSrc 대상 파일 StreamSource
	 * @param fileNm 파일명
	 * @return
	 * @throws Exception 
	 */
	public String parseCSV(InputStream stmSrc, String fileNm) throws Exception {
		
		String reValue = null;

		XMLStreamWriter xsw = null;
		InputStream is = null;
		FileWriter fw = null;

		// CSV to XML
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stmSrc));
				ByteArrayOutputStream os = new ByteArrayOutputStream()) {

			// 첫번째 라인에서 CSV 데이터 정보 추출
			String row = br.readLine().trim();
			String[] token = row.split(",(?=([^\"]|\"[^\"]*\")*$)", -1);

			if (token.length != 5) throw new Exception(fileNm + " 파일의 CVS 첫번째 줄 포맷 오류.\n" + row);

			String val = null, flag = null, pFlag = null;
			boolean isFL = true, isFF = true; // LINES, ATCH_FILES TAG 닫음여부
			int depth = 0;

			// 더블쿼터 제거 및 trim
			removeDoubleQuote(token);

			String uid = token[4].replaceFirst("\\]", "");
			String rptSeCd = token[3];

			// VO에 할당할 필드 정의
			Field field = TaskConst.class.getField("csvHeader" + rptSeCd);
			String[] header = (String[]) field.get(null);

			field = TaskConst.class.getField("csvLine" + rptSeCd);
			String[] line = (String[]) field.get(null);

			xsw = new IndentingXMLStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(os, "UTF-8"));
			xsw.writeStartDocument("UTF-8", "1.0");
			xsw.writeStartElement(rptSeCd.toLowerCase() + "_regist");
			depth++;
			xsw.writeDefaultNamespace("https://www.nims.or.kr/schema/nims");
			xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			xsw.writeStartElement("REPORT_SET");
			depth++;
			xsw.writeStartElement("UID");
			xsw.writeCData(uid);
			xsw.writeEndElement();

			while ((row = br.readLine()) != null) {

				token = row.split(",(?=([^\"]|\"[^\"]*\")*$)", -1);

				if (token == null || token.length <= 0) continue;

				removeDoubleQuote(token);

				// 첫번째 컬럼의 데이터 구분 조회 
				flag = token[0].toUpperCase();

				// 이전 태그가 존재하면 신규태그의 종류에 따라 부모태그 종료처리
				if ( pFlag != null && (flag.equals("F") || flag.equals("H")) ) {
					
					// H 다음에 F는 올수 없으므로 Exception 처리
					if (pFlag.equals("H") && flag.equals("F")) {
						throw new Exception(fileNm + " 파일의 H 다음 줄에 F가 올 수 없습니다. CSV파일 서식을 확인하십시오.");
					}
					
					// close LINES or ATCH_FILES
					if (pFlag.equals("L")) {
						xsw.writeEndElement();
						depth--;
						isFL = true;
					} else if (pFlag.equals("F") && flag.equals("H")) {
						xsw.writeEndElement();
						depth--;
						isFF = true;
					}
					
					// 현재 태그가 HEADER이면 기존 HEADER 닫음
					if (flag.equals("H")) {
						xsw.writeEndElement();
						depth--;
					}
				}

				pFlag = flag;

				if ("H".equals(flag)) {

					if ((token.length - 1) != header.length) {
						throw new Exception(fileNm + " 파일의 입력된 <HEADER>의 컬럼갯수(" + (token.length - 1) + ")가 정의된 갯수("
								+ header.length + "개)와 일치하지 않습니다.");
					}

					xsw.writeStartElement("HEADER");
					depth++;

					for (int i = 1; i < token.length; i++) {
						val = token[i];
						if (val.equals("")) xsw.writeEmptyElement(header[i - 1]);
						else {
							xsw.writeStartElement(header[i - 1]);
							xsw.writeCData(val);
							xsw.writeEndElement();
						}
					}
				} else if ("L".equals(flag)) {

					if ((token.length - 1) != line.length) {
						throw new Exception(fileNm + " 파일의 입력된 <LINE>의 컬럼갯수(" + (token.length - 1) + ")가 정의된 갯수("
								+ line.length + "개)와 일치하지 않습니다.");
					}

					if (isFL) {
						xsw.writeStartElement("LINES");
						depth++;
						isFL = false;
					}
					xsw.writeStartElement("LINE");
					for (int i = 1; i < token.length; i++) {
						val = token[i];
						if (val.equals("")) xsw.writeEmptyElement(line[i - 1]);
						else {
							xsw.writeStartElement(line[i - 1]);
							xsw.writeCData(val);
							xsw.writeEndElement();
						}
					}
					xsw.writeEndElement();
				} else if ("F".equals(flag)) {

					if (token.length != 2) {
						throw new Exception(fileNm + " 파일의 첨부파일 설정이 올바르지 않습니다.");
					}

					if (isFF) {
						xsw.writeStartElement("ATCH_FILES");
						depth++;
						isFF = false;
					}
					xsw.writeStartElement("ATCH_FILE_NM");
					xsw.writeCData(token[1]);
					xsw.writeEndElement();
				} else {
					throw new Exception(fileNm + " 파일에 정의되지 않은 라인구분자[" + flag + "]가 있습니다.");
				}

			} // end of while(row)

			if (depth > 0) {
				while (depth > 0) {
					xsw.writeEndElement();
					depth--;
				}
			}

			xsw.flush();
//			logger.debug(os.toString());

			is = new ByteArrayInputStream(os.toByteArray());
			parseXML(is);

			/*java.io.File xmlFile = new java.io.File("C:/test/target/" + fileNm + ".xml");
			fw = new FileWriter(xmlFile);
			fw.write(new String(os.toByteArray()));
			fw.flush();
			fw.close();*/
			
			reValue = os.toString();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (xsw != null) xsw.close();
				if (is != null) is.close();
				if (fw != null) fw.close();
			} catch (Exception e) {
			}
		}
		
		return reValue;
	}

	/**
	 * String Array의 데이터 중 시작과 끝이 더블쿼터(")인 데이터의 더블쿼터 제거 
	 * @param arr
	 * @param str
	 */
	private void removeDoubleQuote(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			String value = arr[i].trim();
			if (value.startsWith("\"") && value.endsWith("\"")) {
				arr[i] = value.substring(2, value.length() - 1);
			}
		}
	}

	/**
	 * Unmarshalling 시 각각의 Header와 Line에 XML location 정보를 입력
	 * @author su-jong gang
	 *
	 */
	public class UnmarshallerLocationListener extends javax.xml.bind.Unmarshaller.Listener {
		private XMLStreamReader xsr;

		public UnmarshallerLocationListener(XMLStreamReader xsr) {
			this.xsr = xsr;
		}
//
//		@Override
//		public void beforeUnmarshal(Object target, Object parent) {
//			if (target instanceof Header) ((Header) target).startLine = xsr.getLocation().getLineNumber();
//			else if (target instanceof Line) ((Line) target).startLine = xsr.getLocation().getLineNumber();
//			else if (target instanceof Lines) ((Lines) target).startLine = xsr.getLocation().getLineNumber();
//		}
//
//		@Override
//		public void afterUnmarshal(Object target, Object parent) {
//			if (target instanceof Header) ((Header) target).endLine = xsr.getLocation().getLineNumber();
//			else if (target instanceof Line) ((Line) target).endLine = xsr.getLocation().getLineNumber();
//			else if (target instanceof Lines) ((Lines) target).endLine = xsr.getLocation().getLineNumber();
//		}
	}

	/**
	 * Marshalling 시 escape 무시(<[CDATA![]]> tag 처리)
	 * @author su-jong gang
	 *
	 */
	public class TaskCharacterEscapeHandler implements CharacterEscapeHandler {

		@Override
		public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {
			out.write(ch, start, length);
		}
	}
}
