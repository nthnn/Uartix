package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class GroupedExpression implements Expression {
    private final Token address;
    private final Expression child;

    public GroupedExpression(Token address, Expression child) {
        this.address = address;
        this.child = child;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit() throws ASTVisitException, IOException, TerminativeSignal {
        return this.child.visit();
    }
}
