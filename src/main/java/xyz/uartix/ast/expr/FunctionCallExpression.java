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

public class FunctionCallExpression implements Expression {
    private final Token address;
    private final Expression callOrigin;
    private final List<Expression> arguments;

    public FunctionCallExpression(Token address, Expression callOrigin, List<Expression> arguments) {
        this.address = address;
        this.callOrigin = callOrigin;
        this.arguments = arguments;
    }

    public Token getAddress() {
        return this.address;
    }

    public Object visit(SymbolTable symtab) throws ASTVisitException, IOException, TerminativeSignal {
        Object origin = null;

        if(this.callOrigin instanceof IdentifierExpression)
            origin = symtab.get(this.callOrigin.getAddress().getImage());
        else
            origin = this.callOrigin.visit(symtab);

        if(!(origin instanceof Function))
            throw new ASTVisitException(this, "Expression value is not of function instance.");

        List<Object> args = new ArrayList<>();
        for(Expression arg : this.arguments)
            args.add(arg.visit(symtab));

        return ((Function) origin).invoke(symtab, args);
    }
}
