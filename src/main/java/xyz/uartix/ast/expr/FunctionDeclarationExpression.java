package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.Function;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FunctionDeclarationExpression implements Expression {
    private final Token address;
    private final List<Token> parameters;
    private final Statement body;

    public FunctionDeclarationExpression(
        Token address,
        List<Token> parameters,
        Statement body
    ) {
        this.address = address;
        this.parameters = parameters;
        this.body = body;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        List<String> params = new ArrayList<>();
        for(Token parameter : this.parameters) {
            if(symtab.has(parameter.getImage()))
                throw new ASTVisitException(parameter, "Name already in-use.");

            params.add(parameter.getImage());
        }

        return new Function(
            this.address,
            params,
            this.body
        );
    }
}
