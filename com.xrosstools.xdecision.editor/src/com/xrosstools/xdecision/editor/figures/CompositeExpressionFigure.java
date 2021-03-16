package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ToolbarLayout;

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
