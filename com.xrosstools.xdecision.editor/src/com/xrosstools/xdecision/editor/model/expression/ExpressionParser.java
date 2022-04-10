package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.editor.model.definition.ArrayType;
import com.xrosstools.xdecision.editor.model.definition.DataType;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedType;

public class ExpressionParser {
    private DecisionTreeManager manager;
    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();

    public ExpressionParser(DecisionTreeManager manager) {
        this.manager = manager;
    }
    public ExpressionDefinition parse(String expressionRawText) {
        if(expressionRawText == null || expressionRawText.trim().length() == 0)
            return null;

        ExpressionDefinition rawExpression = compiler.compile(tokenParser.parseToken(expressionRawText));
        
        matchVariables(rawExpression);
            
        return rawExpression;
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
        NamedType member = exp instanceof MethodExpression ? 
                parentType.findMethod(varExp.getName()) :
                    parentType.findField(varExp.getName());


        varExp.setReferenceElement(member);
        
        if(varExp instanceof MethodExpression)
            matchVariables(((MethodExpression)varExp).getParameters());
        
        resolve(DataType.getType(member), varExp.getChildExpression());
    }
}
