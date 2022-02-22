package com.xrosstools.xdecision.editor.model;

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
    
    private DataTypeEnum(String name) {
        this.name = name;
    }

    private String name;
    
    public String getName() {
        return name;
    }

    public DataType createDataType(DecisionTreeDiagram diagram) {
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
            return new DataTypeArray(diagram);
        case COLLECTION:
            return new DataTypeCollection(diagram);
        case LIST:
            return new DataTypeList(diagram);
        case SET:
            return new DataTypeSet(diagram);
        case MAP:
            return new DataTypeMap(diagram);
        case ENUM:
            return null;
        default:
            return null;
        }
    }
    
    public static DataTypeEnum findByName(String name) {
        for(DataTypeEnum e: DataTypeEnum.values())
            if(e.getName().equals(name))
                return e;
        
        //TODO shall we just return null?
        return DataTypeEnum.USER_DEFINED;
    }
    
    public static boolean isUserDefined(DataTypeEnum type) {
        return type == USER_DEFINED || type == ENUM;
    }
}
