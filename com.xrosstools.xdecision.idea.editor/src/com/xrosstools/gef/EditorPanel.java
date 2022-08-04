package com.xrosstools.gef;

     import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.treeStructure.Tree;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.figures.Connection;
import com.xrosstools.gef.figures.Endpoint;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.parts.*;
import com.xrosstools.gef.util.IPropertySource;
import com.xrosstools.gef.util.PropertyTableModel;
import com.xrosstools.gef.util.SimpleTableCellEditor;
import com.xrosstools.gef.util.SimpleTableRenderer;

import javax.swing.*;
     import javax.swing.event.TreeSelectionListener;
     import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
     import java.util.Enumeration;

public class EditorPanel<T extends IPropertySource> extends JPanel {
    private JBSplitter mainPane;
    private JBSplitter diagramPane;
    private Tree treeNavigator;
    private JBTable tableProperties;

    private JScrollPane innerDiagramPane;
    private UnitPanel unitPanel;
    private GraphicalEditPart root;
    private TreeEditPart treeRoot;

    private T diagram;
    private ContextMenuProvider contextMenuBuilder;
    private ContextMenuProvider outlineContextMenuProvider;

    private Point lastHit;
    private DefaultTreeModel treeModel;
    private PropertyTableModel tableModel;
    private Figure lastSelected;
    private Figure lastHover;
    private Object newModel;
    private GraphicalEditPart sourcePart;

    private PanelContentProvider contentProvider;

    public EditorPanel(PanelContentProvider<T> contentProvider) throws Exception {
        this.contentProvider = contentProvider;
        contentProvider.setEditorPanel(this);
        diagram = contentProvider.getContent();
        contextMenuBuilder = contentProvider.getContextMenuProvider();
        outlineContextMenuProvider = contentProvider.getOutlineContextMenuProvider();

        createVisual();
        registerListener();
        build();
    }

    private void createVisual() {
        setLayout(new BorderLayout());
        mainPane = new JBSplitter(true, 0.8f);
        mainPane.setDividerWidth(3);
        add(mainPane, BorderLayout.CENTER);

        mainPane.setFirstComponent(createMain());
        mainPane.setSecondComponent(createProperty());
    }

    private JComponent createMain() {
        diagramPane = new JBSplitter(false, 0.8f);
        diagramPane.setDividerWidth(3);

        diagramPane.setFirstComponent(createEditArea());
        diagramPane.setSecondComponent(createTree());

        return diagramPane;
    }

    private JComponent createEditArea() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createPalette(), BorderLayout.WEST);
        mainPanel.add(createToolbar(), BorderLayout.NORTH);

        unitPanel = new UnitPanel();
        innerDiagramPane = new JBScrollPane(unitPanel);
        innerDiagramPane.setLayout(new ScrollPaneLayout());
        innerDiagramPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        innerDiagramPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        innerDiagramPane.getVerticalScrollBar().setUnitIncrement(50);

        mainPanel.add(innerDiagramPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JComponent createPalette() {
        JPanel palette = new JPanel();
        GridLayout layout = new GridLayout(0, 1, 10,0);
        palette.setLayout(layout);

        palette.add(createResetButton());
        contentProvider.buildPalette(palette);
        return palette;
    }

    private JComponent createToolbar() {
        JToolBar  toolbar = new JToolBar ();
        toolbar.setFloatable(false);
        contentProvider.buildToolbar(toolbar);
        return toolbar;
    }

    private JComponent createTree() {
        treeNavigator = new Tree();
        treeNavigator.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeNavigator.setExpandsSelectedPaths(true);

        JScrollPane treePane = new JBScrollPane(treeNavigator);
        treePane.setLayout(new ScrollPaneLayout());
        treePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        treePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        treePane.getVerticalScrollBar().setUnitIncrement(50);

        return treePane;
    }

    private JComponent createProperty() {
        tableProperties = new JBTable();
        tableModel = new PropertyTableModel(diagram, contentProvider);
        tableProperties.setModel(tableModel);

        JScrollPane scrollPane = new JBScrollPane(tableProperties);
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        return scrollPane;
    }

    private JButton createResetButton() {
        JButton btn = new JButton("Select", IconLoader.findIcon("icons/tree.png"));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> reset());
        return btn;
    }

    private void reset(){
        gotoNext(ready);
    }

    public void createConnection(Object connModel){
        newModel = connModel;
        gotoNext(connectionCreated);
    }

    public void createModel(Object model){
        newModel = model;
        gotoNext(modelCreated);
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    private void build() {
        EditContext editContext = new EditContext(this);
        EditPartFactory editPartFactory = contentProvider.createEditPartFactory(editContext);
        EditPartFactory treeEditPartFactory = contentProvider.createTreePartFactory(editContext);

        root = (GraphicalEditPart) editPartFactory.createEditPart(null, diagram);
        treeRoot = (TreeEditPart) treeEditPartFactory.createEditPart(null, diagram);

        treeModel = new DefaultTreeModel(treeRoot.getTreeNode(), false);
        tableModel = new PropertyTableModel((IPropertySource)treeRoot.getModel(), contentProvider);

        treeNavigator.setModel(treeModel);
        contentProvider.preBuildRoot();

        root.refresh();
        treeRoot.refresh();
        contentProvider.postBuildRoot();

        postBuild();
        updateVisual();
    }

    private TreeSelectionListener treeSelectionListener = e -> selectTreeNode();

    private void postBuild() {
        treeNavigator.addTreeSelectionListener(treeSelectionListener);

        treeNavigator.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeNavigator.getLastSelectedPathComponent();
                    if(node == null)
                        return;

                    outlineContextMenuProvider.buildDisplayMenu(node.getUserObject()).show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        });

        treeNavigator.setCellRenderer(new DefaultTreeCellRenderer(){
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean sel, boolean expanded, boolean leaf, int row,
                                                          boolean hasFocus){
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                TreeEditPart treePart = (TreeEditPart)((DefaultMutableTreeNode)value).getUserObject();
                setText(treePart.getText());
                setIcon(treePart.getImage());
                return this;
            }
        });

        treeNavigator.expandPath(new TreePath(treeRoot.getTreeNode()));
    }

    public void refresh() {
        root.refresh();
        treeRoot.refresh();
        refreshVisual();
        contentProvider.save();
    }

    private void updateTooltip(Point location) {
        Figure f = findFigureAt(location);
        if (f == null || f == root.getFigure())
            unitPanel.setToolTipText(null);
        else {
            unitPanel.setToolTipText(f.getToolTipText());
        }
    }

    private Figure findFigureAt(Point location) {
        Figure rootFigure = root.getFigure();
        Figure selected = rootFigure.selectFigureAt(location.x, location.y);
        return selected == null ? rootFigure : selected;
    }

    private Command updateHover(Figure underPoint, Command command, Point location) {
        underPoint = underPoint == null ? root.getFigure() : underPoint;

        if(lastHover != null && lastHover != underPoint)
            lastHover.setInsertionPoint(null);

        if(command != null)
            underPoint.setInsertionPoint(location);

        unitPanel.repaint();
        lastHover = underPoint;

        return command;
    }

    private void clearHover() {
        if(lastHover != null) {
            lastHover.setInsertionPoint(null);
            unitPanel.repaint();
            lastHover = null;
        }
    }

    private void registerListener() {
        unitPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {curHandle.mouseDragged(e);}
            public void mouseMoved(MouseEvent e) {curHandle.mouseMoved(e);}
        });

        unitPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {curHandle.mouseClicked(e);}
            public void mousePressed(MouseEvent e) {curHandle.mousePressed(e);}
            public void mouseReleased(MouseEvent e) {curHandle.mouseReleased(e);}
            public void mouseEntered(MouseEvent e) {curHandle.mouseEntered(e);}
            public void mouseExited(MouseEvent e) {curHandle.mouseExited(e);}
            public void mouseWheelMoved(MouseWheelEvent e){curHandle.mouseWheelMoved(e);}
        });

        unitPanel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {curHandle.keyTyped(e);}
            public void keyPressed(KeyEvent e) {curHandle.keyPressed(e);}
            public void keyReleased(KeyEvent e) {curHandle.keyReleased(e);}
        });
    }

    private void gotoNext(InteractionHandle next) {
        curHandle.leave();
        next.enter();
        curHandle = next;
    }

    private void updatePropertySelection(Object model){
        if(model == null)
            return;

        if(!(model instanceof IPropertySource)) {
            tableModel = null;
            tableProperties.setVisible(false);
            return;
        }

        if(tableModel != null && tableModel.isSame((IPropertySource) model))
            return;

        tableModel = new PropertyTableModel((IPropertySource) model, contentProvider);
        tableProperties.setVisible(true);
        tableProperties.setModel(tableModel);
        tableProperties.setDefaultRenderer(Object.class, new SimpleTableRenderer(tableModel));
        tableProperties.getColumnModel().getColumn(1).setCellEditor(new SimpleTableCellEditor(tableModel));
    }

    private void updateTreeSelection(Object model) {
        triggedByFigure = true;
        TreeEditPart treePart = treeRoot.findEditPart(model);
        if(treePart == null) {
            treeNavigator.clearSelection();
            return;
        }

        TreeNode selected = treePart.getTreeNode();
        expandSelected(new TreePath(treeNavigator.getModel().getRoot()), selected);
        treeNavigator.scrollPathToVisible(new TreePath(selected));
    }

    private void updateFigureSelection(Figure selected) {
        if(lastSelected == selected)
            return;

        if(lastSelected != null)
            lastSelected.setSelected(false);

        if(selected != null) {
            lastSelected = selected;
            lastSelected.setSelected(true);
        }

        refreshVisual();
        adjust(innerDiagramPane.getVerticalScrollBar(), lastSelected.getY(), lastSelected.getHeight());
        adjust(innerDiagramPane.getHorizontalScrollBar(), lastSelected.getX(), lastSelected.getWidth());
    }

    private boolean triggedByFigure = false;

    private void selectTreeNode() {
        if(triggedByFigure) {
            triggedByFigure = false;
            return;
        }

        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treeNavigator.getLastSelectedPathComponent();
        if(treeNode == null)
            return;

        TreeEditPart treePart = (TreeEditPart)treeNode.getUserObject();

        Figure selected = treePart.getContext().findFigure(treePart.getModel());
        updateFigureSelection(selected);
        updatePropertySelection(treePart.getModel());
    }

    private void selectFigureAt(Point location) {
        Figure f = findFigureAt(location);
        updateFigureSelection(f);

        Object model = f == null ? null : f.getPart().getModel();
        updateTreeSelection(model);
        updatePropertySelection(model);

        if(f == null) {
            gotoNext(ready);
            return;
        }

        if(f instanceof Endpoint && f.getParent() instanceof Connection) {
            gotoNext(((Endpoint)f).isConnectionSourceEndpoint() ? sourceEndpointSelected : targetEndpointSelected);
        }
    }

    private boolean expandSelected(TreePath parent, TreeNode selectedNode) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();

        if(selectedNode == node) {
            treeNavigator.setSelectionPath(parent);
            return true;
        }

        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                if(expandSelected(path, selectedNode)) {
                    // Expansion or collapse must be done bottom-up
                    treeNavigator.expandPath(parent);
                    return  true;
                }
            }
        }
        return false;
    }

    private void adjust(JScrollBar scrollBar, int start, int length ) {
        if (scrollBar.getValue() > start || scrollBar.getValue() + scrollBar.getVisibleAmount() < start + length)
            scrollBar.setValue(start - 100);
    }

    private void showContexMenu(int x, int y) {
        contextMenuBuilder.buildDisplayMenu(lastSelected.getPart()).show(unitPanel, x, y);
    }

    public void refreshVisual() {
        updateVisual();
        unitPanel.grabFocus();
    }

    public void execute(Command action) {
        if(action == null)
            return;

        action.run();

        refresh();
    }

    private void updateVisual() {
        int height = unitPanel.getPreferredSize().height;
        innerDiagramPane.getVerticalScrollBar().setMaximum(height);

        int width = unitPanel.getPreferredSize().width;
        innerDiagramPane.getHorizontalScrollBar().setMaximum(width);
        repaint();
    }

    private class UnitPanel extends JPanel {
        @Override
        protected void paintChildren(Graphics g) {
            root.getFigure().paint(g);
        }

        @Override
        public Dimension getPreferredSize() {
            if(root == null)
                return new Dimension(500,800);

            Dimension size = root.getFigure().getPreferredSize();
            root.getFigure().layout();
            size.height+=100;
            return size;
        }
    }

    private class InteractionHandle extends MouseAdapter implements KeyListener {
        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
        public void enter() {}
        public void leave() {}
        public String id;

        public InteractionHandle(String id) {
            this.id = id;
        }
    }

    private InteractionHandle ready = new InteractionHandle("ready") {
        public void enter() {
            if(lastSelected != null) {
                lastSelected.setSelected(false);
                lastSelected = null;
            }

            newModel = null;
            sourcePart = null;
            treeNavigator.clearSelection();

            refreshVisual();
        }
        public void mouseMoved(MouseEvent e) {
            updateTooltip(e.getPoint());
        }
        public void mousePressed(MouseEvent e) {
            selectFigureAt(e.getPoint());
            lastHit = new Point(e.getPoint());
            gotoNext(figureSelected);
        }
    };

    private InteractionHandle figureSelected = new InteractionHandle("figureSelected") {
        private boolean moved;
        public int deltaX;
        public int deltaY;
        private Command getMoveCommand(Figure underPoint, Point p) {
            p = new Point(p);
            p.translate(deltaX, deltaY);
            return underPoint.getPart().getEditPolicy().getMoveCommand(lastSelected.getPart(), new Rectangle(p.x, p.y, lastSelected.getWidth(), lastSelected.getHeight()));
        }
        public void enter() {
            moved = false;
            deltaX = lastSelected.getX() - lastHit.x;
            deltaY = lastSelected.getY() - lastHit.y;
        }
        public void mouseMoved(MouseEvent e) {
            updateTooltip(e.getPoint());
        }
        public void mousePressed(MouseEvent e) {
            selectFigureAt(e.getPoint());
            lastHit = new Point(e.getPoint());
            enter();
        }
        public void mouseDragged(MouseEvent e) {
            moved = true;
            Point p = e.getPoint();
            if(lastSelected.getPart().getModel() == diagram)
                return;

            if(lastSelected instanceof Connection)
                return;

            Figure underPoint = findFigureAt(p);
            updateHover(underPoint, getMoveCommand(underPoint, p), p);
        }
        public void mouseReleased(MouseEvent e) {
            // Drag and drop
            if (lastSelected != null && lastHover != null && lastSelected != lastHover) {
                Point p = e.getPoint();
                Figure underPoint = findFigureAt(p);
                execute(updateHover(underPoint, getMoveCommand(underPoint, p), p));
                return;
            }

            if (e.isPopupTrigger())
                showContexMenu(e.getX(), e.getY());
        }
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                lastSelected.getPart().performAction();
            }
        }
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                Command deleteCmd = lastSelected.getPart().getEditPolicy().getDeleteCommand();
                if(deleteCmd == null)
                    return;

                execute(deleteCmd);
            }
        }
    };

    private InteractionHandle modelCreated = new InteractionHandle("modelCreated") {
        private Command getCreateCommand(Figure underPoint, Point p) {
            return underPoint.getPart().getEditPolicy().getCreateCommand(newModel, p);
        }
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            Figure underPoint = findFigureAt(p);
            updateHover(underPoint, getCreateCommand(underPoint, p), p);
        }
        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            Figure underPoint = findFigureAt(p);
            Command createCommand = updateHover(underPoint, getCreateCommand(underPoint, p), p);

            if(createCommand != null) {
                execute(createCommand);
            }

            gotoNext(ready);
        }
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                gotoNext(ready);
        }
    };

    private InteractionHandle connectionCreated = new InteractionHandle("connectionCreated") {
        public void mouseMoved(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(f.getPart().getEditPolicy().isSelectableSource(newModel))
                f.getPart().showSourceFeedback();
        }
        public void mousePressed(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(f.getPart().getEditPolicy().isSelectableSource(newModel)) {
                sourcePart = f.getPart();
                gotoNext(sourceSelected);
            } else {
                gotoNext(ready);
            }
        }
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                gotoNext(ready);
        }
    };

    private InteractionHandle sourceSelected = new InteractionHandle("sourceSelected") {
        private Command getCreateConnectionCommand(Figure f) {
            return f.getPart().getEditPolicy().getCreateConnectionCommand(newModel, sourcePart);
        }
        public void mouseMoved(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(getCreateConnectionCommand(f) != null)
                f.getPart().showTargetFeedback();
        }
        public void mousePressed(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            Command createConnectionCmd = getCreateConnectionCommand(f);

            if(createConnectionCmd != null) {
                execute(createConnectionCmd);
            }

            gotoNext(ready);
        }
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                gotoNext(ready);
        }
    };

    private InteractionHandle sourceEndpointSelected = new InteractionHandle("sourceEndpointSelected") {
        private Endpoint endpoint;
        public void enter() {
            endpoint = (Endpoint)lastSelected;
        }
        public void mousePressed(MouseEvent e) {
            selectFigureAt(e.getPoint());
            if(lastSelected == endpoint)
                return;

            endpoint = null;
            gotoNext(figureSelected);
        }
        public void mouseDragged(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(getCommand(f) != null)
                f.getPart().showSourceFeedback();
        }
        public void mouseReleased(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            execute(getCommand(f));
            gotoNext(ready);
        }
        private Command getCommand(Figure f) {
            Connection connection = ((Endpoint)lastSelected).getParentConnection();
            return f.getPart().getEditPolicy().getReconnectSourceCommand(connection.getConnectionPart());
        }
    };

    private InteractionHandle targetEndpointSelected = new InteractionHandle("targetEndpointSelected") {
        private Endpoint endpoint;
        public void enter() {
            endpoint = (Endpoint)lastSelected;
        }
        public void mousePressed(MouseEvent e) {
            selectFigureAt(e.getPoint());
            if(lastSelected == endpoint)
                return;

            endpoint = null;
            gotoNext(figureSelected);
        }
        public void mouseDragged(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(getCommand(f) != null)
                f.getPart().showTargetFeedback();
        }
        public void mouseReleased(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            execute(getCommand(f));
            gotoNext(ready);
        }
        private Command getCommand(Figure f) {
            Connection connection = ((Endpoint)lastSelected).getParentConnection();
            return f.getPart().getEditPolicy().getReconnectTargetCommand(connection.getConnectionPart());
        }
    };
    private InteractionHandle curHandle = ready;
}