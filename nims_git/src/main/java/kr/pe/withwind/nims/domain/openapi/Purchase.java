package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Purchase extends Common {
	private ArrayList<PurchaseList> list;

	public ArrayList<PurchaseList> getList() {
		if (list == null) list = new ArrayList<PurchaseList>();
		return list;
	}

	public void setList(ArrayList<PurchaseList> list) {
		this.list = list;
	}
}
