package com.xrosstools.xdecision.editor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagramFactory;
import com.xrosstools.xdecision.editor.model.DecisionTreeModel;
import com.xrosstools.xdecision.editor.model.DecisionTreePath;
import com.xrosstools.xdecision.editor.model.DecisionTreePathEntry;

public class DecisionTreeJunit4TestCodeGen {
	private static final String TEST_ASSIGN = 		"		test.set(\"?\", \"?\");\n";
	private static final String ASSERT_DISPLAY = 	"		assertEquals(\"?\", tree.get(test));\n\n";
	private static final String TEST_RESET = 		"		test = new MapFacts();\n";
	public String generate(DecisionTreeDiagram diagram, String packageName, String testName, String path){
		DecisionTreeModel model = new DecisionTreeDiagramFactory().convert(diagram);
		
		StringBuffer codeBuf = getTemplate();
		replace(codeBuf, "!PACKAGE!", packageName);
		replace(codeBuf, "!TEST_CLASS!", testName);
		replace(codeBuf, "!MODEL_PATH!", path);
		replace(codeBuf, "!TREE_VERIFY!", "\n" + generateVerify(model));
		
		return codeBuf.toString();
	}
	
	private void replace(StringBuffer codeBuf, String replacementMark, String replacement){
		int start = codeBuf.indexOf(replacementMark);
		codeBuf.replace(start, start + replacementMark.length(), replacement);
	}
	
	private String generateVerify(DecisionTreeModel model){
		StringBuffer codeBuf = new StringBuffer();
		
		for(DecisionTreePath path: model.getPathes()){
			StringBuffer testReset = new StringBuffer(TEST_RESET);
			codeBuf.append(testReset);
			for(DecisionTreePathEntry entry: path.getPathEntries()){
				StringBuffer testAssign = new StringBuffer(TEST_ASSIGN);
				replace(testAssign, "?", String.valueOf(model.getFactors()[entry.getFactorIndex()].getFactorName()));
				replace(testAssign, "?", String.valueOf(model.getFactors()[entry.getFactorIndex()].getFactorValues()[entry.getValueIndex()]));
				codeBuf.append(testAssign);
			}
			
			StringBuffer assignExpected = new StringBuffer(ASSERT_DISPLAY);
			replace(assignExpected, "?", model.getDecisions()[path.getDecisionIndex()]);
			codeBuf.append(assignExpected);
		}
		return codeBuf.toString();
	}
	
	private StringBuffer getTemplate(){
		StringBuffer codeBuf = new StringBuffer();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(DecisionTreeJunit4TestCodeGen.class.getResourceAsStream("/templates/Junit4TestTemplate.txt")));
		String line;
		try {
			while((line = reader.readLine()) != null)
				codeBuf.append(line).append('\n');
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return codeBuf;
	}
}
