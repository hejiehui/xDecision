package com.xrosstools.gef.figures;

import com.xrosstools.gef.parts.EditPart;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Figure implements ImageObserver {
    private JComponent rootPane;
    private EditPart part;
    private Figure parent;
    private LayoutManager layout;

    private int x;
    private int y;
    private int width;
    private int height;
    private int lineWidth = 1;
    private Dimension preferredSize;

    private boolean visible = true;
    private boolean selected = false;
    private List<Figure> components = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();
    private Insets insets = new Insets(0, 0, 0, 0);

    private Color foreground;
    private Color background;
    private String toolTipText;
    private boolean opaque;
    private Point insertionPoint;

    public static boolean isUnderDarcula() {
        return UIManager.getLookAndFeel().getName().contains("Darcula");
    }

    public JComponent getRootPane() {
        return rootPane;
    }

    public void setRootPane(JComponent rootPane) {
        this.rootPane = rootPane;
        for(Figure c: components)
            c.setRootPane(rootPane);
    }

    public EditPart getPart() {
        return part;
    }

    public void setPart(EditPart part) {
        this.part = part;
    }

    public int getX() {
        return x;
    }

    public int getInnerX() {
        return x + insets.left;
    }

    public int getInnerY() {
        return y + insets.top;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMarginWidth() {
        return insets.left + insets.right;
    }

    public int getMarginHeight() {
        return insets.top + insets.bottom;
    }

    public Point getInnerLocation() {
        return new Point(getInnerX(), getInnerY());
    }

    public Dimension getInnerSize() {
        return new Dimension(width - getMarginWidth(), height - getMarginHeight());
    }

    public int getInnerWidth() {
        return width - getMarginWidth();
    }

    public int getInnerrHeight() {
        return height - getMarginHeight();
    }

    public boolean isSelectable() {
        return part != null && part.isSelectable();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public LayoutManager getLayout() {
        return layout;
    }

    public void setLayout(LayoutManager layout) {
        this.layout = layout;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

    public void setLocation(Point location) {
        this.x = location.x;
        this.y = location.y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getTop() {
        return new Point(x + width/2, y);
    }

    public Point getLeft() {
        return new Point(x, y + height/2);
    }

    public Point getBottom() {
        return new Point(x + width/2, y + height);
    }

    public Point getRight() {
        return new Point(x + width, y + height/2);
    }

    public Point getCenter() {
        return new Point(x + width/2, y + height/2);
    }

    public Rectangle getBound() {
        return new Rectangle(x, y, width, height);
    }

    public void resizeToPreferredSize() {
        setSize(getPreferredSize());
    }

    public void setSize(Dimension size) {
        setSize(size.width,size.height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Dimension getSize() {
        return new Dimension(width, height);
    }
    public Dimension getPreferredSize() {
//        if(preferredSize != null)
//            return preferredSize;

        preferredSize = layout == null ? new Dimension(width, height) : layout.preferredLayoutSize(this);

        return preferredSize;
    }

    public List<Figure> getComponents() {
        return components;
    }

    public List<Connection> getConnection() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public boolean containsPoint(int x, int y) {
        return containsPoint(new Point(x, y));
    }

    public Figure selectFigureAt(int x, int y) {
        Figure candidate = findFigureAt(x, y);
        while(candidate != null && !candidate.isSelectable())
            candidate = candidate.getParent();

        return candidate;
    }

    public Figure findFigureAt(int x, int y) {
        Figure found;
        // Check connection first because endpoint of connection may overlap with under figure
        for (Connection conn: connections) {
            found = conn.findFigureAt(x, y);
            if(found == null)
                continue;

            return found;
        }

        for(Figure child: components) {
            found = child.findFigureAt(x, y);
            if(found == null)
                continue;

            return found;
        }
        if(!containsPoint(x, y))
            return null;

        return this;
    }

    public boolean containsPoint(Point hit) {
        if(visible == false)
            return false;

        if(x > hit.x || x + width < hit.x)
            return false;
        if(y > hit.y || y + height < hit.y)
            return false;

        return true;
    }

    public Point getInsertionPoint() {
        return insertionPoint;
    }

    public void setInsertionPoint(Point insertionPoint) {
        this.insertionPoint = insertionPoint;
    }

    public int getInsertionIndex() {
        return layout.getInsertionIndex(this, insertionPoint);
    }

    public void layout() {
        if(layout != null) {
            layout.layoutContainer(this);
        }

        for(Connection conn: connections) {
            conn.layout();
        }
    }

    public  void add(Figure child) {
        child.setParent(this);
        components.add(child);
        child.setRootPane(rootPane);
//        layout();
    }

    public  void add(Figure child, int index) {
        child.setParent(this);
        components.add(index, child);
        child.setRootPane(rootPane);
//        layout();
    }

    public  void remove(Figure child) {
        components.remove(child);
        layout();
    }

    public int getComponentCount() {
        return components.size();
    }

    public void invalidate() {
        layout();
        repaint();
    }

    public void repaint() {
//        rootPane.repaint(x, y, width, height);
    }

    public void paint(Graphics graphics) {
        if(visible == false)
            return;

        layout();
        paintComponent(graphics);
        paintChildren(graphics);
        painLink(graphics);

        if(isSelected())
            paintSelection(graphics);
    }

    public void paintSelection(Graphics graphics) {
        Stroke s = setLineWidth(graphics, isSelected() ? 2 : 1);

        Color oldColor = graphics.getColor();
        graphics.setColor(Color.black);
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
        graphics.setColor(oldColor);

        restore(graphics, s);
    }

    public void paintComponent(Graphics graphics) {}

    public Stroke setLineWidth(Graphics graphics, int size) {
        Graphics2D g2 = (Graphics2D)graphics;
        Stroke s = g2.getStroke();
        g2.setStroke(new BasicStroke(size));
        return s;
    }

    public void restore(Graphics graphics, Stroke s) {
        if(s == null) return;
        Graphics2D g2 = (Graphics2D)graphics;
        g2.setStroke(s);
    }

    public void painLink(Graphics graphics) {
        for(Connection conn: connections)
            conn.paint(graphics);
    }

    public void paintChildren(Graphics graphics) {
        for (Figure f: components)
            f.paint(graphics);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if(rootPane == null)
            return false;

        return rootPane.imageUpdate(img, infoflags, x, y, width, height);
    }

    public Insets getInsets() {
        return insets;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

    public Figure getParent() {
        return parent;
    }

    public void setParent(Figure parent) {
        this.parent = parent;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
}
