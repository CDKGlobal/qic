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
		return negative * -1;
	}
	public int getPositive() {
		return positive;
	}
	
	public void addPositive(double d){
		positive += d;
		total += d;
	}
	public void addNegative(double d){
		negative += d;;
		total += d;
	}
	public void addTotal(int i){
		total +=i;
	}
	
}
