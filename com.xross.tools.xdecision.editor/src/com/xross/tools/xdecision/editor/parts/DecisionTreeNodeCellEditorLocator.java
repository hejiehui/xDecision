package com.xross.tools.xdecision.editor.parts;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import com.xross.tools.xdecision.editor.figures.DecisionTreeNodeFigure;

public class DecisionTreeNodeCellEditorLocator implements CellEditorLocator {
    private DecisionTreeNodeFigure nodeFigure;

    /**
     * Creates a new ActivityCellEditorLocator for the given Label
     * @param nodeFigure the Label
     */
    public DecisionTreeNodeCellEditorLocator(DecisionTreeNodeFigure nodeFigure) {
        this.nodeFigure = nodeFigure;
    }

    /**
     * @see CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
     */
    public void relocate(CellEditor celleditor) {
        Text text = (Text) celleditor.getControl();
        Point pref = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        Rectangle rect = nodeFigure.getTextBounds();
        text.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
    }
}
