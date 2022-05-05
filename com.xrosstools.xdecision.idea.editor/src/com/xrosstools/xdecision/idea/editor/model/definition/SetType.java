package com.xrosstools.xdecision.idea.editor.model.definition;


import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class SetType extends CollectionType {
    //TODO shall we support toArray[]? If you need it, just contact me
    public SetType(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.SET);
    }
}
