package kr.pe.withwind.nims.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.StringUtils;

public class CDATAAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String v) throws Exception {
		if (StringUtils.isEmpty(v)) {
			return v;
		}else {
			return "<![CDATA[" + v + "]]>";
		}
	}

	@Override
	public String unmarshal(String v) throws Exception {
		return v;
	}
}