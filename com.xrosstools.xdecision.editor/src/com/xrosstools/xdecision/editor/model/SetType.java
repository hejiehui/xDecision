package com.xrosstools.xdecision.editor.model;

public class SetType extends CollectionType {
    //TODO shall we support toArray[]? If you need it, just contact me
    public SetType(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.SET);
    }
}
