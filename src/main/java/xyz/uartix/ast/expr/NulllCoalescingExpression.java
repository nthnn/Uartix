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

public class NulllCoalescingExpression implements Expression {
    private final Token address;
    private final Expression left, right;

    public NulllCoalescingExpression(Token address, Expression left, Expression right) {
        this.address = address;
        this.left = left;
        this.right = right;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws ASTVisitException, IOException, TerminativeSignal {
        Object value = this.left.visit(symtab);
        if(value != null)
            return value;

        return this.right.visit(symtab);
    }
}
