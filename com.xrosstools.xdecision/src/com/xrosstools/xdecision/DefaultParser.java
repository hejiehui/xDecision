package com.xrosstools.xdecision;

/**
 * Default implementation which just use the string as value
 * @author Jerry He
 */
public class DefaultParser implements XDecisionTreeParser {
    @Override
    public Object parseDecisionPath(String name, String operator, String value) {
        //The V1.0 format, the default "==" is not specified
        return operator == null ? value : operator + " " + value;
    }

    @Override
    public Object parseDecision(String name) {
        return name;
    }
}
