package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Accept extends Common {
	private ArrayList<AcceptList> list;

	public ArrayList<AcceptList> getList() {
		if (list == null) list = new ArrayList<AcceptList>();
		return list;
	}

	public void setList(ArrayList<AcceptList> list) {
		this.list = list;
	}
}