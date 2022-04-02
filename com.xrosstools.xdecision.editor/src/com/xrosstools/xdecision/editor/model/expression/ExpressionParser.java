package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.ArrayType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedType;

public class ExpressionParser {
    private DecisionTreeManager manager;
    private TokenParser tokenParser = new TokenParser();
    private ExpressionCompiler compiler = new ExpressionCompiler();

    public ExpressionParser(DecisionTreeManager manager) {
        this.manager = manager;
    }
    public ExpressionDefinition parse(String expressionRawText) {
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
        
        if(member == null)
            return;

        varExp.setReferenceElement(member);

        resolve(DataType.getType(member), varExp.getChild());
    }
    
    public void resolve(DataType parentType, ExpressionDefinition exp) {
        if(parentType == null || exp == null)
            return;
            
        if (exp instanceof ElementExpression) { 
            matchVariables(((ElementExpression)exp).getIndexExpression());
            
            if(parentType instanceof ArrayType)
                resolve(((ArrayType) parentType).getValueType(), ((ElementExpression)exp).getChild());

            return;
        }
        
        VariableExpression varExp = (VariableExpression)exp;
        NamedType member = exp instanceof MethodExpression ? 
                parentType.findMethod(varExp.getName()) :
                    parentType.findField(varExp.getName());

        if(member == null)
            return;

        varExp.setReferenceElement(member);
        resolve(member.getType(), varExp.getChild());
    }
}
