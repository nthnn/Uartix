package xyz.uartix.ast.stmt;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class ExpressionStatement implements Statement {
    private final Token address;
    private final Expression expression;

    public ExpressionStatement(Token address, Expression expression) {
        this.address = address;
        this.expression = expression;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws TerminativeSignal,
            ASTVisitException,
            IOException {
        return this.expression.visit(symtab);
    }
}
