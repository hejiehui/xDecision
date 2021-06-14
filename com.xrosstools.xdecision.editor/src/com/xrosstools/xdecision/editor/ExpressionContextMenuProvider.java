package com.xrosstools.xdecision.editor;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.expression.AddOperatorCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeOperatorCommand;
import com.xrosstools.xdecision.editor.commands.expression.CreateExpressionCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.Identifier;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;
import com.xrosstools.xdecision.editor.model.expression.NumberExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorEnum;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;
import com.xrosstools.xdecision.editor.model.expression.PlaceholderExpression;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;
import com.xrosstools.xdecision.editor.parts.expression.BaseExpressionPart;

public class ExpressionContextMenuProvider {
    private DecisionTreeDiagramEditor editor;
    
    public ExpressionContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, BaseExpressionPart expPart) {
        ExpressionDefinition exp = (ExpressionDefinition)expPart.getModel();
        
        if(exp instanceof OperatorExpression)
            createOperatorMenu(menu, (OperatorExpression)exp);
        else if(exp instanceof ExtensibleExpression)
            createExtensibleExpressionMenu(menu, expPart);
        else if(exp instanceof PlaceholderExpression)
            createPlaceholderExpressionMenu(menu, expPart);
        else if(exp instanceof NumberExpression)
            createOperatorMenu(menu, expPart);
    }
    
    //Only field or method expression goes here
    public void createExtensibleExpressionMenu(IMenuManager menu, EditPart part) {
        createChildMenu(menu, part.getParent(), part, true);
    }

    private void createChildMenu(IMenuManager menu, EditPart parentPart, EditPart part, boolean extendChildren) {
        DataType parentType = findDataType(parentPart);

        // select field
        for(FieldDefinition field: parentType.getFields())
            createIdMenu(menu, field.getIdentifier(), field.getTypeName(), parentPart, part, extendChildren, new VariableExpression(field.getName()));

        menu.add(new Separator());
        
        // select method
        //TODO static and instance method
        for(MethodDefinition method: parentType.getMethods())
            //TODO fix method identification
            createIdMenu(menu, method.getIdentifier(), method.getTypeName(), parentPart, part, extendChildren, new MethodExpression(method));
        
        if(extendChildren) {
            menu.add(new Separator());
            createOperatorMenu(menu, part);
        }

        menu.add(new Separator());
        
//        createIdMenu(menu, "[index]", method.getType(), parentPart, childModel, extendChildren, new MethodExpression(method));
    }
    
    private void createIdMenu(IMenuManager menu, String definitionId, String defType, EditPart parentPart, EditPart part, boolean extendChildren, ExpressionDefinition childExp) {
        Identifier childModel = part == null ? null: (Identifier)part.getModel();
        boolean selected = childModel == null ? false : definitionId.equals(childModel.getIdentifier());
        
        if(selected && extendChildren) {
            MenuManager subMenu = new MenuManager(definitionId);
            createChildMenu(subMenu, part, findChild(parentPart, childModel), false);
            menu.add(subMenu);
        } else
            menu.add(new CommandAction(editor, definitionId, selected, new ChangeChildCommand(parentPart.getModel(), (ExpressionDefinition)childModel, childExp)));
    }

    private void add(IMenuManager menu, String text, boolean checked, Command command) {
        menu.add(new CommandAction(editor, text, checked, command));
    }

    private EditPart findChild(EditPart parentPart, Object child) {
        for(Object childPartObj: parentPart.getChildren()) {
            EditPart childPart = (EditPart)childPartObj;
            if(childPart.getModel() == child)
                return childPart;
        }
        return null;
    }
    
    private DataType findDataType(EditPart part) {
        Object extExp = part.getModel();
        
        //we are at top level, it must be VariableExpression and represents Factor
        if(!(part.getModel() instanceof ExtensibleExpression)) {
            return getDiagram().getType();
        }

        DataType parentUDT = findDataType(part.getParent());
        VariableExpression exp = (VariableExpression)extExp;
        
        if(parentUDT == DataType.NOT_MATCHED) {
            exp.setValid(false);
            return DataType.NOT_MATCHED;
        }

        FieldDefinition fd;
        if(exp instanceof MethodExpression)
            fd = parentUDT.findMethod(exp.getName());
        else
            fd = parentUDT.findField(exp.getName());
        
        exp.setValid(fd != null);
        
        if(fd == null)
            return DataType.NOT_MATCHED;

        return getDiagram().findDataType(fd.getTypeName());
    }
    
    private DecisionTreeDiagram getDiagram() {
        return (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
    }

    private void createOperatorMenu(IMenuManager menu, OperatorExpression opExp) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), op == opExp.getOperator(), new ChangeOperatorCommand(opExp, op));
        }
    }
    
    private void createOperatorMenu(IMenuManager menu, EditPart expPart) {
        for(OperatorEnum op: OperatorEnum.values()) {
            // TODO revise checked
            add(menu, op.getOperator(), false, new AddOperatorCommand(expPart, op));
        }
    }
    
    private void createPlaceholderExpressionMenu(IMenuManager menu, BaseExpressionPart expPart) {
        Object parentModel = expPart.getParent().getModel();
        ExpressionDefinition placeholder = (ExpressionDefinition)expPart.getModel();
        menu.add(new InputTextCommandAction(editor, DataType.NUMBER, DataType.NUMBER, "0", new CreateExpressionCommand(parentModel, placeholder, DataType.NUMBER)));
        menu.add(new InputTextCommandAction(editor, DataType.STRING, DataType.STRING, "", new CreateExpressionCommand(parentModel, placeholder, DataType.STRING)));

        menu.add(new Separator());
        
        // select factors
        for(FieldDefinition field: getDiagram().getType().getFields())
            menu.add(new CommandAction(editor, field.getIdentifier(), false, new ChangeChildCommand(expPart.getParent().getModel(), placeholder, new VariableExpression(field.getName()))));

    }
}
