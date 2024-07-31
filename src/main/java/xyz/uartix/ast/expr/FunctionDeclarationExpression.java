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
    private final Expression body;

    public FunctionDeclarationExpression(Token address, List<Token> parameters, Expression body) {
        this.address = address;
        this.parameters = parameters;
        this.body = body;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws ASTVisitException, IOException, TerminativeSignal {
        List<String> params = new ArrayList<>();
        for(Token parameter : this.parameters) {
            if(symtab.has(parameter.getImage()))
                throw new ASTVisitException(parameter, "Name already in-use.");

            params.add(parameter.getImage());
        }

        return new Function(this.address, params, this.body);
    }
}
