package com.testing123.dataObjects;

public class FooterData {
	private int total;
	private int negative;
	private int positive;
	
	public FooterData() {
		this.total = 0;
		this.negative = 0;
		this.positive = 0;
	}
	public int getTotal() {
		return total;
	}
	public int getNegative() {
		return negative;
	}
	public int getPositive() {
		return positive;
	}
	
	public void addPositive(int i){
		positive += i;
		total += i;
	}
	public void addNegative(int i){
		negative += i;;
		total += i;
	}
	public void addTotal(int i){
		total +=i;
	}
	
}
