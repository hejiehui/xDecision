package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.FactorExpression;

public class ChangeNodeFactorCommand extends Command{
    private DecisionTreeNode node;
    private ExpressionDefinition oldExp;
    private ExpressionDefinition newExp;
    
    public ChangeNodeFactorCommand(DecisionTreeNode node, int newFactorId){
    	this.node = node;
    	oldExp = node.getNodeExpression();
    	newExp = new FactorExpression(node.getDecisionTreeManager(), newFactorId);
    }
    
    public void execute() {
        node.setNodeExpression(newExp);
    }

    public String getLabel() {
        return "Change factor id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	node.setNodeExpression(oldExp);
    }
    /**
     *      
     *  CalculationExpression innerCalExp = new CalculationExpression();
        innerCalExp.add(new StringExpression("a book"));
        innerCalExp.add(new OperatorExpression(OperatorEnum.TIMES));
        FactorExpression factorB = new FactorExpression(manager, 0);
        innerCalExp.add(factorB);
        ParameterListExpression pl = new ParameterListExpression(2);
        pl.setParameter(0, new NumberExpression(2));
        pl.setParameter(1, new NumberExpression(4));
        factorB.withField("name").withMethod(new MethodExpression("substring", pl));
        
        CalculationExpression calExp = new CalculationExpression();
        FactorExpression factorA = new FactorExpression(manager, 1);
        calExp.add(factorA);
        calExp.add(new OperatorExpression(OperatorEnum.PLUS));
        calExp.add(new NumberExpression(123));
        calExp.add(new OperatorExpression(OperatorEnum.MINUS));
        calExp.add(EnclosedExpression.bracketOf(innerCalExp));
        calExp.add(new OperatorExpression(OperatorEnum.DIVIDE));
        FactorExpression factorC = new FactorExpression(manager, 2);
        calExp.add(factorC);
        factorC.withField("friends").withElement(new NumberExpression(123));
        
        return EnclosedExpression.bracketOf(calExp);

     */
}
