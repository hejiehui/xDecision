package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IMenuManager;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.definition.NamedElementContainerTreePart;
import com.xrosstools.xdecision.editor.parts.definition.NamedElementTreePart;

public class DecisionTreeOutlineContextMenuProvider extends ContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private NamedElementContainerContextMenuProvider namedElementContainerProvider;
    private NamedElementContextMenuProvider namedElementContextMenuProvider;

    public DecisionTreeOutlineContextMenuProvider(EditPartViewer viewer, DecisionTreeDiagramEditor editor) {
        super(viewer);
        
        namedElementContextMenuProvider = new NamedElementContextMenuProvider(editor);
        namedElementContainerProvider = new NamedElementContainerContextMenuProvider(editor);
    }

    public void buildContextMenu(IMenuManager menu) {
        List selected = getViewer().getSelectedEditParts();
        
        if(selected.size() == 1) {
            Object model = selected.get(0);
            
            if(model instanceof NamedElementContainerTreePart) {
                namedElementContainerProvider.buildContextMenu(menu, (NamedElementContainerTreePart)model);
            }else if(model instanceof NamedElementTreePart) {
                namedElementContextMenuProvider.buildContextMenu(menu, (NamedElementTreePart)model);
            }
            return;
        }
    }
}
