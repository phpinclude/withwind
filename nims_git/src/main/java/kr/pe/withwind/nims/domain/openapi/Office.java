package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Office extends Common {
	private ArrayList<OfficeList> list;

	public ArrayList<OfficeList> getList() {
		if (list == null) list = new ArrayList<OfficeList>();
		return list;
	}

	public void setList(ArrayList<OfficeList> list) {
		this.list = list;
	}
}