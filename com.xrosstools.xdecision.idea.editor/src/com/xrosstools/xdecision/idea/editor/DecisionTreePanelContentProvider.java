package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.sun.org.glassfish.external.amx.AMX;
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
    private DecisionTreeContextMenuProvider contextMenuProvider;
    private DecisionTreeOutlineContextMenuProvider outlineContextMenuProvider;

    private DecisionTreeCreateDecisionAction createDecisionAction;
    private DecisionTreeCreateFactorAction createFactorAction;
    private ImportDataTypeAction importDataTypeAction;
    private DecisionTreeCodeGenAction codeGenAction;

    private DecisionTreeLayoutAction alignBottom;
    private DecisionTreeLayoutAction alignMiddle;
    private DecisionTreeLayoutAction alignTop;

    private DecisionTreeLayoutAction alignLeft;
    private DecisionTreeLayoutAction alignCenter;
    private DecisionTreeLayoutAction alignRight;

    public DecisionTreePanelContentProvider(Project project, VirtualFile virtualFile) {
        super(virtualFile);
        this.project = project;
        this.virtualFile = virtualFile;

        contextMenuProvider = new DecisionTreeContextMenuProvider(project);
        outlineContextMenuProvider = new DecisionTreeOutlineContextMenuProvider(project);

        createDecisionAction = new DecisionTreeCreateDecisionAction(project);
        createFactorAction = new DecisionTreeCreateFactorAction(project);
        importDataTypeAction = new ImportDataTypeAction(project);
        codeGenAction = new DecisionTreeCodeGenAction(project, virtualFile);

        alignBottom = new DecisionTreeLayoutAction(true, 1);
        alignMiddle = new DecisionTreeLayoutAction(true, 0.5f);
        alignTop = new DecisionTreeLayoutAction(true, 0);

        alignLeft = new DecisionTreeLayoutAction(false, 0);
        alignCenter = new DecisionTreeLayoutAction(false, 0.5f);
        alignRight = new DecisionTreeLayoutAction(false, 1);
    }

    @Override
    public DecisionTreeDiagram getContent() throws Exception {
        diagram = factory.getFromXML(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
        diagramChanged(diagram);
        return diagram;
    }

    private void diagramChanged(DecisionTreeDiagram diagram) {
        contextMenuProvider.setDiagram(diagram);
        outlineContextMenuProvider.setDiagram(diagram);

        createDecisionAction.setDiagram(diagram);
        createFactorAction.setDiagram(diagram);
        importDataTypeAction.setDiagram(diagram);
        codeGenAction.setDiagram(diagram);

        alignBottom.setDiagram(diagram);
        alignMiddle.setDiagram(diagram);
        alignTop.setDiagram(diagram);

        alignLeft.setDiagram(diagram);
        alignCenter.setDiagram(diagram);
        alignRight.setDiagram(diagram);
    }

    @Override
    public void saveContent() throws Exception {
        String contentStr = XmlHelper.format(factory.convertToXML(diagram));
        virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
    }

    @Override
    public ContextMenuProvider getContextMenuProvider() {
        return contextMenuProvider;
    }

    @Override
    public ContextMenuProvider getOutlineContextMenuProvider() {
        return outlineContextMenuProvider;
    }

    @Override
    public void buildPalette(JPanel palette) {
        palette.add(createConnectionButton());
        palette.add(createNodeButton());

        palette.add(createPaletteButton(createDecisionAction, CREATE_NEW_DECISION, CREATE_NEW_DECISION_MSG));
        palette.add(createPaletteButton(createFactorAction, CREATE_NEW_FACTOR, CREATE_NEW_FACTOR_MSG));

        palette.add(createPaletteButton(importDataTypeAction, IMPORT_TYPE, IMPORT_TYPE_MSG));
        palette.add(createPaletteButton(codeGenAction, GEN_TEST_CODE_DIALOG, GEN_TEST_CODE_MSG));
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
        actionGroup.add(createToolbarAction(alignBottom, ALIGN_BOTTOM, ALIGN_BOTTOM_MSG));
        actionGroup.add(createToolbarAction(alignMiddle, ALIGN_MIDDLE, ALIGN_MIDDLE_MSG));
        actionGroup.add(createToolbarAction(alignTop, ALIGN_TOP, ALIGN_TOP_MSG));
        actionGroup.addSeparator();

        actionGroup.add(createToolbarAction(alignLeft, ALIGN_LEFT, ALIGN_LEFT_MSG));
        actionGroup.add(createToolbarAction(alignCenter, ALIGN_CENTER, ALIGN_CENTER_MSG));
        actionGroup.add(createToolbarAction(alignRight, ALIGN_RIGHT, ALIGN_RIGHT_MSG));

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