package com.xrosstools.xdecision.editor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeJunit4TestCodeGen {
    private static final String METHOD_BODY = 
                                                "\n" + 
                                                "    @Test\n" + 
                                                "    public void test_%d(){\n" + 
                                                "        /*\n" +
                                                "          Decision paths to %s:\n" +
                                                "%s" + 
                                                "        */\n" + 
                                                "%s\n" + 
                                                "    }\n"; 
    
    private static final String COMMENTS =       "          %s,\n";
    private static final String TEST_RESET =       "        test = new MapFacts();\n";
	private static final String TEST_ASSIGN = 		"        test.set(\"%s\", \"%s\");\n";
	private static final String ASSERT_DISPLAY = 	"        assertEquals(\"%s\", tree.get(test));";
	
	
	public String generate(DecisionTreeDiagram diagram, String packageName, String testName, String path){
		StringBuilder codeBuf = getTemplate();
		replace(codeBuf, "!PACKAGE!", packageName);
		replace(codeBuf, "!TEST_CLASS!", testName);
		replace(codeBuf, "!MODEL_PATH!", path);
		replace(codeBuf, "!TREE_VERIFY!", "\n" + generateVerify(diagram));
		
		return codeBuf.toString();
	}
	
	private void replace(StringBuilder codeBuf, String replacementMark, String replacement){
		int start = codeBuf.indexOf(replacementMark);
		codeBuf.replace(start, start + replacementMark.length(), replacement);
	}
	
	private String generateVerify(DecisionTreeDiagram diagram){
		StringBuilder testCasesCode = new StringBuilder();
		
		int index = 0;
		for(DecisionTreeNode node: diagram.getNodes()){
		    if(node.getDecision() == null)
		        continue;

            StringBuilder commentsBuf = new StringBuilder();
            StringBuilder codeBuf = new StringBuilder(TEST_RESET);
            String decision = node.getDecision().getName();

            //get all decision paths
            while(node.getInput() != null) {
                DecisionTreeNode parent = node.getInput().getParent();
                
                StringBuilder commnets = new StringBuilder(parent.getNodeExpression().toString())
                        .append(node.getInput().getOperator().getText())
                        .append(node.getInput().getExpression());
                commentsBuf.insert(0, String.format(COMMENTS, commnets.toString()));

		        node = parent;
		    }

            //get all factors
            for(DecisionTreeFactor factor: diagram.getFactorList()) {
                codeBuf.append(String.format(TEST_ASSIGN, factor.getFactorName(), ""));
            }
            codeBuf.append(String.format(ASSERT_DISPLAY, decision));
            testCasesCode.append(String.format(METHOD_BODY, index++, decision, commentsBuf.toString(), codeBuf.toString()));
		}		
		return testCasesCode.toString();
	}
	
	private StringBuilder getTemplate(){
		StringBuilder codeBuf = new StringBuilder();
		
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
