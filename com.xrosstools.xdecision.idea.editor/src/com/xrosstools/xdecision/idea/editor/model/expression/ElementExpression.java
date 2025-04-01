package com.xrosstools.xdecision.idea.editor.model.expression;

public class ElementExpression extends ExtensibleExpression {
    private ExpressionDefinition indexExpression;

    public ElementExpression () {
        this(new PlaceholderExpression("index"));
    }
    public ElementExpression (ExpressionDefinition indexExpression) {
        setIndexExpression(indexExpression);
    }

    public ExpressionDefinition getIndexExpression() {
        return indexExpression;
    }

    public void setIndexExpression(ExpressionDefinition indexExpression) {
        unlisten(this.indexExpression);
        this.indexExpression = indexExpression;
        listen(indexExpression);
        propertyChanged();
    }

    @Override
    public String getBaseString() {
        return "[" + indexExpression.toString() + "]";
    }
}
