package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class VariableDeclarationExpression implements Expression {
    private final Token name;
    private final Expression value;

    public VariableDeclarationExpression(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public Token getAddress() {
        return this.name;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object varValue = this.value.visit(symtab);
        symtab.set(this.name.getImage(), varValue);

        return varValue;
    }
}
