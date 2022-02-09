package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class CollectionType {//extends DataType {
//    private String elementTypeName;
//    
//    private CollectionType(String nameTemplate, String elementTypeName) {
//        super(String.format(nameTemplate, elementTypeName));
//        this.elementTypeName = elementTypeName;
//    }
//    
//    private static String[] getEmbeddedType(String type, String compositType) {
//        return type.substring(compositType.length()+1, type.length()-1).split(",");
//    }
//    
//    public static CollectionType parseList(String type) {
//        String elementTypeName = getEmbeddedType(type, DataType.LIST)[0].trim();
//        
//        CollectionType listType = new CollectionType("List<%s>", elementTypeName);
//        listType.getMethods().addAll(getCommonMethod(elementTypeName));
//        listType.add(mtd("indexOf", NUMBER));
//        listType.add(mtd("lastIndexOf", NUMBER));
//        listType.add(mtd("get", elementTypeName));
//        listType.add(mtd("subList", listType.getName()));
//        return listType;
//    }
//
//    public static CollectionType parseSet(String type) {
//        String elementTypeName = getEmbeddedType(type, DataType.LIST)[0].trim();
//        
//        CollectionType setType = new CollectionType("Set<%s>", elementTypeName);
//        setType.getMethods().addAll(getCommonMethod(elementTypeName));
//        return setType;
//    }
//    
//    public static CollectionType array(String elementTypeName) {
//        CollectionType arrayType = new CollectionType("[%s]", elementTypeName);
//        FieldDefinition fieldLength = new FieldDefinition();
//        fieldLength.setName("length");
//        fieldLength.setTypeName(NUMBER);
//        arrayType.add(fieldLength);
//        return arrayType;
//    }
//    
//    private static List<MethodDefinition> getCommonMethod(String elementTypeName) {
//        List<MethodDefinition> mtds = new ArrayList<MethodDefinition>();
//        mtds.add(mtd("size", NUMBER));
//        mtds.add(mtd("isEmpty", BOOLEAN));
//        mtds.add(mtd("contains", BOOLEAN));
//        mtds.add(mtd("containsAll", BOOLEAN));
//
//        return mtds;
//    }
//
//    public String getElementTypeName() {
//        return elementTypeName;
//    }
}
