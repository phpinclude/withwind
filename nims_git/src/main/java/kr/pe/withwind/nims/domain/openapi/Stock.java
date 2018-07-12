package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Stock extends Common {
	private ArrayList<StockList> list;

	public ArrayList<StockList> getList() {
		if (list == null) list = new ArrayList<StockList>();
		return list;
	}

	public void setList(ArrayList<StockList> list) {
		this.list = list;
	}
}
