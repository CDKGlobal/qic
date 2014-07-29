package com.testing123.vaadin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MockQueryable implements Queryable {
	private Map<String, Double> churn;
	private Map<String,Double> complexity;
	private Map<String, Double> ncloc;
	private Map<String,Double> dComplexity;
	private Map<String, List<String>> authors;
	
	public MockQueryable(){
		churn = new HashMap<String, Double>();
		complexity = new HashMap<String, Double>();
		ncloc = new HashMap<String, Double>();
		dComplexity = new HashMap<String, Double>();
		authors = new HashMap<String, List<String>>();
		
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
		
		authors.put("a", new ArrayList<String>(Arrays.asList("Me", "Myself", "I")));
		authors.put("b", new ArrayList<String>(Arrays.asList("Real", "Test", "Here")));
		
	}

	@Override
	public Map<String, Double> getChurn(String s, ConvertDate startDate, ConvertDate endDate, String p) {
		return churn;
	}

	@Override
	public Map<String, Double> getComplexity(ConvertDate date, Set<String> authors, Set<ConvertProject> projects) {
		return complexity;
	}

	@Override
	public Map<String, Double> getNCLOC(ConvertDate date, Set<String> authors, Set<ConvertProject> projects) {
		return ncloc;
	}

	@Override
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate, Set<String> authors, Set<ConvertProject> projects) {
		return dComplexity;
	}

	@Override
	public Map<String, List<String>> getAuthors(String s, ConvertDate startDate, ConvertDate endDate, Set<String> setOAuthorNames, String p) {
		return authors;
	}



}
