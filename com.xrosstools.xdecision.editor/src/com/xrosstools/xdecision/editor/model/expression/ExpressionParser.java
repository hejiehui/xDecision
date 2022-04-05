package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.ArrayType;
import com.xrosstools.xdecision.editor.model.DataType;
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

        if(exp instanceof ExtensibleExpression) {
            ExtensibleExpression extExp = (ExtensibleExpression)exp;
            //The first one must be VariableExpression
            NamedElement userDefined = resolve((VariableExpression)((ExtensibleExpression)exp).getBaseExpression());
            resolve(DataType.getType(userDefined), extExp.getChild());
            return;
        }
    }
    
    public NamedElement resolve(VariableExpression varExp) {
        DecisionTreeDiagram diagram = manager.getDiagram();
        String name = varExp.getName();

        NamedElement member = diagram.getFactors().findByName(name);
        if(member == null)
            member = diagram.getUserDefinedConstants().findByName(name);

        if(member == null)
            member = diagram.getUserDefinedEnums().findByName(name);
        
        varExp.setReferenceElement(member);

        return member;
    }
    
    public void resolve(DataType parentType, ExpressionDefinition exp) {
        if(parentType == null || exp == null)
            return;
            
        if(!(exp instanceof ExtensibleExpression))
            return;
            
        ExtensibleExpression extExp = (ExtensibleExpression)exp;
        ExpressionDefinition baseExp = extExp.getBaseExpression();
            
        if (baseExp instanceof ElementExpression) { 
            matchVariables(((ElementExpression)baseExp).getIndexExpression());
            
            if(parentType instanceof ArrayType)
                resolve(((ArrayType) parentType).getValueType(), extExp.getChild());

            return;
        }
        
        VariableExpression varExp = (VariableExpression)baseExp;
        NamedType member = exp instanceof MethodExpression ? 
                parentType.findMethod(varExp.getName()) :
                    parentType.findField(varExp.getName());


        resolve(DataType.getType(member), extExp.getChild());
    }
}
