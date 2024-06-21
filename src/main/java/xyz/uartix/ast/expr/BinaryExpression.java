package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.parser.Token;

import java.util.Objects;

public class BinaryExpression implements Expression {
    private final Token operator;
    private final Expression left, right;

    public BinaryExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Token getAddress() {
        return this.operator;
    }

    public Object visit() throws ASTVisitException {
        String operator = this.operator.getImage();
        Object leftValue = this.left.visit(),
            rightValue = this.right.visit();

        if(Objects.equals(operator, "+"))
            return switch (leftValue) {
                case Double v when rightValue instanceof Double ->
                    ((double) leftValue) + ((double) rightValue);

                case Double v when rightValue instanceof String ->
                    ((double) leftValue) + rightValue.toString();

                case String s when rightValue instanceof Double ->
                    leftValue.toString() + ((double) rightValue);

                case String s when rightValue instanceof String ->
                    leftValue.toString() + rightValue.toString();

                case null, default ->
                    throw new ASTVisitException(this, "Invalid binary expression operation.");
            };

        return null;
    }
}
