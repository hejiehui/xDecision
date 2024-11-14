package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class DecisionTreeJunit4TestCodeGen {
	private static final String METHOD_BODY =
			"\n" +
					"    @Test\n" +
					"    public void test_%d(){\n" +   //Num of test
					"        /*\n" +
					"            %s\n\n" +//decision
                    "%s" +              //evaluation path
					"        */\n" +
					"%s\n" +           // factor assignment and assertion
					"    }\n";

	private static final String COMMENTS =       "            %s\n";
	private static final String TEST_ASSIGN = 		"        test.set(\"%s\", null);\n";
	private static final String ASSERT_DISPLAY = 	"        assertEquals(\"%s\", tree.get(test));";
	private static final String TEST_RESET = 		"        MapFacts test = new MapFacts();\n";
	public String generate(Project project, VirtualFile file, DecisionTreeDiagram diagram, String packageName, String testName){
		StringBuffer codeBuf = getTemplate("/templates/Junit4TestTemplate.txt", this.getClass());
		replace(codeBuf, "!PACKAGE!", packageName);
		replace(codeBuf, "!TEST_CLASS!", testName);
		replace(codeBuf, "!MODEL_PATH!", findResourcesPath(project, file));
		replace(codeBuf, "!TREE_VERIFY!", "\n" + generateVerify(diagram));
		
		return codeBuf.toString();
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

				StringBuilder commnets = new StringBuilder(parent.getNodeExpression().toString()).append(" ")
						.append(node.getInput().getOperator().getText()).append(" ");

				if(node.getInput().getExpression() != null)
                    commnets.append(node.getInput().getExpression());

				commentsBuf.insert(0, String.format(COMMENTS, commnets.toString()));

				node = parent;
			}

			//get all factors
			for(DecisionTreeFactor factor: diagram.getFactorList()) {
				codeBuf.append(String.format(TEST_ASSIGN, factor.getFactorName()));
			}
			codeBuf.append(String.format(ASSERT_DISPLAY, decision));
			testCasesCode.append(String.format(METHOD_BODY, index++, decision, commentsBuf.toString(), codeBuf.toString()));
		}
		return testCasesCode.toString();
	}
}
