package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public enum DataTypeEnum implements PropertyConstants {
    //TODO do we need common Object type?

    STRING("String"){
        public DataType createDataType() {
            //TODO add more functions for string type, e.g. length()
            return DataType.STRING_TYPE;
        }
    }, 

    NUMBER("Number"){
        public DataType createDataType() {
            return DataType.NUMBER_TYPE;
        }
    },

    BOOLEAN("Boolean"){
        public DataType createDataType() {
            return DataType.BOOLEAN_TYPE;
        }
    },

    DATE("Date"){
        public DataType createDataType() {
            return DataType.DATE_TYPE;
        }
    },

    ARRAY("Array") {
        public DataType createDataType() {
            return new DataTypeArray();
        }

        public String getDataTypeName(DataType type) {
            return type.getType().getName() + "[]";
        }

//        public void setPropertyValue(DataType type, Object propName, Object value){
//            if (PROP_NAME.equals(propName))
//                setName((String)value);
//        }
    },

    LIST("List") {
        
        public DataType createDataType() {
            DataType listType = new DataType(this);
            
            DataType valueType = DEFAULT_TYPE;
            
            listType.setValueType(valueType);
            
            listType.getMethods().addAll(getCommonMethod(valueType));
            
            listType.add(new MethodDefinition(CONTAINS, DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, valueType))));
            listType.add(new MethodDefinition(CONTAINS_ALL, DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, listType))));
            listType.add(new MethodDefinition(INDEX_OF, DataType.NUMBER_TYPE, asList(new FieldDefinition(VALUE, valueType))));
            listType.add(new MethodDefinition(LAST_INDEX_OF, DataType.NUMBER_TYPE, asList(new FieldDefinition(VALUE, valueType))));
            listType.add(new MethodDefinition(GET, valueType, asList(new FieldDefinition(VALUE, DataType.NUMBER_TYPE))));

            return listType;
        }

        private void changeValueType(DataType listType, DataType valueType) {
            listType.setValueType(valueType);
            
            listType.findMethod(CONTAINS).findParameterByName(VALUE).setType(valueType);
            listType.findMethod(CONTAINS_ALL).findParameterByName(VALUE).setType(listType);
            listType.findMethod(INDEX_OF).findParameterByName(VALUE).setType(valueType);
            listType.findMethod(LAST_INDEX_OF).findParameterByName(VALUE).setType(valueType);
            listType.findMethod(GET).setType(valueType);
        }

        public String getDataTypeName(DataType type) {
            return getName() + "<" + type.getValueType().getName() + ">";
        }

        public IPropertyDescriptor[] getPropertyDescriptors(DataType type) {
            //TODO combine user defined types, and enums
//            return new IPropertyDescriptor[] {new ComboBoxPropertyDescriptor(PROP_VALUE_TYPE, PROP_VALUE_TYPE, DEFAULT_VALUE_TYPE_NAMES)};
            return null;
        }
        
        public void setPropertyValue(DataType type, Object propName, Object value){
            if (PROP_VALUE_TYPE.equals(propName))
                changeValueType(type, DataType.NUMBER_TYPE);
        }
    },

    SET("Set") {
        public String getDataTypeName(DataType type) {
            return getName() + "<" + type.getValueType().getName() + ">";
        }

        @Override
        public DataType createDataType() {
            // TODO Auto-generated method stub
            return null;
        }

    },

    MAP("Map") {
        public String getDataTypeName(DataType type) {
            return getName() + "<" + type.getKeyType().getType().getName() + ", " + type.getValueType().getType().getName() + ">";
        }

        @Override
        public DataType createDataType() {
            // TODO Auto-generated method stub
            return null;
        }
    },

    ENUM("Enum"){
        
        @Override
        public DataType createDataType() {
            // TODO Auto-generated method stub
            return null;
        }
    },
    
    USER_DEFINED("User defined type"){
        
        @Override
        public DataType createDataType() {
            // TODO Auto-generated method stub
            return new DataType(this);
        }
    };
    
    private static final DataType DEFAULT_TYPE = DataType.STRING_TYPE;
    private static List<MethodDefinition> getCommonMethod(DataType elementType) {
        List<MethodDefinition> mtds = new ArrayList<MethodDefinition>();
        mtds.add(new MethodDefinition(SIZE, DataType.NUMBER_TYPE));
        mtds.add(new MethodDefinition(IS_EMPTY, DataType.BOOLEAN_TYPE));

        return mtds;
    }
    


    private DataTypeEnum(String name) {
        this.name = name;
    }

    private String name;
    
    public String getName() {
        return name;
    }

    
    public String getDataTypeName(DataType type) {
        return type.getName();
    }
    
    public abstract DataType createDataType();

    private final static IPropertyDescriptor[] NONE = new IPropertyDescriptor[0];
    
    public IPropertyDescriptor[] getPropertyDescriptors(DataType type) {
        return NONE;
    }
    
    public Object getPropertyValue(Object propName) {
        return null;
    }

    public void setPropertyValue(DataType type, Object propName, Object value){
    }
    
    public IPropertyDescriptor[] getValueTypeDescriptors(DataType type) {
        return NONE;
    }
    
    public static boolean isPredefined(DataTypeEnum type) {
        switch (type) {
        case USER_DEFINED:
        case ENUM:
            return false;
        default:
            return true;
        }
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
    
    private static final String VALUE = "value";
    private static final String CONTAINS = "contains";
    private static final String CONTAINS_ALL = "containsAll";
    private static final String INDEX_OF = "indexOf";
    private static final String LAST_INDEX_OF = "lastIndexOf";
    private static final String GET = "get";
    private static final String LENGTH = "length";
    private static final String SIZE = "size";
    private static final String IS_EMPTY = "isEmpty";
    
    public DataTypeEnum findByName(String name) {
        for(DataTypeEnum e: DataTypeEnum.values())
            if(e.getName().equals(name))
                return e;
        return null;
    }

}
