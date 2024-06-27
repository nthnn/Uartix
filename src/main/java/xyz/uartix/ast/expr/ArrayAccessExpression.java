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
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.List;

public class ArrayAccessExpression implements Expression {
    private final Token address;
    private final Expression origin, index;

    public ArrayAccessExpression(
        Token address,
        Expression origin,
        Expression index
    ) {
        this.address = address;
        this.origin = origin;
        this.index = index;
    }

    public Token getAddress() {
        return this.address;
    }

    public Expression getOrigin() {
        return this.origin;
    }

    public Expression getIndex() {
        return this.index;
    }

    @SuppressWarnings("unchecked")
    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object access = this.origin.visit(symtab);
        if(!(access instanceof List<?>))
            throw new ASTVisitException(this, "Trying to access non-array expression.");

        Object index = this.index.visit(symtab);
        if(!(index instanceof Double))
            throw new ASTVisitException(this, "Accessing array with non-number index.");

        return ((List<Object>) access).get((int)(double) index);
    }
}
