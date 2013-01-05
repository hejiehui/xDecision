package com.xross.tools.xdecision.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.xross.tools.xdecision.DecisionTree;

public class DecisionTreeFactory {
	public DecisionTree<String> createFromProperties(Properties prop){
		return createFromModel(new DecisionTreePropertySerializer().readMode(prop));
	}
	
	public DecisionTree<String> createFromModel(DecisionTreeModel model){
		DecisionTree<String> tree = new DecisionTree<String>();
		
		DecisionTreePath[] pathes = model.getPathes();
		DecisionTreeFactor[] factors = model.getFactors();
		String[] decisions = model.getDecisions();
		
		for(int i = 0; i < pathes.length; i++){
			DecisionTreePathEntry[] entries = pathes[i].getPathEntries();
			Object[][] newPath = new Object[entries.length][2];
			for(int j = 0; j < entries.length; j++){
				newPath[j][0] = entries[j].getFactorIndex();
				newPath[j][1] = factors[entries[j].getFactorIndex()].getFactorValues()[entries[j].getValueIndex()];
			}
			tree.add(newPath, decisions[pathes[i].getDecisionIndex()]);
		}
		return tree;
	}
	
	public DecisionTree<String> createFromProperties(InputStream in) throws IOException {
		Properties p = new Properties();
		p.load(in);
		return createFromProperties(p);
	}
	
	public DecisionTree<String> createFromXML(InputStream in) throws Exception {
		Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
		DecisionTreeXMLSerializer saver = new DecisionTreeXMLSerializer();
		return createFromModel(saver.readMode(doc));
	}
	
	public DecisionTree<String> createFromProperties(String path) {
		FileInputStream in = null;
		DecisionTree<String> tree = null;
		try{
			in = new FileInputStream(new File(path));
			tree = createFromProperties(in);
			in.close();
		}catch(Throwable e){
			if(in != null)
				try{
					in.close();
				}catch(Throwable e1){
					
				}
			e.printStackTrace();
		}

		return tree;
	}
	
	public DecisionTree<String> createFromXML(String path) {
		FileInputStream in = null;
		DecisionTree<String> tree = null;
		try{
			in = new FileInputStream(new File(path));
			tree = createFromXML(in);
			in.close();
		}catch(Throwable e){
			if(in != null)
				try{
					in.close();
				}catch(Throwable e1){
					
				}
			e.printStackTrace();
		}

		return tree;
	}
}
