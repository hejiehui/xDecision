package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.InputTextCommandAction;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.actions.CommandAction;
import com.xrosstools.idea.gef.parts.GraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.commands.expression.*;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.*;
import com.xrosstools.xdecision.idea.editor.model.expression.*;
import com.xrosstools.xdecision.idea.editor.parts.expression.BaseExpressionPart;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static com.xrosstools.idea.gef.ContextMenuProvider.*;

public class ExpressionContextMenuProvider implements DecisionTreeMessages {
    private static final String DIALOG = "...";
    private Project project;
    private DecisionTreeDiagram diagram;

    public ExpressionContextMenuProvider(Project project, DecisionTreeDiagram diagram, PropertyChangeListener listener) {
        this.project = project;
        this.diagram = diagram;
    }
    
    public void buildContextMenu(JPopupMenu menu, BaseExpressionPart part) {
        ExpressionDefinition exp = (ExpressionDefinition)part.getModel();
        if(exp instanceof OperatorExpression) {
            createOperatorMenu(menu, (OperatorExpression)exp);
            return;
        }

        if(exp instanceof NumberExpression) {
            createOperatorMenu(menu, part);
            menu.addSeparator();
        }
        
        if(exp instanceof ExtensibleExpression)
            addAll(menu, createChildMenu((GraphicalEditPart) part.getParent(), part, true));
        else
            changeToUserDefinedMenu(menu, part);

        addSeparator(menu);
        
        if(!(exp instanceof NumberExpression))
            changeToTypeMenu(menu, part, DataType.NUMBER_TYPE);
        
        if(!(exp instanceof StringExpression)) {
            changeToTypeMenu(menu, part, DataType.STRING_TYPE);
        }
        
        if(!(exp instanceof NumberExpression || exp instanceof StringExpression)) {
            menu.addSeparator();
            createOperatorMenu(menu, part);
        }

        menu.addSeparator();

        wrapBracketOperatorMenu(menu, part);
        addRemoveMenu(menu, part);
    }
    
    private void createOperatorMenu(JPopupMenu menu, OperatorExpression opExp) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), op == opExp.getOperator(), new ChangeOperatorCommand(opExp, op));
        }
    }

    private List<JMenuItem> createChildMenu(GraphicalEditPart parentPart, GraphicalEditPart part, boolean extendChildren) {
        List<JMenuItem> menuItems = new ArrayList<>();
        if(parentPart.getModel() instanceof VariableExpression) {
            // It is a factor and can be replaced by other factors or constants
            NamedElement refrenceElement = ((VariableExpression)parentPart.getModel()).getReferenceElement();
            
            DataType parentType = DataType.getType(refrenceElement);
            
            if(parentType == null)
                return menuItems;

            if(parentType instanceof ArrayType) {
                menuItems.add(changeToArray(parentPart, part));
            }
                
            if(parentType instanceof EnumType) {
                menuItems.addAll(buildReplacementMenu(((EnumType) parentType).getValues(), parentPart, part, extendChildren));
                menuItems.add(separator());
            }

            menuItems.addAll(buildReplacementMenu(parentType.getFields(), parentPart, part, extendChildren));
            menuItems.add(separator());
            menuItems.addAll(buildReplacementMenu(parentType.getMethods(), parentPart, part, extendChildren));
        } else {
            // It is a factor and can be replaced by other factors or constants
            menuItems.addAll(buildReplacementMenu(diagram.getFactors(), parentPart, part, extendChildren));
            menuItems.add(separator());
            menuItems.addAll(buildReplacementMenu(diagram.getUserDefinedConstants(), parentPart, part, extendChildren));
            menuItems.add(separator());
            menuItems.addAll(buildReplacementMenu(diagram.getUserDefinedEnums(), parentPart, part, extendChildren));
        }

        menuItems.add(separator());
        return menuItems;
    }

    private List<JMenuItem> buildReplacementMenu(NamedElementContainer<?> container, GraphicalEditPart parentPart, GraphicalEditPart part, boolean extendChildren) {
        List<JMenuItem> menuItems = new ArrayList<>();
        for(NamedElement element: container.getElements()) {
            ExpressionDefinition exp = element instanceof MethodDefinition ? new MethodExpression((MethodDefinition)element): new VariableExpression(element);
            menuItems.add(createIdMenu(element, parentPart, part, extendChildren, exp));
        }
        return menuItems;
    }
    
    private JMenuItem createIdMenu(NamedElement element, GraphicalEditPart parentPart, GraphicalEditPart part, boolean extendChildren, ExpressionDefinition childExp) {
        String definitionId = element instanceof MethodDefinition ? element.getName() + "()" : element.getName();
        
        ExtensibleExpression model = part == null ? null: (ExtensibleExpression)part.getModel();
        boolean selected = model != null && !(model instanceof ElementExpression) && element == ((VariableExpression) model).getReferenceElement();
        
        if(selected && extendChildren) {
            return createItem(definitionId, createChildMenu(part, findChild(part, model.getChildExpression()), false));
        } else
            return createItem(definitionId, selected, new ChangeChildCommand(parentPart.getModel(), model, childExp));
    }

    private void add(JPopupMenu menu, String text, boolean checked, Command command) {
        menu.add(createItem(new CommandAction(text, checked, command)));
    }

    private GraphicalEditPart findChild(GraphicalEditPart parentPart, Object childModel) {
        if(childModel == null)
            return null;

        for(Object childPartObj: parentPart.getChildren()) {
            GraphicalEditPart childPart = (GraphicalEditPart)childPartObj;
            if(childPart.getModel() == childModel)
                return childPart;
        }

        return null;
    }
    
    private void createOperatorMenu(JPopupMenu menu, GraphicalEditPart expPart) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), false, new AddOperatorCommand(expPart, op));
        }
    }
    
    private void wrapBracketOperatorMenu(JPopupMenu menu, GraphicalEditPart expPart) {
        expPart = AddOperatorCommand.findTopExpressionPart(expPart);
        ExpressionDefinition exp = (ExpressionDefinition)expPart.getModel();
        add(menu, "(...)", false, new ChangeChildCommand(expPart.getParent().getModel(), exp, new BracktExpression().setInnerExpression(exp)));        
    }
    
    private void changeToUserDefinedMenu(JPopupMenu menu, GraphicalEditPart expPart) {
        changeToElementMenu(menu, expPart, diagram.getFactors());
        addSeparator(menu);

        changeToElementMenu(menu, expPart, diagram.getUserDefinedConstants());
        addSeparator(menu);

        changeToElementMenu(menu, expPart, diagram.getUserDefinedEnums());
    }

    private void changeToTypeMenu(JPopupMenu menu, GraphicalEditPart expPart, DataType type) {
        menu.add(createItem(new InputTextCommandAction(project,type.getName() + DIALOG, type.getName(), "", new CreateExpressionCommand(expPart, type))));
    }

    private JMenuItem changeToArray(GraphicalEditPart parentPart, GraphicalEditPart part) {
        VariableExpression exp = (VariableExpression)parentPart.getModel();
        boolean selected = part != null && part.getModel() instanceof ElementExpression;
        Object model = part == null ? null : part.getModel();
        return createItem("[index]", selected, new ChangeChildCommand(exp, (ExpressionDefinition)model, new ElementExpression()));
    }

    private <T extends NamedElement> void changeToElementMenu(JPopupMenu menu, GraphicalEditPart expPart, NamedElementContainer<T> container) {
        Object parentModel = expPart.getParent().getModel();
        ExpressionDefinition toBeReplaced = (ExpressionDefinition)expPart.getModel();
        for(NamedElement field: container.getElements())
            menu.add(createItem(new CommandAction(field.getName(), false, new ChangeChildCommand(parentModel, toBeReplaced, new VariableExpression(field)))));
    }

    private void addRemoveMenu(JPopupMenu menu, GraphicalEditPart expPart) {
        menu.add(createItem(new CommandAction(String.format(REMOVE_MSG, expPart.getModel().toString()), false, new RemoveExpressionCommand(expPart))));
    }
}
