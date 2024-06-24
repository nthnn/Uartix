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

package xyz.uartix.core;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Statement;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.List;

public class Function {
    private final Token address;
    private final List<String> parameters;
    private final Statement body;

    public Function(
        Token address,
        List<String> parameters,
        Statement body
    ) {
        this.address = address;
        this.parameters = parameters;
        this.body = body;
    }

    public Object invoke(SymbolTable table, List<Object> arguments)
        throws TerminativeSignal,
            ASTVisitException,
            IOException {
        SymbolTable symtab = new SymbolTable(table);
        if(this.parameters.size() != arguments.size())
            throw new ASTVisitException(
                this.address,
                "Expecting " + this.parameters.size() +
                ", but got " + arguments.size() + " arguments."
            );

        for(int i = 0; i < this.parameters.size(); i++)
            symtab.set(this.parameters.get(i), arguments.get(i));
        return this.body.visit(symtab);
    }
}
