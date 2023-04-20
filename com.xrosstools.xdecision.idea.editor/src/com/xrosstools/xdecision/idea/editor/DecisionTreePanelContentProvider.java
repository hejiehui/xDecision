package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractPanelContentProvider;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.idea.gef.util.XmlHelper;
import com.xrosstools.xdecision.idea.editor.actions.*;
import com.xrosstools.xdecision.idea.editor.layout.LayoutAlgorithm;
import com.xrosstools.xdecision.idea.editor.menus.DecisionTreeContextMenuProvider;
import com.xrosstools.xdecision.idea.editor.menus.DecisionTreeOutlineContextMenuProvider;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagramFactory;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreePartFactory;
import com.xrosstools.xdecision.idea.editor.treeparts.DecisionTreeTreePartFactory;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;

public class DecisionTreePanelContentProvider extends AbstractPanelContentProvider<DecisionTreeDiagram> implements XdecisionsIcons, DecisionTreeMessages {
    private Project project;
    private VirtualFile virtualFile;
    private DecisionTreeDiagram diagram;
    private LayoutAlgorithm layoutAlgorithm = new LayoutAlgorithm();

    private DecisionTreeDiagramFactory factory = new DecisionTreeDiagramFactory();

    public DecisionTreePanelContentProvider(Project project, VirtualFile virtualFile) {
        super(virtualFile);
        this.project = project;
        this.virtualFile = virtualFile;
    }

    @Override
    public DecisionTreeDiagram getContent() throws Exception {
        diagram = factory.getFromXML(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
        return diagram;
    }

    @Override
    public void saveContent() throws Exception {
        String contentStr = XmlHelper.format(factory.convertToXML(diagram));
        virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
    }

    @Override
    public ContextMenuProvider getContextMenuProvider() {
        return new DecisionTreeContextMenuProvider(project, diagram, this);
    }

    @Override
    public ContextMenuProvider getOutlineContextMenuProvider() {
        return new DecisionTreeOutlineContextMenuProvider(project, diagram, this);
    }

    @Override
    public void buildPalette(JPanel palette) {
        palette.add(createConnectionButton());
        palette.add(createNodeButton());

        palette.add(createPaletteButton(new DecisionTreeCreateDecisionAction(project, diagram, this), CREATE_NEW_DECISION, CREATE_NEW_DECISION_MSG));
        palette.add(createPaletteButton(new DecisionTreeCreateFactorAction(project, diagram, this), CREATE_NEW_FACTOR, CREATE_NEW_FACTOR_MSG));
        //Disable printing to console for now
        //palette.add(createPaletteButton(new DecisionTreeCodeGenAction(virtualFile, diagram, true), GEN_TEST_CODE_CONSOLE, GEN_TEST_CODE_IN_CONSOLE_MSG));
        palette.add(createPaletteButton(new DecisionTreeCodeGenAction(virtualFile, diagram, false), GEN_TEST_CODE_DIALOG, GEN_TEST_CODE_MSG));
    }

    private JButton createConnectionButton() {
        JButton btn = new JButton("Link Node", CONNECTION);
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> createConnection(new DecisionTreeNodeConnection()));
        return btn;
    }

    private JButton createNodeButton() {
        JButton btn = new JButton("Create node", NODE);
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> createModel(new DecisionTreeNode()));
        return btn;
    }

    @Override
    public void buildToolbar(JToolBar toolbar) {
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, true, 1, this), ALIGN_BOTTOM, ALIGN_BOTTOM_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, true, 0.5f, this), ALIGN_MIDDLE, ALIGN_MIDDLE_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, true, 0, this), ALIGN_TOP, ALIGN_TOP_MSG));
        toolbar.addSeparator();

        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, false, 0, this), ALIGN_LEFT, ALIGN_LEFT_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, false, 0.5f, this), ALIGN_CENTER, ALIGN_CENTER_MSG));
        toolbar.add(createToolbarButton(new DecisionTreeLayoutAction(diagram, false, 1, this), ALIGN_RIGHT, ALIGN_RIGHT_MSG));
        toolbar.addSeparator();
    }

    @Override
    public EditPartFactory createEditPartFactory() {
        return new DecisionTreePartFactory();
    }

    @Override
    public EditPartFactory createTreePartFactory() {
        return new DecisionTreeTreePartFactory();
    }

    public void preBuildRoot(){
        layoutAlgorithm.layout(diagram);
    }

    public void postBuildRoot(){
        layoutAlgorithm.layout(diagram);
        getEditorPanel().refreshVisual();

        layoutAlgorithm.layout(diagram);
        getEditorPanel().refreshVisual();
    }

}
