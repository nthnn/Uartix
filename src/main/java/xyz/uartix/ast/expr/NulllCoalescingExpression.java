package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class NulllCoalescingExpression implements Expression {
    private final Token address;
    private final Expression left, right;

    public NulllCoalescingExpression(
        Token address,
        Expression left,
        Expression right
    ) {
        this.address = address;
        this.left = left;
        this.right = right;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object value = this.left.visit(symtab);
        if(value != null)
            return value;

        return this.right.visit(symtab);
    }
}
