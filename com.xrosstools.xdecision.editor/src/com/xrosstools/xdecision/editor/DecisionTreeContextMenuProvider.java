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
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodePart;
import com.xrosstools.xdecision.editor.parts.OperatorReferencePart;
import com.xrosstools.xdecision.editor.parts.expression.BaseExpressionPart;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
	private ActionRegistry actionRegistry;
	private NodeContextMenuProvider nodeMenuProvider;
	private ConnectionContextMenuProvider connMenuProvider;
	private ExpressionContextMenuProvider expMenuProvider;
	private FactorContextMenuProvider fcatorMenuProvider;
	private UserDefinedTypeContextMenuProvider udfMenuProvider;
	private ConditionOperatorContextMenuProvider conditionMenuProvider;
    public DecisionTreeContextMenuProvider(EditPartViewer viewer, ActionRegistry registry, DecisionTreeDiagramEditor editor) {
        super(viewer);
        actionRegistry = registry;
        nodeMenuProvider = new NodeContextMenuProvider(editor);
        expMenuProvider = new ExpressionContextMenuProvider(editor);
        connMenuProvider = new ConnectionContextMenuProvider(editor);
        fcatorMenuProvider = new FactorContextMenuProvider(editor);
        udfMenuProvider = new UserDefinedTypeContextMenuProvider(editor);
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
        GEFActionConstants.addStandardActionGroups(menu);
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.GEN_JUNIT_TEST_CODE));
    	menu.add(new Separator());
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.CREATE_NEW_DECISION));
    	
    	fcatorMenuProvider.buildContextMenu(menu);
    	menu.add(new Separator());
    	udfMenuProvider.buildContextMenu(menu);
    }
}
