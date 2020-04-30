package com.xrosstools.xdecision.idea.editor.actions;

import com.xrosstools.gef.Command;
import com.xrosstools.gef.Action;

public class DecisionTreeCodeGenAction extends Action implements DecisionTreeActionConstants, DecisionTreeMessages{
	private boolean useJunitTest;
	private DecisionTreeJunit4TestCodeGen junit4CodeGen = new DecisionTreeJunit4TestCodeGen();
	public DecisionTreeCodeGenAction(boolean useJunitTest){
		this.useJunitTest = useJunitTest;
	}

	@Override
	public Command createCommand() {
		return null;
	}

	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
//		DecisionTreeDiagramEditor editor = (DecisionTreeDiagramEditor)getWorkbenchPart();
//		IFile file = ((IFileEditorInput)editor.getEditorInput()).getFile();
//
//		String packageName, testName, path;
//		packageName = "com.ebay.xdomain.xcomponent";
//		testName = file.getName().substring(0, file.getName().indexOf(".xdecision")) + "Test";
//
//		path = file.getName();
////		file.getProjectRelativePath()
//		DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
//		String out = junit4CodeGen.generate(diagram, packageName, testName, path);
//		print(out);
	}
	
	private static final String CONSOLE_NAME = "Decision tree editor[Xross tools]";
	
	public void print(String text){
//		try {
//			ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
//			IConsoleManager manager = consolePlugin.getConsoleManager();
//			MessageConsole console = null;
//			for (IConsole c : manager.getConsoles()) {
//				if (c.getName().equals(CONSOLE_NAME)) {
//					console = (MessageConsole) c;
//					break;
//				}
//			}
//			if (console == null) {
//				console = new MessageConsole(CONSOLE_NAME, null);
//				manager.addConsoles(new IConsole[] { console });
//				console.activate();
//			}
//
//			MessageConsoleStream stream = console.newMessageStream();
//			stream.setColor(new Color(null, 0x33, 0x99, 0xcc));
//			stream.println(text);
//			stream.flush();
//			stream.close();
//			manager.refresh(stream.getConsole());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			//PluginLogHelper.log(Activator.getDefault(), Activator.PLUGIN_ID, IStatus.WARNING, "Unable to initialize console: " + e.getMessage(), e);
//		}

	}
}
