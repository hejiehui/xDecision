package com.xrosstools.xdecision.editor.model.expression;

import java.util.Arrays;
import java.util.List;

public class Grammar {
    List<Object> tokens;

    private Grammar(Object...values) {
        tokens = Arrays.asList(values);
    }
    
    public static Grammar of(Object...values) {
        return new Grammar(values);
    }

}
