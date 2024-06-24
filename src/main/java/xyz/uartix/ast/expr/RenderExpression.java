package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class RenderExpression implements Expression {
    private final Token address;
    private final Expression expression;

    public RenderExpression(Token address, Expression expression) {
        this.address = address;
        this.expression = expression;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object value = this.expression.visit(symtab);

        System.out.println(value == null ? "nil" : value.toString());
        return value;
    }
}
