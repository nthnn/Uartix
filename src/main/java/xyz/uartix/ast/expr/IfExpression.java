package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class IfExpression implements Expression {
    private final Token address;
    private final Expression condition;
    private final Statement then, elseStatement;

    public IfExpression(
        Token address,
        Expression condition,
        Statement then,
        Statement elseStatement
    ) {
        this.address = address;
        this.condition = condition;
        this.then = then;
        this.elseStatement = elseStatement;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object cond = this.condition.visit(symtab);

        if((cond instanceof Double && ((double) cond) > 0) ||
            (cond instanceof Boolean && (boolean) cond) ||
            cond instanceof String)
            return this.then.visit(symtab);
        else if(this.elseStatement != null)
            return this.elseStatement.visit(symtab);

        return null;
    }
}
