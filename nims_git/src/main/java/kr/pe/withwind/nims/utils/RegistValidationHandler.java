package kr.pe.withwind.nims.utils;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class RegistValidationHandler implements ValidationEventHandler {

	
	private StringBuffer sb = new StringBuffer();
	
	@Override
	public boolean handleEvent(ValidationEvent event) {
//		sb.append("EVENT").append(System.lineSeparator());
//        sb.append("SEVERITY:  " + event.getSeverity()).append(System.lineSeparator());

		sb.append(System.lineSeparator());
        sb.append("LINE NUMBER:  " + event.getLocator().getLineNumber()).append(System.lineSeparator());
        sb.append("COLUMN NUMBER:  " + event.getLocator().getColumnNumber()).append(System.lineSeparator());
        sb.append("MESSAGE:  " + event.getMessage()).append(System.lineSeparator());
        sb.append("===============================").append(System.lineSeparator());

//        sb.append("LINKED EXCEPTION:  " + event.getLinkedException()).append(System.lineSeparator());
//        sb.append("LOCATOR").append(System.lineSeparator());
//        sb.append("LINE NUMBER:  " + event.getLocator().getLineNumber()).append(System.lineSeparator());
//        sb.append("COLUMN NUMBER:  " + event.getLocator().getColumnNumber()).append(System.lineSeparator());
//        sb.append("OFFSET:  " + event.getLocator().getOffset()).append(System.lineSeparator());
//        sb.append("OBJECT:  " + event.getLocator().getObject()).append(System.lineSeparator());
//        sb.append("NODE:  " + event.getLocator().getNode()).append(System.lineSeparator());
//        sb.append("URL:  " + event.getLocator().getURL()).append(System.lineSeparator());
        return true;
	}
	
	public String getErrMsg() {
		String reValue = sb.toString();
		sb = new StringBuffer();
		return reValue;
	}
}
