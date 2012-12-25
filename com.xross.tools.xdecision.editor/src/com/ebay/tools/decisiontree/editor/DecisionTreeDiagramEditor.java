package com.ebay.tools.decisiontree.editor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.RootEditPart;
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
import org.w3c.dom.Document;

import com.ebay.tools.decisiontree.editor.actions.DecisionTreeActionConstants;
import com.ebay.tools.decisiontree.editor.actions.DecisionTreeCodeGenAction;
import com.ebay.tools.decisiontree.editor.actions.DecisionTreeCreateDecisionAction;
import com.ebay.tools.decisiontree.editor.actions.DecisionTreeCreateFactorAction;
import com.ebay.tools.decisiontree.editor.actions.DecisionTreeLayoutAction;
import com.ebay.tools.decisiontree.editor.actions.DecisionTreeMessages;
import com.ebay.tools.decisiontree.editor.actions.DecisionTreeResizeAction;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagramFactory;
import com.ebay.tools.decisiontree.editor.parts.DecisionTreePartFactory;
import com.ebay.tools.decisiontree.editor.parts.DecisionTreeTreePartFactory;

public class DecisionTreeDiagramEditor extends GraphicalEditorWithPalette implements DecisionTreeActionConstants, DecisionTreeMessages {

    private DecisionTreeDiagram diagram;
    private PaletteRoot paletteRoot;
    
    private DecisionTreeDiagramFactory diagramFactory = new DecisionTreeDiagramFactory();
    
    public DecisionTreeDiagramEditor() {
        setEditDomain(new DefaultEditDomain(this));
    }
    
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        getGraphicalViewer().setRootEditPart(new ScalableFreeformRootEditPart());
        getGraphicalViewer().setEditPartFactory(new DecisionTreePartFactory());
        getGraphicalViewer().setContextMenu(new DecisionTreeContextMenuProvider(getGraphicalViewer(), getActionRegistry(), this));
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

    private static final String COMMENTS = "Please use Decision Tree editor to edit this file. Any problem, contact jiehe@ebay.com";
    public void doSave(IProgressMonitor monitor) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			diagramFactory.convertToProp(diagram).store(out, COMMENTS);
			writeAsXML(out);
			IFile file = ((IFileEditorInput)getEditorInput()).getFile();
			file.setContents(new ByteArrayInputStream(out.toByteArray()), 
							true, false, monitor);
			out.close();
			getCommandStack().markSaveLocation();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void writeAsXML(OutputStream out) throws Exception{
    	TransformerFactory tFactory =TransformerFactory.newInstance();
    	Transformer transformer = tFactory.newTransformer();
    	DOMSource source = new DOMSource(diagramFactory.convertToXML(diagram));
    	StreamResult result = new StreamResult(out);
    	transformer.transform(source, result);
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
    				ByteArrayOutputStream out = new ByteArrayOutputStream();
    				diagramFactory.convertToProp(diagram).store(out, COMMENTS);
    				file.create(new ByteArrayInputStream(out.toByteArray()), true, monitor);
    				out.close();
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
    	Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is); 
    	return diagramFactory.getFromXML(doc);
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
