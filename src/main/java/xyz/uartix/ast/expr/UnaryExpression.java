package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;
import xyz.uartix.uart.UartOperation;

import java.io.IOException;

public class UnaryExpression implements Expression {
    private final Token operator;
    private final Expression expression;

    public UnaryExpression(Token operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public Token getAddress() {
        return this.operator;
    }

    public Object visit() throws ASTVisitException, IOException, TerminativeSignal {
        Object value = this.expression.visit();

        switch(this.operator.getImage()) {
            case "+" -> {
                if (value instanceof Double)
                    return UartOperation.pos((double) value);

                throw new ASTVisitException(this, "Invalid plus unary operation.");
            }
            case "-" -> {
                if (value instanceof Double)
                    return UartOperation.neg((double) value);

                throw new ASTVisitException(this, "Invalid negate unary operation.");
            }
            case "~" -> {
                if (value instanceof Double)
                    return UartOperation.not((double) value);
                else if (value instanceof String)
                    return new StringBuilder(value.toString())
                        .reverse()
                        .toString();

                throw new ASTVisitException(this, "Invalid bitwise not unary operation.");
            }
        }

        throw new ASTVisitException(this, "Invalid unary expression.");
    }
}
