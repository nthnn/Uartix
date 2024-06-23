package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.Random;

public class RandomExpression implements Expression {
    private final Token address;
    private final Statement then, elseStatement;

    public RandomExpression(
            Token address,
            Statement then,
            Statement elseStatement
    ) {
        this.address = address;
        this.then = then;
        this.elseStatement = elseStatement;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        if(new Random().nextBoolean())
            return this.then.visit(symtab);
        else if(this.elseStatement != null)
            return this.elseStatement.visit(symtab);

        return null;
    }
}
