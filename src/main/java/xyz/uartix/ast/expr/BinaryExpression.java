package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.parser.Token;
import xyz.uartix.uart.UartOperation;
import xyz.uartix.util.MiscUtil;

import java.io.IOException;
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

    public Object visit() throws ASTVisitException, IOException {
        ASTVisitException invalidVisit = new ASTVisitException(
            this,
            "Invalid binary expression operation."
        );

        String operator = this.operator.getImage();
        Object leftValue = this.left.visit(),
            rightValue = this.right.visit();

        if(Objects.equals(operator, "+"))
            return switch (leftValue) {
                case Double _ when rightValue instanceof Double ->
                    UartOperation.add((double) leftValue, (double) rightValue);

                case Double _ when rightValue instanceof String ->
                    ((double) leftValue) + rightValue.toString();

                case String _ when rightValue instanceof Double ->
                    leftValue.toString() + ((double) rightValue);

                case String _ when rightValue instanceof String ->
                    leftValue.toString() + rightValue;

                case null, default ->
                    throw invalidVisit;
            };
        else if(Objects.equals(operator, "*"))
            return switch (leftValue) {
                case Double _ when rightValue instanceof Double ->
                    UartOperation.mul((double) leftValue, (double) rightValue);

                case Double _ when rightValue instanceof String ->
                    MiscUtil.multiply(rightValue.toString(), (int) (double) leftValue);

                case String _ when rightValue instanceof Double ->
                    MiscUtil.multiply(leftValue.toString(), (int) (double) rightValue);

                case null, default ->
                    throw invalidVisit;
            };
        else if(Objects.equals(operator, "-")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.sub((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "/")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.div((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "&")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.and((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "|")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.or((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "%")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.rem((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "^")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.pow((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "<")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.lt((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "<=")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.le((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, ">")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.gt((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, ">=")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.ge((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "<<")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.shl((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, ">>")) {
            if(leftValue instanceof Double && rightValue instanceof Double)
                return UartOperation.shr((double) leftValue, (double) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "&&")) {
            if(leftValue instanceof Boolean && rightValue instanceof Boolean)
                return ((boolean) leftValue) && ((boolean) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "||")) {
            if(leftValue instanceof Boolean || rightValue instanceof Boolean)
                return ((boolean) leftValue) || ((boolean) rightValue);

            throw invalidVisit;
        }
        else if(Objects.equals(operator, "!="))
            return leftValue != rightValue;
        else if(Objects.equals(operator, "=="))
            return leftValue == rightValue;

        return null;
    }
}
