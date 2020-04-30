package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.gef.figures.Label;
import com.xrosstools.gef.figures.RoundedRectangle;
import com.xrosstools.gef.figures.ToolbarLayout;

public class DecisionTreeNodeFigure extends RoundedRectangle {
    private static int BORDER_WIDTH = 5;
    private com.xrosstools.gef.figures.Label factorLabel;
    private com.xrosstools.gef.figures.Label decisionLabel;

    public DecisionTreeNodeFigure() {
        setLayout(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT, 10));
        factorLabel = new com.xrosstools.gef.figures.Label();
//        factorLabel .getInsets().set(BORDER_WIDTH, 0, BORDER_WIDTH, 0);
        factorLabel .getInsets().set(BORDER_WIDTH, BORDER_WIDTH, 0, BORDER_WIDTH);
        add(factorLabel);

        decisionLabel = new Label();
//        decisionLabel.getInsets().set(BORDER_WIDTH, 0, 10, 0);
        decisionLabel.getInsets().set(BORDER_WIDTH, BORDER_WIDTH, 10, BORDER_WIDTH);
        add(decisionLabel);
    }

    public void setFactor(String name) {
    	factorLabel.setText(name);
    	factorLabel.setToolTipText(name);
    	repaint();
    }
    
    public void setDecision(String decision) {
    	decisionLabel.setText(decision);
    	decisionLabel.setToolTipText(decision);
        repaint();
    }
}
