package com.ebay.tools.decisiontree.editor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagramFactory;
import com.ebay.tools.decisiontree.utils.DecisionTreeModel;
import com.ebay.tools.decisiontree.utils.DecisionTreePath;
import com.ebay.tools.decisiontree.utils.DecisionTreePathEntry;

public class DecisionTreeTestCodeGen {
	private static final String TEST_ASSIGN = 		"		test[?] = \"?\";\n";
	private static final String ASSERT_DISPLAY = 	"		assertEquals(test, \"?\", tree.get(test));\n";	
	public String generate(DecisionTreeDiagram diagram, String packageName, String testName, String path){
		DecisionTreeModel model = new DecisionTreeDiagramFactory().convert(diagram);
		
		StringBuffer codeBuf = getTemplate();
		replace(codeBuf, "!PACKAGE!", packageName);
		replace(codeBuf, "!TEST_CLASS!", testName);
		
		replace(codeBuf, "!MODEL_PATH!", path);
		replace(codeBuf, "!FACTOR_COUNT!", String.valueOf(model.getFactors().length));
		
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
			for(DecisionTreePathEntry entry: path.getPathEntries()){
				StringBuffer testAssign = new StringBuffer(TEST_ASSIGN);
				replace(testAssign, "?", String.valueOf(entry.getFactorIndex()));
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
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(DecisionTreeTestCodeGen.class.getResourceAsStream("/templates/TestTemplate.txt")));
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
