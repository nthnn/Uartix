package xyz.uartix.ast.stmt;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminatoryObject;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class ReturnStatement implements Statement {
    private final Token address;
    private final Expression value;

    public ReturnStatement(Token address, Expression value) {
        this.address = address;
        this.value = value;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws TerminativeSignal,
            ASTVisitException,
            IOException {
        throw new TerminatoryObject(this.value.visit(symtab));
    }
}
