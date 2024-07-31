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

package xyz.uartix.ast.stmt;

import xyz.uartix.ast.ASTVisitException;
import xyz.uartix.ast.Expression;
import xyz.uartix.ast.Statement;
import xyz.uartix.core.Runtime;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.core.TerminatoryObject;
import xyz.uartix.parser.Token;

import java.io.IOException;

public class TestStatement implements Statement {
    private final Token address;
    private final Expression name, body;

    public TestStatement(Token address, Expression name, Expression body) {
        this.address = address;
        this.name = name;
        this.body = body;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws TerminativeSignal, ASTVisitException, IOException {
        if(!Runtime.isTestMode())
            return null;

        String name = this.name.visit(symtab).toString();
        Object value = null;

        long startTime = System.nanoTime();
        try {
            value = this.body.visit(symtab);
        } catch(TerminativeSignal sig) {
            if(sig instanceof TerminatoryObject)
                value = ((TerminatoryObject) sig).getObject();
            else
                throw sig;
        }

        long elapsedTime = System.nanoTime() - startTime;
        if((value instanceof Boolean && ((boolean) value)) || (value instanceof Double && ((double) value) != 0.0))
            System.out.print("[\u001b[32m SUCCESS \u001b[0m] ");
        else
            System.out.print("[\u001b[33m FAILED \u001b[0m]  ");

        System.out.println(String.format("%.3f", elapsedTime / 1000000.0) + " ms: " + name);
        return null;
    }
}
