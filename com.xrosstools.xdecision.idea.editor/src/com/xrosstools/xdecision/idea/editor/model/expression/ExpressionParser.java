package com.xrosstools.xdecision.idea.editor.model.expression;

import com.xrosstools.xdecision.idea.editor.model.DecisionTreeConstant;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.idea.editor.model.definition.*;

public class ExpressionParser {
    private DecisionTreeManager manager;
    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();

    public ExpressionParser(DecisionTreeManager manager) {
        this.manager = manager;
    }

    public ExpressionDefinition parseParameters(ExpressionType type, String expressionRawText) {
        if(expressionRawText == null || expressionRawText.trim().length() == 0)
            return new PlaceholderExpression("");

        ExpressionDefinition rawExpression;
        try {
            rawExpression = compiler.compile(type, tokenParser.parseToken(expressionRawText));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Expression: \"%s\"\n is INVALID!\nNested error: %s", expressionRawText, e.getMessage()));
        }
        
        matchVariables(rawExpression);
            
        return rawExpression;
    }

    public ExpressionDefinition parseExpression(String expressionRawText) {
        return parseParameters(ExpressionType.A, expressionRawText);
    }
    
    private void matchVariables(ExpressionDefinition exp) {
        if(exp == null)
            return;

        if(exp instanceof BasicExpression)
            return;
    
        if(exp instanceof CompositeExpression) {
            for(ExpressionDefinition childExp: ((CompositeExpression)exp).getAllExpression())
                matchVariables(childExp);
            return;
        }
        
        if(exp instanceof EnclosedExpression) {
            matchVariables(((EnclosedExpression)exp).getInnerExpression());
            return;
        }

        if(exp instanceof VariableExpression) {
            resolve((VariableExpression)exp);
            return;
        }
    }
    
    public void resolve(VariableExpression varExp) {
        DecisionTreeDiagram diagram = manager.getDiagram();
        String name = varExp.getName();

        NamedElement member = diagram.getFactors().findByName(name);
        if(member == null)
            member = diagram.getUserDefinedConstants().findByName(name);

        if(member == null)
            member = diagram.getUserDefinedEnums().findByName(name);
        
        if(DecisionTreeConstant.TRUE.getName().equals(name))
            member = DecisionTreeConstant.TRUE;

        if(DecisionTreeConstant.FALSE.getName().equals(name))
            member = DecisionTreeConstant.FALSE;

        varExp.setReferenceElement(member);

        resolve(DataType.getType(member), varExp.getChildExpression());
    }
    
    public void resolve(DataType parentType, ExpressionDefinition exp) {
        if(parentType == null || exp == null)
            return;
            
        if (exp instanceof ElementExpression) {
            ElementExpression eleExp = (ElementExpression)exp;
            matchVariables((eleExp).getIndexExpression());
            
            if(parentType instanceof ArrayType)
                resolve(((ArrayType) parentType).getValueType(), eleExp.getChildExpression());

            return;
        }

        VariableExpression varExp = (VariableExpression)exp;

        if(parentType instanceof EnumType) {
            NamedElement member = ((EnumType)parentType).findByName(varExp.getName());
            varExp.setReferenceElement(member);
            resolve(parentType, varExp.getChildExpression());
        } else {
            NamedType member = exp instanceof MethodExpression ?
                    parentType.findMethod(varExp.getName()) :
                    parentType.findField(varExp.getName());
            varExp.setReferenceElement(member);
            if(varExp instanceof MethodExpression)
                matchVariables(((MethodExpression)varExp).getParameters());

            resolve(DataType.getType(member), varExp.getChildExpression());
        }
    }
}
