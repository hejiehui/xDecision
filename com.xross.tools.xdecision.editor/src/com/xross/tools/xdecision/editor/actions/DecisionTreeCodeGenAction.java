package com.xross.tools.xdecision.editor.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.xross.tools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.util.DecisionTreeJunit4TestCodeGen;
import com.xross.tools.xdecision.editor.util.DecisionTreeJunitTestCodeGen;
import com.xross.tools.xdecision.editor.util.DecisionTreeTestCodeGen;

public class DecisionTreeCodeGenAction extends WorkbenchPartAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private boolean useJunitTest;
	private DecisionTreeTestCodeGen codeGen = new DecisionTreeTestCodeGen();
	private DecisionTreeJunitTestCodeGen junitCodeGen = new DecisionTreeJunitTestCodeGen();
	private DecisionTreeJunit4TestCodeGen junit4CodeGen = new DecisionTreeJunit4TestCodeGen();
	public DecisionTreeCodeGenAction(IWorkbenchPart part, boolean useJunitTest){
		super(part);
		if(useJunitTest){
			setId(ID_PREFIX + GEN_JUNIT_TEST_CODE);
			setText(GEN_JUNIT_TEST_CODE_MSG);
		}else{
			setId(ID_PREFIX + GEN_TEST_CODE);
			setText(GEN_TEST_CODE_MSG);
		}
			
		this.useJunitTest = useJunitTest;
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
		IFile file = ((IFileEditorInput)editor.getEditorInput()).getFile();

		String packageName, testName, path;
		packageName = "com.ebay.xdomain.xcomponent";
		testName = file.getName().substring(0, file.getName().indexOf(".xdecision")) + "Test";
		
		path = file.getName();
//		file.getProjectRelativePath()
		DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
		String out = useJunitTest?junit4CodeGen.generate(diagram, packageName, testName, path):codeGen.generate(diagram, packageName, testName, path);
		print(out);
	}
	
	private static final String CONSOLE_NAME = "Decision tree editor[Xross tools]";
	
	public void print(String text){
		try {
			ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
			IConsoleManager manager = consolePlugin.getConsoleManager();
			MessageConsole console = null;
			for (IConsole c : manager.getConsoles()) {
				if (c.getName().equals(CONSOLE_NAME)) {
					console = (MessageConsole) c;
					break;
				}
			}
			if (console == null) {
				console = new MessageConsole(CONSOLE_NAME, null);
				manager.addConsoles(new IConsole[] { console });
				console.activate();
			}
			
			MessageConsoleStream stream = console.newMessageStream();
			stream.setColor(new Color(null, 0x33, 0x99, 0xcc));
			stream.println(text);
			stream.flush();
			stream.close();
			manager.refresh(stream.getConsole());

		} catch (Exception e) {
			e.printStackTrace();
			//PluginLogHelper.log(Activator.getDefault(), Activator.PLUGIN_ID, IStatus.WARNING, "Unable to initialize console: " + e.getMessage(), e);
		}

	}
}
