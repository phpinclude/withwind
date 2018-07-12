package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Bssh extends Common {
	private ArrayList<BsshList> list;

	public ArrayList<BsshList> getList() {
		if (list == null) list = new ArrayList<BsshList>();
		return list;
	}

	public void setList(ArrayList<BsshList> list) {
		this.list = list;
	}
}