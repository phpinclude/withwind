package kr.pe.withwind.nims.xls.adapter;

import kr.pe.withwind.nims.NimsException;
import kr.pe.withwind.nims.xml.Header;
import kr.pe.withwind.nims.xml.Line;

public interface NimsAdpInf{
	public void getData(Header header, Line line, String param) throws NimsException;
}