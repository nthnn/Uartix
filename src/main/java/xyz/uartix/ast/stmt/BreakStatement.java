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

import xyz.uartix.ast.Statement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeBreak;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

public class BreakStatement implements Statement {
    private final Token address;

    public BreakStatement(Token address) {
        this.address = address;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws TerminativeSignal {
        throw new TerminativeBreak();
    }
}
