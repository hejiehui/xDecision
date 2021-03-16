package com.xrosstools.xdecision.editor;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeFactorExpCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeOperatorCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.FactorExpression;
import com.xrosstools.xdecision.editor.model.expression.FieldExpression;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorEnum;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;
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
        else if(exp instanceof FactorExpression)
            createFactorMenu(menu, expPart);
        else if(exp instanceof ExtensibleExpression)
            createExtensibleExpressionMenu(menu, expPart);
    }
    
    public void createFactorMenu(IMenuManager menu, EditPart part) {
        DecisionTreeDiagram diagram = getDiagram();
        FactorExpression factorExp = (FactorExpression)part.getModel();
      
        int i = 0;
        for(DecisionTreeFactor factor: diagram.getFactors()) {
            boolean selected = factorExp.getFactorId() == i;
            if(factor.getType().isCustomized() && selected) {
                MenuManager subMenu = new MenuManager(factor.getFactorName());
                createChildMenu(subMenu, part, false);
                menu.add(subMenu);
            } else
                menu.add(new CommandAction(editor, factor.getFactorName(), selected, new ChangeFactorExpCommand(factorExp, i)));
            i++;
        }

        //Select function
//        selectFunctionMeun(menu, diagram, node);
    }

    //Only field or method expression goes here
    public void createExtensibleExpressionMenu(IMenuManager menu, EditPart part) {
        createChildMenu(menu, part.getParent(), true);
    }

    private void createChildMenu(IMenuManager menu, EditPart parentPart, boolean extendChildren) {
        selectFieldMenu(menu, parentPart, extendChildren);
        
        menu.add(new Separator());
        
        selectMethodMenu(menu, parentPart, extendChildren);
    }
    
    private void selectFieldMenu(IMenuManager menu, EditPart parentPart, boolean extendChildren) {
        UserDefinedType parentType = findDataType(parentPart);
        Object parentModel = parentPart.getModel();
        Object childModel = ChangeChildCommand.getChild(parentModel);
        
        // select field
        for(FieldDefinition field: parentType.getFields()) {
            boolean selected = false; 
            if(childModel instanceof VariableExpression) {
                VariableExpression fieldExp = (VariableExpression)childModel;
                selected = fieldExp.getName().equals(field.getName());
            }
            
            if(selected && extendChildren && field.getType().isCustomized()) {
                MenuManager subMenu = new MenuManager(field.getName());
                createChildMenu(subMenu, findChild(parentPart, childModel), false);
                menu.add(subMenu);
            } else
                menu.add(new CommandAction(editor, field.getName(), selected, new ChangeChildCommand(parentModel, new VariableExpression(field.getName()))));
        }
    }
    
    private void selectMethodMenu(IMenuManager menu, EditPart parentPart, boolean extendChildren) {
        UserDefinedType parentType = findDataType(parentPart);
        Object parentModel = parentPart.getModel();
        Object childModel = ChangeChildCommand.getChild(parentModel);
        
        // select method
        for(MethodDefinition method: parentType.getMethods()) {
            boolean selected = false; 
            if(childModel instanceof MethodExpression) {
                MethodExpression methodChild = (MethodExpression)childModel;
                //TODO fix method identification
                selected = methodChild.getName().equals(method.getName());
            }
            
            if(selected && extendChildren && method.getType().isCustomized()) {
                MenuManager subMenu = new MenuManager(method.getName());
                createChildMenu(subMenu, findChild(parentPart, childModel), false);
                menu.add(subMenu);
            } else
                menu.add(new CommandAction(editor, method.getName(), selected, new ChangeChildCommand(parentModel, new MethodExpression(method))));
        }
    }

//    private void selectChildMethodMenu(IMenuManager menu, ExtensibleExpression extExp, FactorType type) {
//        for(FactorType type: FactorType.getDisplayTypes()) {
//            if(type.getStaticMethods().length == 0)
//                continue;
//            
//            MenuManager subMenu = new MenuManager(type.toString());
//            for(MethodDefinition mtd: type.getStaticMethods()) {
//                subMenu.add(new CommandAction(editor, mtd.getName(), mtd.getName().equals(node.getFunctionName()), new ChangeFunctionCommand(node, mtd.getName())));
//            }
//            menu.add(subMenu);
//        }
//    }
    
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
    
    private UserDefinedType findDataType(EditPart part) {
        Object extExp = part.getModel();
        
        //we are at top level, it must be VariableExpression and represents Factor
        if(!(part.getModel() instanceof ExtensibleExpression)) {
            return getDiagram().getType();
        }

        UserDefinedType parentUDT = findDataType(part.getParent());
        if(extExp instanceof MethodExpression)
            return getDiagram().findUserDefinedType(parentUDT.findMethod(((MethodExpression)extExp).getName()).getType().getCustomizedType());
        else
            return getDiagram().findUserDefinedType(parentUDT.findField(((VariableExpression)extExp).getName()).getType().getCustomizedType());
    }
    
    private DecisionTreeDiagram getDiagram() {
        return (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
    }

    private void createOperatorMenu(IMenuManager menu, OperatorExpression opExp) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), op == opExp.getOperator(), new ChangeOperatorCommand(opExp, op));
        }
    }    
}
