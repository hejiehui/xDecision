package com.xrosstools.gef.figures;

import java.awt.*;

public interface LayoutManager {
    Dimension preferredLayoutSize(Figure parent);
    void layoutContainer(Figure parent);
    int getInsertionIndex(Figure parent, Point insertionPoint);
    void paintInsertionFeedback(Figure parent, Point insertionPoint, Graphics gef);
}
