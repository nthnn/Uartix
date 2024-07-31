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
import xyz.uartix.ast.stmt.ReturnStatement;
import xyz.uartix.core.SymbolTable;
import xyz.uartix.core.TerminativeSignal;
import xyz.uartix.parser.Token;

import java.io.IOException;
import java.util.List;

public class BlockExpression implements Expression {
    private final Token address;
    private final List<Statement> statements;

    public BlockExpression(Token address, List<Statement> statements) {
        this.address = address;
        this.statements = statements;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws TerminativeSignal, ASTVisitException, IOException {
        SymbolTable childTab = new SymbolTable(symtab);
        Object value = null;

        for(Statement statement : this.statements) {
            if(statement instanceof ReturnStatement)
                statement.visit(childTab);

            value = statement.visit(childTab);
        }

        return value;
    }
}
