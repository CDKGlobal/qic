package com.testing123.dataObjects;

public class ItemData {
	private Object[] item;

	public Object getItem(int index) {
		if(index < item.length)
			return item[index];
		return null;
	}

	public void setItem(Object[] item) {
		this.item = new Object[item.length];
		for(int i = 0; i < item.length; i++){
			this.item[i]=item[i];
		}
	}
	
}
