package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.ChangeElementNameCommand;
import com.xrosstools.xdecision.editor.commands.DeleteElementCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedElementTypeEnum;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.NamedElementTreePart;

public class NamedElementContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    protected DecisionTreeDiagramEditor editor;
    
    public NamedElementContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, NamedElementTreePart namedTypePart) {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();

        NamedElement element = (NamedElement)namedTypePart.getModel();
        NamedElementTypeEnum type = ((NamedElementContainer<?>)namedTypePart.getParent().getModel()).getElementType();
        String typeName = type.getTypeName();
        List<DataType> types = type.getQualifiedDataTypes(diagram);
        
        menu.add(new InputTextCommandAction(editor, String.format(CHANGE_NAME_MSG, typeName), "New Name", element.getName(), new ChangeElementNameCommand(element)));
        menu.add(new Separator());
        menu.add(new CommandAction(editor, String.format(REMOVE_MSG, element.getName()), false, new DeleteElementCommand((NamedElementContainer<NamedElement>)namedTypePart.getParent().getModel(), element)));
    }
}
