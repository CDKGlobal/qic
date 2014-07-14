package com.testing123.vaadin;

public class ItemData {
	private Object[] item;

	public Object getItem(int index) {
		if(index < item.length)
			return item[index];
		return null;
	}

	public void setItem(Object[] item) {
		this.item = item;
	}
	
}
