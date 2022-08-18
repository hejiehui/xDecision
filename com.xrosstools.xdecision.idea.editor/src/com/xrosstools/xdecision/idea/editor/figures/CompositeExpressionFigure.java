package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.ToolbarLayout;

public class CompositeExpressionFigure extends Figure {

    public CompositeExpressionFigure() {
        ToolbarLayout elementsLayout = new ToolbarLayout();
        elementsLayout.setHorizontal(true);
        elementsLayout.setSpacing(2);
        elementsLayout.setStretchMinorAxis(false);
        elementsLayout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        setLayoutManager(elementsLayout);
    }
}
