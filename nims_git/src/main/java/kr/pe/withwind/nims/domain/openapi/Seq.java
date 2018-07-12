package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Seq extends Common {
	private ArrayList<SeqList> list;

	public ArrayList<SeqList> getList() {
		if (list == null) list = new ArrayList<SeqList>();
		return list;
	}

	public void setList(ArrayList<SeqList> list) {
		this.list = list;
	}
}
