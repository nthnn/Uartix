package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.ast.stmt.ReturnStatement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.List;

public class BlockExpression implements Expression {
    private final Token address;
    private final List<Statement> statements;

    public BlockExpression(Token address, List<Statement> statements) {
        this.address = address;
        this.statements = statements;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws TerminativeSignal,
            ASTVisitException,
            IOException {
        for(Statement statement : this.statements) {
            if(statement instanceof ReturnStatement)
                return statement.visit(symtab);

            statement.visit(symtab);
        }

        return null;
    }
}
