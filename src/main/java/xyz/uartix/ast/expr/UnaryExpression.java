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
import xyz.uartix.uart.UartOperation;

import java.io.IOException;

public class UnaryExpression implements Expression {
    private final Token operator;
    private final Expression expression;

    public UnaryExpression(Token operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public Token getAddress() {
        return this.operator;
    }

    public Object visit(SymbolTable symtab)
        throws ASTVisitException,
            IOException,
            TerminativeSignal {
        Object value = this.expression.visit(symtab);

        switch(this.operator.getImage()) {
            case "+" -> {
                if (value instanceof Double)
                    return UartOperation.pos((double) value);

                throw new ASTVisitException(this, "Invalid plus unary operation.");
            }
            case "-" -> {
                if (value instanceof Double)
                    return UartOperation.neg((double) value);

                throw new ASTVisitException(this, "Invalid negate unary operation.");
            }
            case "~" -> {
                if(value instanceof Double)
                    return UartOperation.not((double) value);
                else if(value instanceof Boolean)
                    return !((boolean) value);
                else if(value instanceof String)
                    return new StringBuilder(value.toString())
                        .reverse()
                        .toString();

                throw new ASTVisitException(this, "Invalid bitwise not unary operation.");
            }
        }

        throw new ASTVisitException(this, "Invalid unary expression.");
    }
}
