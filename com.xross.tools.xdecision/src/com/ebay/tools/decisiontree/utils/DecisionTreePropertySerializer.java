package com.ebay.tools.decisiontree.utils;

import java.util.Properties;
import java.util.StringTokenizer;

public class DecisionTreePropertySerializer{
	
	public static final String COMMENTS = "comments";
	public static final String TOTAL_FACTOR_NUMBER = "total_factor_num";
	public static final String TOTAL_DECISION_NUMBER = "total_decision_num";
	public static final String TOTAL_PATH_NUMBER = "total_path_num";
	
	//The following two compose key "total_factor_n_value_num"
	public static final String TOTAL_FACTOR_ = "total_factor_";
	public static final String _VALUE_NUMBER = "_value_num";
	
	//The following two compose key "factor_n_value_m"
	public static final String FACTOR_ = "factor_";
	public static final String _VALUE_ = "_value_";
	public static final String DECISION_ = "decision_";
	public static final String PATH_ = "path_";
	public static final String _DECISION = "_decision";
	public static final String DELIMITER = "|";
	public static final char FACTOR_VALUE_DELIMITER = ':';

	
	public DecisionTreeModel readMode(Properties p) {
		DecisionTreeModel model = new DecisionTreeModel();
		
		model.setComments(p.getProperty(COMMENTS));
		model.setFactors(createFactors(p));
		model.setPathes(createPathes(p));
		model.setDecisions(createDecisions(p));
		
		return model;
	}

	private DecisionTreeFactor[] createFactors(Properties p) {
		int totalFactorNum = Integer.parseInt(p.getProperty(TOTAL_FACTOR_NUMBER));
		DecisionTreeFactor[] factors = new DecisionTreeFactor[totalFactorNum];
		
		for(int i = 0; i < totalFactorNum; i++){
			DecisionTreeFactor factor = new DecisionTreeFactor();
			factors[i] = factor;
			
			String factorId = FACTOR_ + i;
			factor.setFactorName(p.getProperty(factorId));
			int valueNum = Integer.parseInt(p.getProperty(TOTAL_FACTOR_ + i + _VALUE_NUMBER));
			String[] values = new String[valueNum];
			factor.setFactorValues(values);
			for(int j = 0; j < valueNum; j++){
				values[j] = p.getProperty(FACTOR_ + i + _VALUE_ + j);
			}
		}
		
		return factors;
	}
	
	private DecisionTreePath[] createPathes(Properties p) {
		int totalPathNum = Integer.parseInt(p.getProperty(TOTAL_PATH_NUMBER));
		DecisionTreePath[] pathes = new DecisionTreePath[totalPathNum];
		
		for(int i = 0; i < totalPathNum; i++){
			String pathId = PATH_ + i;
			pathes[i] = getDecisionTreePath(p.getProperty(pathId), p.getProperty(pathId + _DECISION));
		}
		
		return pathes;
	}
	
	/**
	 * @param value looks like 1:2|2:1|..., which means factor 1, value 2
	 * @return
	 */
	private DecisionTreePath getDecisionTreePath(String value, String decisionId){
		StringTokenizer t = new StringTokenizer(value, DELIMITER);
		DecisionTreePathEntry[] entries = new DecisionTreePathEntry[t.countTokens()];
		
		for(int i = 0; i < entries.length; i++){
			String pair = t.nextToken();
			int index = pair.indexOf(FACTOR_VALUE_DELIMITER);
			String factorIndexStr = pair.substring(0, index);
			String valueIndexStr = pair.substring(index+1, pair.length());
			DecisionTreePathEntry entry = new DecisionTreePathEntry(new Integer(factorIndexStr), Integer.parseInt(valueIndexStr));
			entries[i] = entry;
		}

		int decisionIndex = Integer.parseInt(decisionId.replace(DECISION_, ""));
		return new DecisionTreePath(entries, decisionIndex);
	}
	
	private String[] createDecisions(Properties p) {
		int totalDecisionNum = Integer.parseInt(p.getProperty(TOTAL_DECISION_NUMBER));
		String[] decisions = new String[totalDecisionNum];
		
		for(int i = 0; i < totalDecisionNum; i++){
			decisions[i] = p.getProperty(DECISION_ + i);
		}
		
		return decisions;
	}

	public Properties writeModel(DecisionTreeModel model){
		Properties p = new Properties();
			
		p.setProperty(COMMENTS, model.getComments() == null? "" : model.getComments());
		writeFactors(p, model);
		writePathes(p, model);
		writeDecisions(p, model);
		
		return p;
	}
	
	private void writeFactors(Properties p, DecisionTreeModel model){
		DecisionTreeFactor[] factors = model.getFactors();
		p.setProperty(TOTAL_FACTOR_NUMBER, String.valueOf(factors.length));
		
		for(int i = 0; i < factors.length; i++){
			DecisionTreeFactor factor = factors[i];
			String factorId = FACTOR_ + i;
			p.setProperty(factorId, factor.getFactorName());
			int valueNum = factor.getFactorValueNum();
			p.setProperty(TOTAL_FACTOR_ + i + _VALUE_NUMBER, String.valueOf(valueNum));
			String[] values = factor.getFactorValues();
			for(int j = 0; j < valueNum; j++){
				p.setProperty(FACTOR_ + i + _VALUE_ + j, values[j]);
			}
		}
	}
	
	private void writePathes(Properties p, DecisionTreeModel model){
		DecisionTreePath[] pathes = model.getPathes();
		p.setProperty(TOTAL_PATH_NUMBER, String.valueOf(pathes.length));
		
		for(int i = 0; i < pathes.length; i++){
			String pathId = PATH_ + i;
			
			p.setProperty(pathId, buildDecisionTreePath(pathes[i]));
			p.setProperty(pathId + _DECISION, String.valueOf(pathes[i].getDecisionIndex()));
		}
	}
	
	/**
	 * @return looks like 1:2|2:1|..., which means factor 1, value 2
	 */
	private String buildDecisionTreePath(DecisionTreePath path){
		DecisionTreePathEntry[] entries = path.getPathEntries();
		StringBuffer sbf = new StringBuffer();
		
		for(int i = 0; i < entries.length; i++){
			sbf.append(entries[i].getFactorIndex()).append(FACTOR_VALUE_DELIMITER).append(entries[i].getValueIndex());
			if(i < entries.length - 1)
				sbf.append(DELIMITER);	
		}
		
		return sbf.toString();
	}

	private void writeDecisions(Properties p, DecisionTreeModel model){
		String[] decisions = model.getDecisions();
		p.setProperty(TOTAL_DECISION_NUMBER, String.valueOf(decisions.length));
		
		for(int i = 0; i < decisions.length; i++){
			p.setProperty(DECISION_ + i, decisions[i]);
		}
	}
}