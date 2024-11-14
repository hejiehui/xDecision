package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.actions.CodeGenHelper;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

import java.awt.event.ActionEvent;

public class DecisionTreeCodeGenAction extends Action implements DecisionTreeActionConstants, DecisionTreeMessages{
    private Project project;
    private VirtualFile file;
	private DecisionTreeDiagram diagram;
	private DecisionTreeJunit4TestCodeGen junit4CodeGen = new DecisionTreeJunit4TestCodeGen();
	private boolean inConsole;
	public DecisionTreeCodeGenAction(Project project, VirtualFile file, DecisionTreeDiagram diagram, boolean inConsole){
		setText(GEN_TEST_CODE_MSG);
        this.project = project;
		this.diagram = diagram;
		this.file = file;
		this.inConsole = inConsole;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        String packageName, testName, path;
        packageName = "test.xrosstools.decision";

        testName = file.getName().substring(0, file.getName().indexOf(".xdecision"));
        testName =  CodeGenHelper.fileToClassName(testName) + "Test";

        String generatedTest = junit4CodeGen.generate(project, file, diagram, packageName, testName);
        if (inConsole) {
            System.out.println("Generated Unit Test Source:");
            System.out.println(generatedTest);
        } else
            new CodeDisplayer("Generated helper", generatedTest).show();
	}

    @Override
    public Command createCommand() {
        return null;
    }
}
