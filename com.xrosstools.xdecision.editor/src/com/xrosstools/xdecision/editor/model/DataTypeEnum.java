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
    
    public static boolean isPredefined(DataTypeEnum type) {
        return !(type == USER_DEFINED || type == ENUM);
    }
    
    public static String[] getAllNames() {
        return new String[] {STRING.name, NUMBER.name, BOOLEAN.name, DATE.name, ARRAY.name, LIST.name, SET.name, MAP.name};
    }

    public static String[] getKeyTypeNames() {
        return new String[] {STRING.name, NUMBER.name};
    }

    public static String[] getValueTypeNames() {
        return new String[] {STRING.name, NUMBER.name, BOOLEAN.name, DATE.name};
    }

    public static DataTypeEnum findByName(String name) {
        for(DataTypeEnum e: DataTypeEnum.values())
            if(e.getName().equals(name))
                return e;
        return null;
    }
}
