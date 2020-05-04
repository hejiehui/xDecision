package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.treeStructure.Tree;
import com.xrosstools.gef.Action;
import com.xrosstools.gef.Command;
import com.xrosstools.gef.figures.Connection;
import com.xrosstools.gef.figures.Endpoint;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.parts.EditContext;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.gef.util.*;
import com.xrosstools.xdecision.idea.editor.actions.*;
import com.xrosstools.xdecision.idea.editor.io.DecisionTreeDiagramFactory;
import com.xrosstools.xdecision.idea.editor.layout.LayoutAlgorithm;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreePartFactory;
import com.xrosstools.xdecision.idea.editor.treeparts.DecisionTreeTreePartFactory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

//TODO make it factory instead of subclass of JPAnel
public class DecisionTreeDiagramPanel extends JPanel implements DecisionTreeActionConstants, DecisionTreeMessages, PropertyChangeListener {
    private Project project;
    private VirtualFile virtualFile;

    private JBSplitter mainPane;
    private JBSplitter diagramPane;
    private Tree treeNavigator;
    private JBTable tableProperties;

    private JScrollPane innerDiagramPane;
    private UnitPanel unitPanel;
    private EditPart root;
    private TreeEditPart treeRoot;

    private DecisionTreeDiagramFactory factory = new DecisionTreeDiagramFactory();
    private DecisionTreeDiagram diagram;

    private Point lastHit;
    private Figure lastSelected;
    private Figure lastHover;
    private Object newModel;
    private EditPart sourcePart;

    public DecisionTreeDiagramPanel(Project project, VirtualFile virtualFile) throws Exception {
        this.project = project;
        this.virtualFile = virtualFile;
        diagram = factory.getFromXML(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
//        diagram.setProject(project);
//        diagram.setFilePath(virtualFile);
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
        palette.add(createConnectionButton());
        palette.add(createNodeButton());

        palette.add(createPaletteButton(new DecisionTreeCreateDecisionAction(project, diagram), CREATE_NEW_DECISION, CREATE_NEW_DECISION_MSG));
        palette.add(createPaletteButton(new DecisionTreeCreateFactorAction(project, diagram), CREATE_NEW_FACTOR, CREATE_NEW_FACTOR_MSG));
        palette.add(createPaletteButton(new DecisionTreeCodeGenAction(true), GEN_TEST_CODE, GEN_JUNIT_TEST_CODE_MSG));

        return palette;
    }

    private JComponent createToolbar() {
        JToolBar  toolbar = new JToolBar ();
        toolbar.setFloatable(false);

        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, true, 1), ALIGN_BOTTOM, ALIGN_BOTTOM_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, true, 0.5f), ALIGN_MIDDLE, ALIGN_MIDDLE_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, true, 0), ALIGN_TOP, ALIGN_TOP_MSG));
        toolbar.addSeparator();

        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, false, 0), ALIGN_LEFT, ALIGN_LEFT_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, false, 0.5f), ALIGN_CENTER, ALIGN_CENTER_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, false, 1), ALIGN_RIGHT, ALIGN_RIGHT_MSG));
        toolbar.addSeparator();

        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, true, false, true), INCREASE_NODE_HEIGHT, INCREASE_NODE_HEIGHT_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, true, false, false),DECREASE_NODE_HEIGHT, DECREASE_NODE_HEIGHT_MSG));

        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, true, true, true), INCREASE_NODE_WIDTH, INCREASE_NODE_WIDTH_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, true, true, false), DECREASE_NODE_WIDTH, DECREASE_NODE_WIDTH_MSG));

        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, false, true, true), INCREASE_HORIZANTAL_SPACE, INCREASE_HORIZANTAL_SPACE_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, false, true, false), DECREASE_HORIZANTAL_SPACE, DECREASE_HORIZANTAL_SPACE_MSG));

        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, false, false, true), INCREASE_VERTICAL_SPACE, INCREASE_VERTICAL_SPACE_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeResizeAction(diagram, false, false, false), DECREASE_VERTICAL_SPACE, DECREASE_VERTICAL_SPACE_MSG));

        return toolbar;
    }

    private JButton createToolbarButton(Action action, String iconName, String tooltip) {
        JButton btn = new JButton(IconLoader.findIcon(Activator.getIconPath(iconName)));
        btn.setToolTipText(tooltip);
        btn.setContentAreaFilled(false);
        btn.addActionListener(action);
        btn.setSize(new Dimension(32, 32));
        btn.setPreferredSize(new Dimension(32, 32));
        return btn;
    }

    private JComponent createTree() {
        treeNavigator = new Tree();
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
        PropertyTableModel model = new PropertyTableModel(diagram, this);
        setModel(model);

        JScrollPane scrollPane = new JBScrollPane(tableProperties);
        scrollPane.setLayout(new ScrollPaneLayout());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);

        return scrollPane;
    }

    private JButton createResetButton() {
        JButton btn = new JButton("Select", IconLoader.findIcon(Activator.getIconPath(Activator.TREE)));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> gotoNext(ready));
        return btn;
    }

    private JButton createConnectionButton() {
        JButton btn = new JButton("Link Node", IconLoader.findIcon(Activator.getIconPath(Activator.CONNECTION)));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> {
            newModel = new DecisionTreeNodeConnection();
            gotoNext(connectionCreated);
        });
        return btn;
    }

    private JButton createNodeButton() {
        JButton btn = new JButton("Create node", IconLoader.findIcon(Activator.getIconPath(Activator.NODE)));
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> {
            newModel = new DecisionTreeNode();
            gotoNext(modelCreated);
        });
        return btn;
    }

    private JButton createPaletteButton(Action action, String iconName, String tooltip) {
        JButton btn = new JButton(tooltip, IconLoader.findIcon(Activator.getIconPath(iconName)));
        btn.setContentAreaFilled(false);
        btn.addActionListener(action);
        return btn;
    }

    private void save() {
        ApplicationManager.getApplication().runWriteAction(() -> {
            try {
                String contentStr = XmlHelper.format(factory.convertToXML(diagram));
                virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
            } catch (Throwable e) {
                throw new IllegalStateException("Can not save document " + virtualFile.getName(), e);
            }
        });
    }

    private void build() {
        EditContext context = new EditContext(this);

        DecisionTreePartFactory f = new DecisionTreePartFactory(context);
        root = f.createEditPart(null, diagram);
        root.build();

        new LayoutAlgorithm().layout(diagram);


        DecisionTreeTreePartFactory treePartFactory = new DecisionTreeTreePartFactory(context);
        treeRoot = treePartFactory.createEditPart(null, diagram);
        treeNavigator.setModel(new DefaultTreeModel(treeRoot.build(), false));
        treeNavigator.addTreeSelectionListener(e -> selectedNode());

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

        updateVisual();
    }

    public void rebuild() {
        build();
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

    private void selectFigureAt(Point location) {
        Figure f = findFigureAt(location);
        updateSelection(f);

        if(f == null) {
            gotoNext(ready);
            return;
        }

        TreeEditPart treePart = treeRoot.findEditPart(f.getPart().getModel());
        if(treePart != null) {
            DefaultMutableTreeNode treeNode = treePart.getTreeNode();
            //update tree selection
            if (treeNode != null)
                treeNavigator.setSelectionPath(new TreePath(treeNode.getPath()));
        }

        if(f instanceof Endpoint && f.getParent() instanceof Connection) {
            gotoNext(((Endpoint)f).isConnectionSourceEndpoint() ? sourceEndpointSelected : targetEndpointSelected);
        }
    }

    private Command updateHover(Figure underPoint,Command command, Point location) {
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
        System.err.println("Leaving " + curHandle.id);
        curHandle.leave();

        System.err.println("Entering " + next.id);
        next.enter();
        curHandle = next;
    }

    private void setModel(PropertyTableModel model){
        tableProperties.setModel(model);
        tableProperties.setDefaultRenderer(Object.class, new SimpleTableRenderer(model));
        tableProperties.getColumnModel().getColumn(1).setCellEditor(new SimpleTableCellEditor(model));
    }

    private void updateSelection(Figure selected) {
        if(lastSelected == selected)
            return;

        if(lastSelected != null)
            lastSelected.setSelected(false);

        if(selected != null) {
            lastSelected = selected;
            lastSelected.setSelected(true);
        }

        refresh();
    }

    private void selectedNode() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeNavigator.getLastSelectedPathComponent();
        if(node == null)
            return;

        TreeEditPart treePart = (TreeEditPart)node.getUserObject();
        Figure selected = treePart.getContext().findFigure(treePart.getModel());

        if(selected == null || selected == lastSelected)
            return;

        updateSelection(selected);

        adjust(innerDiagramPane.getVerticalScrollBar(), lastSelected.getY(), lastSelected.getHeight());
        adjust(innerDiagramPane.getHorizontalScrollBar(), lastSelected.getX(), lastSelected.getWidth());
    }

    private void adjust(JScrollBar scrollBar, int start, int length ) {
        if (scrollBar.getValue() > start || scrollBar.getValue() + scrollBar.getVisibleAmount() < start + length)
            scrollBar.setValue(start - 100);
    }

    private void showContexMenu(int x, int y) {
        DecisionTreeContextMenuProvider builder = new DecisionTreeContextMenuProvider(project, diagram);
        builder.buildContextMenu(lastSelected.getPart()).show(unitPanel, x, y);
    }

    private void reset() {
        if(lastSelected != null) {
            lastSelected.setSelected(false);
            lastSelected = null;
        }

        newModel = null;
        sourcePart = null;
        treeNavigator.clearSelection();

        refresh();
    }

    public void refresh() {
        updateVisual();
        unitPanel.grabFocus();
    }

    private void update(Runnable action) {
        if(action == null)
            return;

        action.run();
        save();
    }

    private void updateVisual() {
        if(lastSelected!=null) {
            PropertyTableModel model = new PropertyTableModel((IPropertySource) lastSelected.getPart().getModel(), this);
            setModel(model);
        }

        int height = unitPanel.getPreferredSize().height;
        innerDiagramPane.getVerticalScrollBar().setMaximum(height);

        int width = unitPanel.getPreferredSize().width;
        innerDiagramPane.getHorizontalScrollBar().setMaximum(width);
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        rebuild();
        save();
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
            reset();
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
            if(lastSelected.getPart().getModel() == diagram)
                return;

            if(lastSelected instanceof Connection)
                return;

            // Drag and drop
            if (lastSelected != null && lastHover != null && lastSelected != lastHover) {
                Point p = e.getPoint();
                Figure underPoint = findFigureAt(p);
                update(updateHover(underPoint, getMoveCommand(underPoint, p), p));
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

                lastSelected.getPart().remove();
                update(deleteCmd);
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
                update(createCommand);
                underPoint.getPart().addChildModel(newModel, underPoint.getInsertionIndex());
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
                update(createConnectionCmd);
                f.getPart().addConnection(newModel);
            }

            gotoNext(ready);
        }
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                gotoNext(ready);
        }
    };

    private InteractionHandle sourceEndpointSelected = new InteractionHandle("sourceEndpointSelected") {
        public void mouseDragged(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(getCommand(f) != null)
                f.getPart().showSourceFeedback();
        }
        public void mouseReleased(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            update(getCommand(f));
            gotoNext(ready);
        }
        private Command getCommand(Figure f) {
            if(f.getPart().getEditPolicy().isSelectableSource(newModel)) {
                Connection connection = ((Endpoint)lastSelected).getParentConnection();
                return f.getPart().getEditPolicy().getReconnectSourceCommand(connection);
            }
            return null;
        }
    };

    private InteractionHandle targetEndpointSelected = new InteractionHandle("targetEndpointSelected") {
        public void mouseDragged(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            if(getCommand(f) != null)
                f.getPart().showTargetFeedback();
        }
        public void mouseReleased(MouseEvent e) {
            Figure f = findFigureAt(e.getPoint());
            update(getCommand(f));
            gotoNext(ready);
        }
        private Command getCommand(Figure f) {
////            if(f.getPart().getEditPolicy().getCreateConnectCommand(newModel, sourcePart) != null)
//            if(f.getPart().getEditPolicy().isSelectableTarget(newModel)) {
//                Connection connection = ((Endpoint)lastSelected).getParentConnection();
//                return f.getPart().getEditPolicy().getReconnectTargetCommand(connection);
//            }
            return null;
        }
    };
    private InteractionHandle curHandle = ready;
}