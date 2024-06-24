package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class IdentifierExpression implements Expression {
    private final Token identifier;

    public IdentifierExpression(Token identifier) {
        this.identifier = identifier;
    }

    public Token getAddress() {
        return this.identifier;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        if(!symtab.has(this.identifier.getImage()))
            throw new ASTVisitException(this, "Cannot find symbol.");

        return symtab.get(this.identifier.getImage());
    }
}
