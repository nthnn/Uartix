package xyz.uartix.ast.expr;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.*;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class WhileExpression implements Expression {
    private final Token address;
    private final Expression condition;
    private final Statement body;

    public WhileExpression(
        Token address,
        Expression condition,
        Statement body
    ) {
        this.address = address;
        this.condition = condition;
        this.body = body;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object cond = this.condition.visit(symtab);
        Object value = null;

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

            cond = this.condition.visit(symtab);
        }
        return value;
    }
}
