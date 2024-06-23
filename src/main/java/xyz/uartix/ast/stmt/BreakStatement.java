package xyz.uartix.ast.stmt;

import xyz.uartix.ast.Statement;
import xyz.uartix.core.TerminativeBreak;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

public class BreakStatement implements Statement {
    private final Token address;

    public BreakStatement(Token address) {
        this.address = address;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit() throws TerminativeSignal {
        throw new TerminativeBreak();
    }
}
