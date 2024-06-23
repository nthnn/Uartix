package xyz.uartix.ast.expr;

import xyz.uartix.ast.Expression;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.parser.Token;

public class LiteralExpression implements Expression {
    private final Token address;
    private final Object value;

    public LiteralExpression(Token address, Object value) {
        this.address = address;
        this.value = value;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) {
        return this.value;
    }
}
