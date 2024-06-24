package xyz.uartix.ast.stmt;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.core.TerminativeThrow;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class ThrowStatement implements Statement {
    private final Token address;
    private final Expression value;

    public ThrowStatement(Token address, Expression value) {
        this.address = address;
        this.value = value;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        throw new TerminativeThrow(this.value.visit(symtab));
    }
}
