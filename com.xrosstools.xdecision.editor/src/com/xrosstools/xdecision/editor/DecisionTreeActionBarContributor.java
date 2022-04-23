package com.xrosstools.xdecision.editor;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.LabelRetargetAction;

import com.xrosstools.xdecision.editor.actions.DecisionTreeActionConstants;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;

public class DecisionTreeActionBarContributor extends ActionBarContributor
        implements DecisionTreeActionConstants, DecisionTreeMessages {
    protected void buildActions() {
        addRetargetAction(new UndoRetargetAction());
        addRetargetAction(new RedoRetargetAction());
        addRetargetAction(new DeleteRetargetAction());

        addRetargetAction(getAction(ALIGN_BOTTOM, ALIGN_BOTTOM_MSG));
        addRetargetAction(getAction(ALIGN_MIDDLE, ALIGN_MIDDLE_MSG));
        addRetargetAction(getAction(ALIGN_TOP, ALIGN_TOP_MSG));

        addRetargetAction(getAction(ALIGN_LEFT, ALIGN_LEFT_MSG));
        addRetargetAction(getAction(ALIGN_CENTER, ALIGN_CENTER_MSG));
        addRetargetAction(getAction(ALIGN_RIGHT, ALIGN_RIGHT_MSG));

        addRetargetAction(getAction(INCREASE_NODE_HEIGHT, INCREASE_NODE_HEIGHT_MSG));
        addRetargetAction(getAction(DECREASE_NODE_HEIGHT, DECREASE_NODE_HEIGHT_MSG));

        addRetargetAction(getAction(INCREASE_NODE_WIDTH, INCREASE_NODE_WIDTH_MSG));
        addRetargetAction(getAction(DECREASE_NODE_WIDTH, DECREASE_NODE_WIDTH_MSG));

        addRetargetAction(getAction(INCREASE_HORIZANTAL_SPACE, INCREASE_HORIZANTAL_SPACE_MSG));
        addRetargetAction(getAction(DECREASE_HORIZANTAL_SPACE, DECREASE_HORIZANTAL_SPACE_MSG));

        addRetargetAction(getAction(INCREASE_VERTICAL_SPACE, INCREASE_VERTICAL_SPACE_MSG));
        addRetargetAction(getAction(DECREASE_VERTICAL_SPACE, DECREASE_VERTICAL_SPACE_MSG));
    }

    private LabelRetargetAction getAction(String id, String text) {
        LabelRetargetAction action = new LabelRetargetAction(getId(id), text);
        action.setImageDescriptor(Activator.getDefault().getImageRegistry().getDescriptor(id));
        action.setEnabled(true);
        return action;
    }

    public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));

        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(getId(ALIGN_LEFT)));
        toolBarManager.add(getAction(getId(ALIGN_CENTER)));
        toolBarManager.add(getAction(getId(ALIGN_RIGHT)));

        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(getId(ALIGN_TOP)));
        toolBarManager.add(getAction(getId(ALIGN_MIDDLE)));
        toolBarManager.add(getAction(getId(ALIGN_BOTTOM)));

        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(getId(INCREASE_NODE_HEIGHT)));
        toolBarManager.add(getAction(getId(DECREASE_NODE_HEIGHT)));
        toolBarManager.add(getAction(getId(INCREASE_NODE_WIDTH)));
        toolBarManager.add(getAction(getId(DECREASE_NODE_WIDTH)));

        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(getId(INCREASE_HORIZANTAL_SPACE)));
        toolBarManager.add(getAction(getId(DECREASE_HORIZANTAL_SPACE)));
        toolBarManager.add(getAction(getId(INCREASE_VERTICAL_SPACE)));
        toolBarManager.add(getAction(getId(DECREASE_VERTICAL_SPACE)));
    }

    private String getId(String id) {
        return ID_PREFIX + id;
    }

    protected void declareGlobalActionKeys() {
    }
}
