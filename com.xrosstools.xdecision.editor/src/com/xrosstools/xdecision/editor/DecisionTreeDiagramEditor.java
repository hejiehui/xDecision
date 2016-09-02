package com.xrosstools.xdecision.editor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.EventObject;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.xrosstools.common.XmlHelper;
import com.xrosstools.xdecision.editor.actions.DecisionTreeActionConstants;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCodeGenAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateDecisionAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateFactorAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeLayoutAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.DecisionTreeResizeAction;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagramFactory;
import com.xrosstools.xdecision.editor.parts.DecisionTreePartFactory;
import com.xrosstools.xdecision.editor.parts.DecisionTreeTreePartFactory;

public class DecisionTreeDiagramEditor extends GraphicalEditorWithPalette implements DecisionTreeActionConstants, DecisionTreeMessages {

    private DecisionTreeDiagram diagram;
    private PaletteRoot paletteRoot;
    
    private DecisionTreeDiagramFactory diagramFactory = new DecisionTreeDiagramFactory();
    private CommandStackListener commandStackListener = new CommandStackListener() {
        public void commandStackChanged(EventObject event) {
                firePropertyChange(PROP_DIRTY);
        }
    };

    public DecisionTreeDiagramEditor() {
        setEditDomain(new DefaultEditDomain(this));
    }
    
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        getGraphicalViewer().setRootEditPart(new ScalableFreeformRootEditPart());
        getGraphicalViewer().setEditPartFactory(new DecisionTreePartFactory());
        getGraphicalViewer().setContextMenu(new DecisionTreeContextMenuProvider(getGraphicalViewer(), getActionRegistry(), this));
        getCommandStack().addCommandStackListener(commandStackListener);
    }
    
    public RootEditPart getRootEditPart(){
    	return getGraphicalViewer().getRootEditPart();
    }
    
    public DecisionTreeDiagram getModel(){
    	return diagram;
    }

    protected void initializeGraphicalViewer() {
        getGraphicalViewer().setContents(diagram);
    }

    public void doSave(IProgressMonitor monitor) {
		try {
			IFile file = ((IFileEditorInput)getEditorInput()).getFile();
			file.setContents(new ByteArrayInputStream(writeAsXML().getBytes()), 
					true, false, monitor);
			getCommandStack().markSaveLocation();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private String writeAsXML() throws Exception{
    	return XmlHelper.format(diagramFactory.convertToXML(diagram));
    }

    public void doSaveAs() {
    	SaveAsDialog dialog= new SaveAsDialog(getSite().getWorkbenchWindow().getShell());
    	dialog.setOriginalFile(((IFileEditorInput)getEditorInput()).getFile());
    	dialog.open();
    	IPath path= dialog.getResult();
    	
    	if (path == null)
    		return;
    	
    	IWorkspace workspace= ResourcesPlugin.getWorkspace();
    	final IFile file= workspace.getRoot().getFile(path);
    	
    	WorkspaceModifyOperation op= new WorkspaceModifyOperation() {
    		public void execute(final IProgressMonitor monitor) throws CoreException {
    			try {
    				file.create(new ByteArrayInputStream(writeAsXML().getBytes("utf-8")), true, monitor);
    			} 
    			catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	};
    	
    	try {
    		new ProgressMonitorDialog(getSite().getWorkbenchWindow().getShell()).run(false, true, op);
    		setInput(new FileEditorInput((IFile)file));
    		getCommandStack().markSaveLocation();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	} 
    }

    public boolean isDirty() {
        return getCommandStack().isDirty();
    }

    public boolean isSaveAsAllowed() {
        return true;
    }

    protected void setInput(IEditorInput input) {
        super.setInput(input);
    	FileEditorInput fInput = (FileEditorInput)getEditorInput();
    	setPartName(fInput.getName());

    	IFile file = ((IFileEditorInput)input).getFile();
    	try {
    		InputStream is = file.getContents(false);
//    		diagram = diagramFactory.getFromProp(new Properties().load(is));
    		diagram = getFromXML(is);
    		is.close();
    	} catch (Exception e) {
    		//This is just an example.  All exceptions caught here.
    		e.printStackTrace();
    		diagram = diagramFactory.getEmptyDiagram();
    	}
    }
    
    private DecisionTreeDiagram getFromXML(InputStream is) throws Exception {
    	return diagramFactory.getFromXML(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is));
    }

    public Object getAdapter(Class type) {
        if (type == IContentOutlinePage.class)
            return new OutlinePage();
        return super.getAdapter(type);
    }

    protected PaletteRoot getPaletteRoot() {
        if (this.paletteRoot == null) {
            this.paletteRoot = new DecisionTreePaletteFactory().createPalette();
        }
        return this.paletteRoot;
    }

    protected void initializePaletteViewer() {
        super.initializePaletteViewer();
        getPaletteViewer().addDragSourceListener(new TemplateTransferDragSourceListener(getPaletteViewer()));
    }
    
    protected void createActions() {
    	super.createActions();
    	
        getActionRegistry().registerAction(new DecisionTreeLayoutAction(this, ALIGN_BOTTOM, true, 1));
        getActionRegistry().registerAction(new DecisionTreeLayoutAction(this, ALIGN_MIDDLE, true, 0.5f));
        getActionRegistry().registerAction(new DecisionTreeLayoutAction(this, ALIGN_TOP, true, 0));

        getActionRegistry().registerAction(new DecisionTreeLayoutAction(this, ALIGN_LEFT, false, 0));
        getActionRegistry().registerAction(new DecisionTreeLayoutAction(this, ALIGN_CENTER, false, 0.5f));
        getActionRegistry().registerAction(new DecisionTreeLayoutAction(this, ALIGN_RIGHT, false, 1));
        
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, INCREASE_NODE_HEIGHT, true, false, true));
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, DECREASE_NODE_HEIGHT, true, false, false));

        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, INCREASE_NODE_WIDTH, true, true, true));
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, DECREASE_NODE_WIDTH, true, true, false));
        
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, INCREASE_HORIZANTAL_SPACE, false, true, true));
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, DECREASE_HORIZANTAL_SPACE, false, true, false));
        
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, INCREASE_VERTICAL_SPACE, false, false, true));
        getActionRegistry().registerAction(new DecisionTreeResizeAction(this, DECREASE_VERTICAL_SPACE, false, false, false));
        
        getActionRegistry().registerAction(new DecisionTreeCodeGenAction(this, true));
        getActionRegistry().registerAction(new DecisionTreeCodeGenAction(this, false));
        getActionRegistry().registerAction(new DecisionTreeCreateDecisionAction(this));
        getActionRegistry().registerAction(new DecisionTreeCreateFactorAction(this));
    }

    private class OutlinePage extends ContentOutlinePage {
        private Control outline;
        public OutlinePage() {
            super(new TreeViewer());
        }
        public void createControl(Composite parent) {
            outline = getViewer().createControl(parent);
            getSelectionSynchronizer().addViewer(getViewer());
            getViewer().setEditDomain(getEditDomain());
            getViewer().setEditPartFactory(new DecisionTreeTreePartFactory());
            getViewer().setContents(diagram);
        }

        public Control getControl() {
            return outline;
        }

        public void dispose() {
            getSelectionSynchronizer().removeViewer(getViewer());
            super.dispose();
        }
    }
}
