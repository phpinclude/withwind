package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Place extends Common {
	private ArrayList<PlaceList> list;

	public ArrayList<PlaceList> getList() {
		if (list == null) list = new ArrayList<PlaceList>();
		return list;
	}

	public void setList(ArrayList<PlaceList> list) {
		this.list = list;
	}
}
