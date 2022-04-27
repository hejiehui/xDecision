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
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.definition.ArrayType;
import com.xrosstools.xdecision.editor.model.definition.DataType;
import com.xrosstools.xdecision.editor.model.definition.EnumType;
import com.xrosstools.xdecision.editor.model.definition.MethodDefinition;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;
import com.xrosstools.xdecision.editor.model.expression.NumberExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorEnum;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;
import com.xrosstools.xdecision.editor.model.expression.StringExpression;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;
import com.xrosstools.xdecision.editor.parts.expression.BaseExpressionPart;

public class ExpressionContextMenuProvider {
    private static final String DIALOG = "...";
    private DecisionTreeDiagramEditor editor;
    
    public ExpressionContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, BaseExpressionPart part) {
        ExpressionDefinition exp = (ExpressionDefinition)part.getModel();
        if(exp instanceof OperatorExpression) {
            createOperatorMenu(menu, (OperatorExpression)exp);
            return;
        }

        if(exp instanceof NumberExpression) {
            createOperatorMenu(menu, part);
            menu.add(new Separator());
        }
        
        if(exp instanceof ExtensibleExpression)
            createChildMenu(menu, part.getParent(), part, true);
        else
            changeToUserDefinedMenu(menu, part);

        menu.add(new Separator());
        
        if(!(exp instanceof NumberExpression))
            changeToTypeMenu(menu, part, DataType.NUMBER_TYPE);
        
        if(!(exp instanceof StringExpression)) {
            changeToTypeMenu(menu, part, DataType.STRING_TYPE);
        }
        
        if(!(exp instanceof NumberExpression)) {
            menu.add(new Separator());
            createOperatorMenu(menu, part);
        }

        menu.add(new Separator());

        wrapBracketOperatorMenu(menu, part);
        addRemoveMenu(menu, part);
    }
    
    private void createOperatorMenu(IMenuManager menu, OperatorExpression opExp) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), op == opExp.getOperator(), new ChangeOperatorCommand(opExp, op));
        }
    }

    private void createChildMenu(IMenuManager menu, EditPart parentPart, EditPart part, boolean extendChildren) {
        if(parentPart.getModel() instanceof VariableExpression) {
            // It is a factor and can be replaced by other factors or constants
            NamedElement refrenceElement = ((VariableExpression)parentPart.getModel()).getReferenceElement();
            
            DataType parentType = DataType.getType(refrenceElement);
            
            if(parentType == null)
                return;

            if(parentType instanceof ArrayType) {
                changeToArray(menu, parentPart, part);
            }
                
            if(parentType instanceof EnumType)
                buildReplacementMenu(menu, ((EnumType)parentType).getValues(), parentPart, part, extendChildren);
            
            buildReplacementMenu(menu, parentType.getFields(), parentPart, part, extendChildren);
            menu.add(new Separator());
            buildReplacementMenu(menu, parentType.getMethods(), parentPart, part, extendChildren);
        } else {
            // It is a factor and can be replaced by other factors or constants
            buildReplacementMenu(menu, editor.getModel().getFactors(), parentPart, part, extendChildren);
            menu.add(new Separator());
            buildReplacementMenu(menu, editor.getModel().getUserDefinedConstants(), parentPart, part, extendChildren);
            menu.add(new Separator());
            buildReplacementMenu(menu, editor.getModel().getUserDefinedEnums(), parentPart, part, extendChildren);
        }
        
        menu.add(new Separator());
    }

    private void buildReplacementMenu(IMenuManager menu, NamedElementContainer<?> container, EditPart parentPart, EditPart part, boolean extendChildren) {
        for(NamedElement element: container.getElements()) {
            ExpressionDefinition exp = element instanceof MethodDefinition ? new MethodExpression((MethodDefinition)element): new VariableExpression(element);
            createIdMenu(menu, element, parentPart, part, extendChildren, exp);
        }
    }
    
    private void createIdMenu(IMenuManager menu, NamedElement element, EditPart parentPart, EditPart part, boolean extendChildren, ExpressionDefinition childExp) {
        String definitionId = element instanceof MethodDefinition ? element.getName() + "()" : element.getName();
        
        ExtensibleExpression model = part == null ? null: (ExtensibleExpression)part.getModel();
        boolean selected = model == null || model instanceof ElementExpression ? false : element == ((VariableExpression)model).getReferenceElement();
        
        if(selected && extendChildren) {
            MenuManager subMenu = new MenuManager(definitionId);
            
            createChildMenu(subMenu, part, findChild(part, model.getChildExpression()), false);
            menu.add(subMenu);
        } else
            add(menu, definitionId, selected, new ChangeChildCommand(parentPart.getModel(), (ExpressionDefinition)model, childExp));
    }

    private void add(IMenuManager menu, String text, boolean checked, Command command) {
        menu.add(new CommandAction(editor, text, checked, command));
    }

    private EditPart findChild(EditPart parentPart, Object childModel) {
        if(childModel == null)
            return null;

        for(Object childPartObj: parentPart.getChildren()) {
            EditPart childPart = (EditPart)childPartObj;
            if(childPart.getModel() == childModel)
                return childPart;
        }

        return null;
    }
    
    private void createOperatorMenu(IMenuManager menu, EditPart expPart) {
        for(OperatorEnum op: OperatorEnum.values()) {
            add(menu, op.getOperator(), false, new AddOperatorCommand(expPart, op));
        }
    }
    
    private void wrapBracketOperatorMenu(IMenuManager menu, EditPart expPart) {
        expPart = AddOperatorCommand.findTopExpressionPart(expPart);
        ExpressionDefinition exp = (ExpressionDefinition)expPart.getModel();
        add(menu, "(...)", false, new ChangeChildCommand(expPart.getParent().getModel(), exp, new BracktExpression().setInnerExpression(exp)));        
    }
    
    private void changeToUserDefinedMenu(IMenuManager menu, EditPart expPart) {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        
        changeToElementMenu(menu, expPart, diagram.getFactors());
        menu.add(new Separator());

        changeToElementMenu(menu, expPart, diagram.getUserDefinedConstants());
        menu.add(new Separator());

        changeToElementMenu(menu, expPart, diagram.getUserDefinedEnums());
    }

    private void changeToTypeMenu(IMenuManager menu, EditPart expPart, DataType type) {
        menu.add(new InputTextCommandAction(editor, type.getName() + DIALOG, type.getName(), "", new CreateExpressionCommand(expPart, type)));
    }

    private void changeToArray(IMenuManager menu, EditPart parentPart, EditPart part) {
        VariableExpression exp = (VariableExpression)parentPart.getModel();
        boolean selected = part != null ? part.getModel() instanceof ElementExpression : false;
        Object model = part == null ? null : part.getModel();
        add(menu, "[index]", selected, new ChangeChildCommand(exp, (ExpressionDefinition)model, new ElementExpression()));
    }

    private <T extends NamedElement> void changeToElementMenu(IMenuManager menu, EditPart expPart, NamedElementContainer<T> container) {
        Object parentModel = expPart.getParent().getModel();
        ExpressionDefinition toBeReplaced = (ExpressionDefinition)expPart.getModel();
        for(NamedElement field: container.getElements())
            menu.add(new CommandAction(editor, field.getName(), false, new ChangeChildCommand(parentModel, toBeReplaced, new VariableExpression(field))));
    }

    private void addRemoveMenu(IMenuManager menu, EditPart expPart) {
        menu.add(new CommandAction(editor, String.format(DecisionTreeMessages.REMOVE_MSG, expPart.getModel().toString()), false, new RemoveExpressionCommand(expPart)));        
    }
}
