package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractPanelContentProvider;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xdecision.idea.editor.actions.*;
import com.xrosstools.xdecision.idea.editor.layout.LayoutAlgorithm;
import com.xrosstools.xdecision.idea.editor.menus.DecisionTreeContextMenuProvider;
import com.xrosstools.xdecision.idea.editor.menus.DecisionTreeOutlineContextMenuProvider;
import com.xrosstools.xdecision.idea.editor.model.*;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementTypeEnum;
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
        return new DecisionTreeContextMenuProvider(project, diagram);
    }

    @Override
    public ContextMenuProvider getOutlineContextMenuProvider() {
        return new DecisionTreeOutlineContextMenuProvider(project, diagram);
    }

    @Override
    public void buildPalette(JPanel palette) {
        palette.add(createConnectionButton());
        palette.add(createNodeButton());

        palette.add(createPaletteButton(new DecisionTreeCreateDecisionAction(project, diagram), CREATE_NEW_DECISION, CREATE_NEW_DECISION_MSG));
        palette.add(createPaletteButton(new DecisionTreeCreateFactorAction(project, diagram), CREATE_NEW_FACTOR, CREATE_NEW_FACTOR_MSG));

        palette.add(createPaletteButton(new ImportDataTypeAction(project, diagram), IMPORT_TYPE, IMPORT_TYPE_MSG));
        palette.add(createPaletteButton(new DecisionTreeCodeGenAction(project, virtualFile, diagram, false), GEN_TEST_CODE_DIALOG, GEN_TEST_CODE_MSG));
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
    public ActionGroup createToolbar() {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, true, 1), ALIGN_BOTTOM, ALIGN_BOTTOM_MSG));
        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, true, 0.5f), ALIGN_MIDDLE, ALIGN_MIDDLE_MSG));
        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, true, 0), ALIGN_TOP, ALIGN_TOP_MSG));
        actionGroup.addSeparator();

        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, false, 0), ALIGN_LEFT, ALIGN_LEFT_MSG));
        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, false, 0.5f), ALIGN_CENTER, ALIGN_CENTER_MSG));
        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, false, 1), ALIGN_RIGHT, ALIGN_RIGHT_MSG));

        return actionGroup;
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
