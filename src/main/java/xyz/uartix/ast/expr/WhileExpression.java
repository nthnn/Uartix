/*
 * This file is part of the Uartix programming language (https://github.com/nthnn/Uartix).
 * Copyright (c) 2024 Nathanne Isip.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
