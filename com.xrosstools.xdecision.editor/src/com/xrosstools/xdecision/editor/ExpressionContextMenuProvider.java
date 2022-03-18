package com.xrosstools.xdecision.editor;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.expression.AddOperatorCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeOperatorCommand;
import com.xrosstools.xdecision.editor.commands.expression.CreateExpressionCommand;
import com.xrosstools.xdecision.editor.commands.expression.RemoveExpressionCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedType;
import com.xrosstools.xdecision.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.Identifier;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;
import com.xrosstools.xdecision.editor.model.expression.NumberExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorEnum;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;
import com.xrosstools.xdecision.editor.model.expression.PlaceholderExpression;
import com.xrosstools.xdecision.editor.model.expression.StringExpression;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;
import com.xrosstools.xdecision.editor.parts.expression.BaseExpressionPart;

public class ExpressionContextMenuProvider {
    private static final String DIALOG = "...";
    private DecisionTreeDiagramEditor editor;
    
    public ExpressionContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, BaseExpressionPart expPart) {
        ExpressionDefinition exp = (ExpressionDefinition)expPart.getModel();
        //TODO all of the following can be optimized
        if(exp instanceof OperatorExpression)
            createOperatorMenu(menu, (OperatorExpression)exp);
        else if(exp instanceof ExtensibleExpression)
            createExtensibleExpressionMenu(menu, expPart);
        else if(exp instanceof PlaceholderExpression)
            createPlaceholderExpressionMenu(menu, expPart);
        else if(exp instanceof NumberExpression)
            createNumberMenu(menu, expPart);
        else if(exp instanceof StringExpression)
            createStringMenu(menu, expPart);
        
        addRemoveMenu(menu, expPart);
    }
    
    //Only field or method expression goes here
    public void createExtensibleExpressionMenu(IMenuManager menu, EditPart part) {
        createChildMenu(menu, part.getParent(), part, true);
        menu.add(new Separator());
        addChangeToNumberMenu(menu, part);
        addChangeToStringMenu(menu, part);
        addChangeToConstMenu(menu, part);
        addChangeToEnumMenu(menu, part);
        menu.add(new Separator());
        createOperatorMenu(menu, part);
        menu.add(new Separator());
        wrapBracketOperatorMenu(menu, part); 
    }

    private void createChildMenu(IMenuManager menu, EditPart parentPart, EditPart part, boolean extendChildren) {
        if(parentPart.getModel() instanceof VariableExpression) {
            // It is a factor and can be replaced by other factors or constants
            DataType parentType = ((VariableExpression)parentPart.getModel()).getReferenceType().getType();
            if(parentType == null)
                return;

            buildReplacementMenu(menu, parentType.getFields(), parentPart, part, extendChildren);
            menu.add(new Separator());
            buildReplacementMenu(menu, parentType.getMethods(), parentPart, part, extendChildren);
        } else {
            // It is a factor and can be replaced by other factors or constants
            buildReplacementMenu(menu, editor.getModel().getFactors(), parentPart, part, extendChildren);
            menu.add(new Separator());
            buildReplacementMenu(menu, editor.getModel().getUserDefinedConstants(), parentPart, part, extendChildren);
        }
        
        menu.add(new Separator());
        
//        createIdMenu(menu, "[index]", method.getType(), parentPart, childModel, extendChildren, new MethodExpression(method));
    }
    
    private void buildReplacementMenu(IMenuManager menu, NamedElementContainer<?> container, EditPart parentPart, EditPart part, boolean extendChildren) {
        for(NamedElement element: container.getElements()) {
            ExpressionDefinition exp = element instanceof MethodDefinition ? new MethodExpression((MethodDefinition)element): new VariableExpression((NamedType)element);
            createIdMenu(menu, (NamedType)element, parentPart, part, extendChildren, exp);
        }
    }
    
    private void createIdMenu(IMenuManager menu, NamedType element, EditPart parentPart, EditPart part, boolean extendChildren, ExpressionDefinition childExp) {
        String definitionId = element instanceof MethodDefinition ? element.getName() + "()" : element.getName();
        
        Identifier childModel = part == null ? null: (Identifier)part.getModel();
        boolean selected = childModel == null ? false : element == ((VariableExpression)childModel).getReferenceType();
        
        if(selected && extendChildren) {
            MenuManager subMenu = new MenuManager(definitionId);
            createChildMenu(subMenu, part, findChild(parentPart, childModel), false);
            menu.add(subMenu);
        } else
            add(menu, definitionId, selected, new ChangeChildCommand(parentPart.getModel(), (ExpressionDefinition)childModel, childExp));
    }

    private void add(IMenuManager menu, String text, boolean checked, Command command) {
        menu.add(new CommandAction(editor, text, checked, command));
    }

    private EditPart findChild(EditPart parentPart, Object child) {
        if(child == null)
            return null;

        for(Object childPartObj: parentPart.getChildren()) {
            EditPart childPart = (EditPart)childPartObj;
            if(childPart.getModel() == child)
                return childPart;
        }

        return null;
    }
    
    private DecisionTreeDiagram getDiagram() {
        return (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
    }

    private void createOperatorMenu(IMenuManager menu, OperatorExpression opExp) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), op == opExp.getOperator(), new ChangeOperatorCommand(opExp, op));
        }
    }
    
    private void createNumberMenu(IMenuManager menu, EditPart expPart) {
        createOperatorMenu(menu, expPart);
        menu.add(new Separator());
        changeToFactorMenu(menu, expPart);

        menu.add(new Separator());
        addChangeToStringMenu(menu, expPart);
        wrapBracketOperatorMenu(menu, expPart);
    }

    private void createStringMenu(IMenuManager menu, EditPart expPart) {
        changeToFactorMenu(menu, expPart);
        menu.add(new Separator());
        addChangeToNumberMenu(menu, expPart);
        wrapBracketOperatorMenu(menu, expPart);
    }
    private void createOperatorMenu(IMenuManager menu, EditPart expPart) {
        for(OperatorEnum op: OperatorEnum.values()) {
            // TODO revise checked
            add(menu, op.getOperator(), false, new AddOperatorCommand(expPart, op));
        }
    }
    
    private void createPlaceholderExpressionMenu(IMenuManager menu, BaseExpressionPart expPart) {
        addChangeToNumberMenu(menu, expPart);
        addChangeToStringMenu(menu, expPart);

        menu.add(new Separator());
        
        changeToFactorMenu(menu, expPart);

        menu.add(new Separator());
        
        //add bracket
        wrapBracketOperatorMenu(menu, expPart);
    }
    
    private void wrapBracketOperatorMenu(IMenuManager menu, EditPart expPart) {
        expPart = AddOperatorCommand.findTopExpressionPart(expPart);
        ExpressionDefinition exp = (ExpressionDefinition)expPart.getModel();
        add(menu, "(...)", false, new ChangeChildCommand(expPart.getParent().getModel(), exp, new BracktExpression().setEnclosedExpression(exp)));        
    }
    
    private void changeToFactorMenu(IMenuManager menu, EditPart expPart) {
        ExpressionDefinition toBeReplaced = (ExpressionDefinition)expPart.getModel();
        // select factors
        for(NamedElement field: getDiagram().getFactors().getElements())
            menu.add(new CommandAction(editor, field.getName(), false, new ChangeChildCommand(expPart.getParent().getModel(), toBeReplaced, new VariableExpression(field.getName()))));
    }
    
    private void addChangeToNumberMenu(IMenuManager menu, EditPart expPart) {
        menu.add(new InputTextCommandAction(editor, DataType.NUMBER_TYPE.getName() + DIALOG, DataType.NUMBER_TYPE.getName(), "0", new CreateExpressionCommand(expPart, DataType.NUMBER_TYPE.getName())));        
    }

    private void addChangeToStringMenu(IMenuManager menu, EditPart expPart) {
        menu.add(new InputTextCommandAction(editor, DataType.STRING_TYPE.getName() + DIALOG, DataType.STRING_TYPE.getName(), "", new CreateExpressionCommand(expPart, DataType.STRING_TYPE.getName())));        
    }

    //TODO
    private void addChangeToConstMenu(IMenuManager menu, EditPart expPart) {
//        menu.add(new InputTextCommandAction(editor, DataType.STRING, DataType.STRING, "", new CreateExpressionCommand(expPart, DataType.STRING)));        
    }

    //TODO
    private void addChangeToEnumMenu(IMenuManager menu, EditPart expPart) {
//        menu.add(new InputTextCommandAction(editor, DataType.STRING, DataType.STRING, "", new CreateExpressionCommand(expPart, DataType.STRING)));        
    }

    private void addRemoveMenu(IMenuManager menu, EditPart expPart) {
        menu.add(new CommandAction(editor, String.format(DecisionTreeMessages.REMOVE_MSG, expPart.getModel().toString()), false, new RemoveExpressionCommand(expPart)));        
    }
}
