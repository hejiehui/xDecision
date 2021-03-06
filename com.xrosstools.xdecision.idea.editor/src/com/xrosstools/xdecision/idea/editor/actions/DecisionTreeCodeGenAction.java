package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.gef.Command;
import com.xrosstools.gef.Action;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.apache.batik.svggen.SVGStylingAttributes.set;

public class DecisionTreeCodeGenAction extends Action implements DecisionTreeActionConstants, DecisionTreeMessages{
    private VirtualFile file;
	private DecisionTreeDiagram diagram;
	private DecisionTreeJunit4TestCodeGen junit4CodeGen = new DecisionTreeJunit4TestCodeGen();
	public DecisionTreeCodeGenAction(VirtualFile file, DecisionTreeDiagram diagram){
		setText(GEN_JUNIT_TEST_CODE_MSG);
		this.diagram = diagram;
		this.file = file;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
		String packageName, testName, path;
		packageName = "test.xrosstools.decision";
		testName = file.getName().substring(0, file.getName().indexOf(".xdecision")) + "Test";

		String out = junit4CodeGen.generate(diagram, packageName, testName, file.getPath());
        Messages.showInfoMessage(out, "Generated Unit Test Source");
	}

    @Override
    public Command createCommand() {
        return null;
    }
}
