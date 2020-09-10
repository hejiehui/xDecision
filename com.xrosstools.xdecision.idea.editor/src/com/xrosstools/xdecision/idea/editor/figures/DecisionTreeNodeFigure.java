package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.gef.figures.Label;
import com.xrosstools.gef.figures.RoundedRectangle;
import com.xrosstools.gef.figures.ToolbarLayout;

import java.awt.*;

public class DecisionTreeNodeFigure extends RoundedRectangle {
    private static int BORDER_WIDTH = 5;
    private com.xrosstools.gef.figures.Label factorLabel;
    private com.xrosstools.gef.figures.Label decisionLabel;

    public DecisionTreeNodeFigure() {
        setMinSize(new Dimension(100, 50));
        setLayout(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT, 10));
        factorLabel = new Label();
//        factorLabel .getInsets().set(BORDER_WIDTH, 0, BORDER_WIDTH, 0);
        factorLabel .getInsets().set(BORDER_WIDTH, BORDER_WIDTH, 0, BORDER_WIDTH);
        add(factorLabel);

        decisionLabel = new Label();
        decisionLabel.setForeground(new Color(0, 125, 0));
//        decisionLabel.getInsets().set(BORDER_WIDTH, 0, 10, 0);
        decisionLabel.getInsets().set(BORDER_WIDTH, BORDER_WIDTH, 10, BORDER_WIDTH);
        add(decisionLabel);
    }

//    private Dimension preferredSize;
//
//    @Override
//    public Dimension getPreferredSize() {
//        return this.preferredSize;
//    }
//
//    @Override
//    public void setPreferredSize(Dimension preferredSize) {
//        this.preferredSize = preferredSize;
//    }
//
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
