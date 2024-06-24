package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.core.Function;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FunctionCallExpression implements Expression {
    private final Token address;
    private final Expression callOrigin;
    private final List<Expression> arguments;

    public FunctionCallExpression(
        Token address,
        Expression callOrigin,
        List<Expression> arguments
    ) {
        this.address = address;
        this.callOrigin = callOrigin;
        this.arguments = arguments;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object origin = this.callOrigin.visit(symtab);
        if(!(origin instanceof Function))
            throw new ASTVisitException(this, "Expression value is not of function instance.");

        List<Object> args = new ArrayList<>();
        for(Expression arg : this.arguments)
            args.add(arg.visit(symtab));

        return ((Function) origin).invoke(symtab, args);
    }
}
