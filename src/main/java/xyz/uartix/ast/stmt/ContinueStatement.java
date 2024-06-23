package xyz.uartix.ast.stmt;

import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeContinue;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

public class ContinueStatement implements Statement {
    private final Token address;

    public ContinueStatement(Token address) {
        this.address = address;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws TerminativeSignal {
        throw new TerminativeContinue();
    }
}
