package com.xrosstools.xdecision.editor.model.definition;

import static java.util.Arrays.asList;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;

public class ListType extends CollectionType {
    private MethodDefinition indexOf;
    private MethodDefinition lastIndexOf;
    private MethodDefinition get;
    
    public ListType(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.LIST);

        add(indexOf = new MethodDefinition(diagram, "indexOf", DataType.NUMBER_TYPE, asList(new ParameterDefinition(diagram, VALUE, getValueType()))));
        add(lastIndexOf = new MethodDefinition(diagram, "lastIndexOf", DataType.NUMBER_TYPE, asList(new ParameterDefinition(diagram, VALUE, getValueType()))));
        add(get = new MethodDefinition(diagram, "get", getValueType(), asList(new ParameterDefinition(diagram, VALUE, DataType.NUMBER_TYPE))));
    }

    @Override
    public void setValueType(DataType valueType) {
        indexOf.findParameterByName(VALUE).setType(valueType);
        lastIndexOf.findParameterByName(VALUE).setType(valueType);
        get.setType(valueType);

        super.setValueType(valueType);
    }    
}
