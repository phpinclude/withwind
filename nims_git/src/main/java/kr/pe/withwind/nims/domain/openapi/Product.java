package kr.pe.withwind.nims.domain.openapi;

import java.util.ArrayList;

public class Product extends Common{
	
	private ArrayList<ProductList> list;

	public ArrayList<ProductList> getList() {
		if (list == null) list = new ArrayList<ProductList>();
		return list;
	}

	public void setList(ArrayList<ProductList> list) {
		this.list = list;
	}
}
