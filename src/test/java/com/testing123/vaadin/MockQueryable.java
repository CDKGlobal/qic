package com.testing123.vaadin;

import java.util.HashMap;
import java.util.Map;

public class MockQueryable implements Queryable {
	private Map<String, Double>churn;
	private Map<String,Double> complexity;

	@Override
	public Map<String, Double> getChurn(ConvertDate startDate, ConvertDate endDate) {
		return churn;
	}

	@Override
	public Map<String, Double> getComplexity(ConvertDate date) {
		return complexity;
	}
	
	public MockQueryable(){
		churn = new HashMap<String, Double>();
		complexity = new HashMap<String, Double>();
		churn.put("a", 13.0);
		churn.put("b", 28.0);
		complexity.put("b", 11.0);
		complexity.put("c", 3333333.33);
	}

	@Override
	public Map<String, Double> getDeltaLOC(ConvertDate startDate, ConvertDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
