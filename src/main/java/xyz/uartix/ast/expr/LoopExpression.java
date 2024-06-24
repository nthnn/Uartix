package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeBreak;
import xyz.uartix.core.TerminativeContinue;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class LoopExpression implements Expression {
    private final Token address;
    private final Expression initial, condition, postExpression;
    private final Statement body;

    public LoopExpression(
        Token address,
        Expression initial,
        Expression condition,
        Expression postExpression,
        Statement body
    ) {
        this.address = address;
        this.initial = initial;
        this.condition = condition;
        this.postExpression = postExpression;
        this.body = body;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        this.initial.visit(symtab);

        Object cond = this.condition.visit(symtab),
            value = null;

        while((cond instanceof Boolean && (boolean) cond) ||
                (cond instanceof Double && ((double) cond) > 0.0) ||
                (cond instanceof String)) {
            try {
                value = this.body.visit(symtab);
            }
            catch(TerminativeBreak _) {
                value = null;
                break;
            }
            catch(TerminativeContinue _) {
                value = null;
                continue;
            }

            this.postExpression.visit(symtab);
            cond = this.condition.visit(symtab);
        }

        return value;
    }
}
