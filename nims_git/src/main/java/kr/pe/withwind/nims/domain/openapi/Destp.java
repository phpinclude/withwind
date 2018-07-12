package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Destp extends Common {
	private ArrayList<DestpList> list;

	public ArrayList<DestpList> getList() {
		if (list == null) list = new ArrayList<DestpList>();
		return list;
	}

	public void setList(ArrayList<DestpList> list) {
		this.list = list;
	}
}
