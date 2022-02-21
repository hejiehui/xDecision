package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import static com.xrosstools.xdecision.editor.model.DataType.*;

public enum DataTypeEnum implements PropertyConstants {
    STRING("String"),

    NUMBER("Number"),

    BOOLEAN("Boolean"),

    DATE("Date"),

    ARRAY("Array"),

    COLLECTION("Collection"),
    
    LIST("List"),

    SET("Set"),

    MAP("Map"),

    ENUM("Enum"),
    
    USER_DEFINED("User defined type");
    
    private static final NamedElementContainer<DataType> CONSTANT_TYPES = new NamedElementContainer<DataType>("Constant Types", NamedElementTypeEnum.DATA_TYPE, asList(STRING_TYPE, NUMBER_TYPE, BOOLEAN_TYPE, DATE_TYPE));
    
    private static final NamedElementContainer<DataType> PREDEFINED_TYPES = new NamedElementContainer<DataType>("Predefined Types", NamedElementTypeEnum.DATA_TYPE, asList(STRING_TYPE, NUMBER_TYPE, BOOLEAN_TYPE, DATE_TYPE));
    
    private DataTypeEnum(String name) {
        this.name = name;
    }

    private String name;
    
    public String getName() {
        return name;
    }

    public DataType createDataType() {
        switch(this) {
        case STRING:
            return DataType.STRING_TYPE;
        case NUMBER:
            return DataType.NUMBER_TYPE;
        case BOOLEAN:
            return DataType.BOOLEAN_TYPE;
        case DATE:
            return DataType.DATE_TYPE;
        case ARRAY:
            return new DataTypeArray();
        case COLLECTION:
            return new DataTypeCollection();
        case LIST:
            return new DataTypeList();
        case SET:
            return new DataTypeSet();
        case MAP:
            return new DataTypeMap();
        case ENUM:
            return null;
        default:
            return null;
        }
    }
    
    @Deprecated
    public static List<DataType> getAllTypes() {
        List<DataType> names = new ArrayList<DataType>();
        names.addAll(PREDEFINED_TYPES.getElements());
        return names;
    }
    
    public static boolean isUserDefined(DataTypeEnum type) {
        return type == USER_DEFINED || type == ENUM;
    }
}
