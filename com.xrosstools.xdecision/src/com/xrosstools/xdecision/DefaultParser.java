package com.xrosstools.xdecision;

/**
 * Default implementation which just use the string as value
 * @author Jerry He
 */
public class DefaultParser implements XDecisionTreeParser {
    @Override
    public Object parseFact(String name, String value) {
        return value;
    }

    @Override
    public Object parseDecision(String name) {
        return name;
    }
}
