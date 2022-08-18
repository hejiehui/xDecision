package com.xrosstools.idea.gef.figures;

import java.awt.*;

public class BorderLayout implements LayoutManager {
    private Figure center;
    private Figure left;
    private Figure top;
    private Figure bottom;
    private Figure right;
    private int vGap;
    private int hGap;

    public BorderLayout() {
        vGap = 0;
        hGap = 0;
    }

    private int getWidth(Insets border) {
        return border.left + border.right;
    }

    private int getHeight(Insets border) {
        return border.top + border.bottom;
    }

    public Dimension union(Dimension base, Dimension d) {
        base.width = Math.max(base.width, d.width);
        base.height = Math.max(base.height, d.height);
        return base;
    }

    @Override
    public synchronized Dimension preferredLayoutSize(Figure figure) {
        int wHint = -1;
        int hHint = -1;
        int minWHint = 0;
        int minHHint = 0;
        if (wHint < 0)
            minWHint = -1;
        if (hHint < 0)
            minHHint = -1;

        Insets border = figure.getInsets();
        wHint = Math.max(minWHint, wHint - getWidth(border));
        hHint = Math.max(minHHint, hHint - getHeight(border));
        Dimension prefSize = new Dimension();
        int middleRowWidth = 0;
        int middleRowHeight = 0;
        int rows = 0;
        int columns = 0;
        if (top != null && top.isVisible()) {
            Dimension childSize = top.getPreferredSize();
            hHint = Math.max(minHHint, hHint - (childSize.height + vGap));
            prefSize.setSize(childSize);
            rows++;
        }
        if (bottom != null && bottom.isVisible()) {
            Dimension childSize = bottom.getPreferredSize();
            hHint = Math.max(minHHint, hHint - (childSize.height + vGap));
            prefSize.width = Math.max(prefSize.width, childSize.width);
            prefSize.height += childSize.height;
            rows++;
        }
        if (left != null && left.isVisible()) {
            Dimension childSize = left.getPreferredSize();
            middleRowWidth = childSize.width;
            middleRowHeight = childSize.height;
            wHint = Math.max(minWHint, wHint - (childSize.width + hGap));
            columns++;
        }
        if (right != null && right.isVisible()) {
            Dimension childSize = right.getPreferredSize();
            middleRowWidth += childSize.width;
            middleRowHeight = Math.max(childSize.height, middleRowHeight);
            wHint = Math.max(minWHint, wHint - (childSize.width + hGap));
            columns++;
        }
        if (center != null && center.isVisible()) {
            Dimension childSize = center.getPreferredSize();
            middleRowWidth += childSize.width;
            middleRowHeight = Math.max(childSize.height, middleRowHeight);
            columns++;
        }
        rows += columns <= 0 ? 0 : 1;
        prefSize.height += middleRowHeight + getHeight(border) + (rows - 1) * vGap;
        prefSize.width = Math.max(prefSize.width, middleRowWidth) + getWidth(border) + (columns - 1) * hGap;
//        union(prefSize, getBorderPreferredSize(figure));

        return prefSize;
    }

    @Override
    public synchronized void layoutContainer(Figure container) {
        Point innerLoc = container.getInnerLocation();
        Dimension innerSize = container.getInnerSize();

        Rectangle area = new Rectangle(innerLoc, innerSize);
        Rectangle rect = new Rectangle();
        if (top != null && top.isVisible()) {
            Dimension childSize = top.getPreferredSize();
            rect.setLocation(area.x, area.y);
            rect.setSize(childSize);
            rect.width = area.width;
            top.setBounds(rect);
            area.y += rect.height + vGap;
            area.height -= rect.height + vGap;
        }
        if (bottom != null && bottom.isVisible()) {
            Dimension childSize = bottom.getPreferredSize();
            rect.setSize(childSize);
            rect.width = area.width;
            rect.setLocation(area.x, (area.y + area.height) - rect.height);
            bottom.setBounds(rect);
            area.height -= rect.height + vGap;
        }
        if (left != null && left.isVisible()) {
            Dimension childSize = left.getPreferredSize();
            rect.setLocation(area.x, area.y);
            rect.width = childSize.width;
            rect.height = Math.max(0, area.height);
            left.setBounds(rect);
            area.x += rect.width + hGap;
            area.width -= rect.width + hGap;
        }
        if (right != null && right.isVisible()) {
            Dimension childSize = right.getPreferredSize();
            rect.width = childSize.width;
            rect.height = Math.max(0, area.height);
            rect.setLocation((area.x + area.width) - rect.width, area.y);
            right.setBounds(rect);
            area.width -= rect.width + hGap;
        }
        if (center != null && center.isVisible()) {
            if (area.width < 0)
                area.width = 0;
            if (area.height < 0)
                area.height = 0;
            center.setBounds(area);
        }
    }

    public void remove(Figure child) {
        if (center == child)
            center = null;
        else if (top == child)
            top = null;
        else if (bottom == child)
            bottom = null;
        else if (right == child)
            right = null;
        else if (left == child)
            left = null;
    }

    public void setConstraint(Figure child, Object constraint) {
        remove(child);
//        super.setConstraint(child, constraint);
        if (constraint == null)
            return;
        switch (((Integer) constraint).intValue()) {
            case 2 : // '\002'
                center = child;
                break;
            case 8 : // '\b'
                top = child;
                break;
            case 32 : // ' '
                bottom = child;
                break;
            case 4 : // '\004'
                right = child;
                break;
            case 1 : // '\001'
                left = child;
                break;
        }
    }

    public void setHorizontalSpacing(int gap) {
        hGap = gap;
    }

    public void setVerticalSpacing(int gap) {
        vGap = gap;
    }

    @Override
    public int getInsertionIndex(Figure parent, Point insertionPoint) {
        return 0;
    }

    @Override
    public void paintInsertionFeedback(Figure parent, Point insertionPoint, Graphics gef) {

    }
}
