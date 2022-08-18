package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.idea.gef.figures.*;

public class MethodExpressionFigure extends Figure {
    private Label nameLabel;
    private EnclosedExpressionFigure paramPanel;
    private BorderLayout layout = new BorderLayout();

    public MethodExpressionFigure() {
        setLayoutManager(layout);
        nameLabel = new Label();
        add(nameLabel);
        layout.setConstraint(nameLabel, PositionConstants.LEFT);
        paramPanel = EnclosedExpressionFigure.createBracketFigure();
        add(paramPanel);
        layout.setConstraint(paramPanel, PositionConstants.CENTER);
//        this.setBorder(new MarginBorder(0, 2, 0, 2));
    }

    public void setMethodName(String name) {
        nameLabel.setText(name);
    }

    public void setParameterFigure(Figure parameterFigure) {
        paramPanel.setEnclosedFigure(parameterFigure);
    }

    public void setMethodValidation(boolean valid) {
        nameLabel.setForegroundColor(valid ? ColorConstants.black: ColorConstants.red);
    }

    public void setExpandedFigure(Figure expandedFigure) {
        add(expandedFigure);
        layout.setConstraint(expandedFigure, PositionConstants.RIGHT);
    }
}