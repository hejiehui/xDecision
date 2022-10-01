package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import java.awt.event.ActionEvent;

public class DecisionTreeCodeGenAction extends Action implements DecisionTreeActionConstants, DecisionTreeMessages{
    private VirtualFile file;
	private DecisionTreeDiagram diagram;
	private DecisionTreeJunit4TestCodeGen junit4CodeGen = new DecisionTreeJunit4TestCodeGen();
	private boolean inConsole;
	public DecisionTreeCodeGenAction(VirtualFile file, DecisionTreeDiagram diagram, boolean inConsole){
		setText(GEN_TEST_CODE_MSG);
		this.diagram = diagram;
		this.file = file;
		this.inConsole = inConsole;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        String packageName, testName, path;
        packageName = "test.xrosstools.decision";
        testName = file.getName().substring(0, file.getName().indexOf(".xdecision")) + "Test";

        String out = junit4CodeGen.generate(diagram, packageName, testName, file.getPath());
        if (inConsole) {
            System.out.println("Generated Unit Test Source:");
            System.out.println(out);
        } else
            Messages.showInfoMessage(out, "Generated Unit Test Source");
	}

    @Override
    public Command createCommand() {
        return null;
    }
}
