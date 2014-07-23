package com.testing123.vaadin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockQueryable implements Queryable {
	private Map<String, Double> churn;
	private Map<String,Double> complexity;
	private Map<String, Double> ncloc;
	private Map<String,Double> dComplexity;
	
	public MockQueryable(){
		churn = new HashMap<String, Double>();
		complexity = new HashMap<String, Double>();
		ncloc = new HashMap<String, Double>();
		dComplexity = new HashMap<String, Double>();
		
		churn.put("a", 13.0);
		churn.put("b", 28.0);
		churn.put("c", 32.0);
		churn.put("e", 28.0);
		churn.put("g", 1.0);
		churn.put("t", 8.0);
		
		complexity.put("b", 11.0);
		complexity.put("c", 13.0);
		complexity.put("d", 11.0);
		complexity.put("t", 14.0);
		complexity.put("s", 11.0);
		complexity.put("q", 9989.0);
		complexity.put("p", 54.0);
		complexity.put("o", 7.0);
		complexity.put("i", 11.0);
		complexity.put("n", 28.0);
		
		ncloc.put("a", 1.0);
		ncloc.put("b", 13.0);
		ncloc.put("d", 15.0);
		ncloc.put("p", 11.0);
		ncloc.put("i", 8.0);
		ncloc.put("t", 6.0);
		ncloc.put("w", 1.0);
		
	}

	@Override
	public Map<String, Double> getChurn(ConvertDate startDate, ConvertDate endDate) {
		return churn;
	}

	@Override
	public Map<String, Double> getComplexity(ConvertDate date) {
		return complexity;
	}

	@Override
	public Map<String, Double> getNCLOC(ConvertDate date) {
		return ncloc;
	}

	@Override
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate) {
		return dComplexity;
	}

	@Override
	public Map<String, List<String>> getAuthors(ConvertDate startDate, ConvertDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}



}
