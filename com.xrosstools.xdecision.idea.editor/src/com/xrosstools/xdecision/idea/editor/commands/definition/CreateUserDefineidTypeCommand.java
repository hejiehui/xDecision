package com.xrosstools.xdecision.idea.editor.commands.definition;

import com.intellij.psi.*;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.commands.InputTextCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.*;

public class CreateUserDefineidTypeCommand extends Command {
    private DecisionTreeDiagram diagram;
    private PsiClass selectedClass;
    private DataType newType;
    
    public CreateUserDefineidTypeCommand(DecisionTreeDiagram diagram, PsiClass selectedClass){
        this.diagram = diagram;
        this.selectedClass = selectedClass;
    }

    public boolean canExecute() {
        return !diagram.getUserDefinedTypes().containsName(selectedClass.getName());
    }
    
    public void execute() {
        if(selectedClass.isEnum())
            newType = new EnumType(selectedClass.getName());
        else
            newType = new DataType(selectedClass.getName());

        for(PsiField f:selectedClass.getFields()) {
            if(f instanceof PsiEnumConstant)
                ((EnumType)newType).getValues().add(new EnumValue(f.getName()));
            else
                newType.add(new FieldDefinition(diagram, f.getName(), diagram.findDataType(f.getType().getCanonicalText(false))));
        }

        for(PsiMethod m:selectedClass.getMethods()) {
            if(m.isConstructor()) continue;

            if("void".equals(m.getReturnType().getPresentableText())) continue;

            MethodDefinition mdef = new MethodDefinition(diagram, m.getName(), diagram.findDataType(m.getReturnType().getPresentableText()));

            for(PsiTypeParameter p: m.getTypeParameters()) {
                mdef.getParameters().add(new ParameterDefinition(diagram, p.getName(), diagram.findDataType(p.getQualifiedName())));
            }
            newType.add(mdef);
        }

        redo();
    }

    public String getLabel() {
        return "Create new decision";
    }

    public void redo() {
        if(selectedClass.isEnum())
            diagram.getUserDefinedEnums().add((EnumType)newType);
        else
            diagram.getUserDefinedTypes().add(newType);
    }

    public void undo() {
        diagram.getUserDefinedTypes().remove(newType);
    }
}
