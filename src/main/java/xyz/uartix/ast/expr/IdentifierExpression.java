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

public class IdentifierExpression implements Expression {
    private final Token identifier;

    public IdentifierExpression(Token identifier) {
        this.identifier = identifier;
    }

    public Token getAddress() {
        return this.identifier;
    }

    public Object visit(SymbolTable symtab) throws ASTVisitException, IOException, TerminativeSignal {
        if(!symtab.has(this.identifier.getImage()))
            throw new ASTVisitException(this, "Cannot find symbol.");

        return symtab.get(this.identifier.getImage());
    }
}
