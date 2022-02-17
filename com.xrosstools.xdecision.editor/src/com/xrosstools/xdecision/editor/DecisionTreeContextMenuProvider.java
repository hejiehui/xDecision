package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.DecisionTreeActionConstants;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodePart;
import com.xrosstools.xdecision.editor.parts.OperatorReferencePart;
import com.xrosstools.xdecision.editor.parts.expression.BaseExpressionPart;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private DecisionTreeDiagram diagram;
	private ActionRegistry actionRegistry;
	private NodeContextMenuProvider nodeMenuProvider;
	private ConnectionContextMenuProvider connMenuProvider;
	private ExpressionContextMenuProvider expMenuProvider;
	
	private NamedElementContainerContextMenuProvider elementContainerMenuProvider;
	
	
	private ConditionOperatorContextMenuProvider conditionMenuProvider;
    public DecisionTreeContextMenuProvider(EditPartViewer viewer, ActionRegistry registry, DecisionTreeDiagramEditor editor) {
        super(viewer);
        actionRegistry = registry;
        diagram = editor.getModel();
        nodeMenuProvider = new NodeContextMenuProvider(editor);
        expMenuProvider = new ExpressionContextMenuProvider(editor);
        connMenuProvider = new ConnectionContextMenuProvider(editor);
        
        elementContainerMenuProvider = new NamedElementContainerContextMenuProvider(editor);
        conditionMenuProvider = new ConditionOperatorContextMenuProvider(editor);
    }

    public void buildContextMenu(IMenuManager menu) {
        List selected = getViewer().getSelectedEditParts();
        
        if(selected.size() == 1) {
            Object model = selected.get(0);
            if(model instanceof DecisionTreeNodeConnectionPart) {
                connMenuProvider.buildContextMenu(menu, (DecisionTreeNodeConnectionPart)model);
            }else if(model instanceof DecisionTreeNodePart) {
                nodeMenuProvider.buildContextMenu(menu, (DecisionTreeNodePart)model);
            }else if(model instanceof BaseExpressionPart ) {
                expMenuProvider.buildContextMenu(menu, (BaseExpressionPart)model);
            }else if(model instanceof OperatorReferencePart ) {
                conditionMenuProvider.buildContextMenu(menu, (OperatorReferencePart)model);
            }
            
            return;
        }
        
        // Add standard action groups to the menu
        GEFActionConstants.addStandardActionGroups(menu);//?
    	menu.add(new Separator());
    	
    	elementContainerMenuProvider.buildContextMenu(menu, diagram.getDecisions());
    	elementContainerMenuProvider.buildContextMenu(menu, diagram.getFactors());
    	elementContainerMenuProvider.buildContextMenu(menu, diagram.getUserDefinedConstants());
    	menu.add(new Separator());

    	elementContainerMenuProvider.buildContextMenu(menu, diagram.getUserDefinedTypes());
        menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.GEN_JUNIT_TEST_CODE));
    }
}
